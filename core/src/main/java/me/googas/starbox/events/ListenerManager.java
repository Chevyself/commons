package me.googas.starbox.events;

import lombok.Getter;
import lombok.NonNull;
import me.googas.starbox.Validate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

/**
 * Manages calling to events and selecting the respective listeners for the event. Listener are
 * registered using reflection that is why the annotation {@link Listener} is required for the
 * methods that are going to be listening for an event.
 */
public class ListenerManager {

  /** The listeners registered in the manager */
  @NonNull @Getter private final Collection<EventListener> listeners = new HashSet<>();

  /**
   * Register the listeners from the object.
   *
   * <p>Using reflection this method will get the class of the object doing a loop for each method.
   * The methods that have the annotation {@link Listener} will be attempted to create a listener
   *
   * @param object to get the class and methods to create the listeners
   * @throws ListenerRegistrationException if the method does not have parameters, if the method has
   *     more than one parameters and if the parameter does not extend {@link Event}
   */
  // Suppressed the warning for the cast of the event class but it is check by using the Event class
  // to see if the class of the parameter can be assigned
  @SuppressWarnings("unchecked")
  public void registerListeners(@NonNull Object object) {
    Class<?> aClass = object.getClass();
    for (Method method : aClass.getMethods()) {
      Listener annotation = method.getAnnotation(Listener.class);
      if (annotation != null) {
        Parameter[] parameters = method.getParameters();
        if (parameters.length == 0) {
          throw new ListenerRegistrationException(
              "Method "
                  + method
                  + "  in "
                  + aClass
                  + " does not have parameters therefore no event was found");
        } else if (parameters.length > 1) {
          throw new ListenerRegistrationException(
              "Method "
                  + method
                  + " in "
                  + aClass
                  + " has more than one parameter which will cause an exception when trying to invoke the method");
        } else if (Event.class.isAssignableFrom(parameters[0].getType())) {
          this.listeners.add(
              new EventListener(
                  object,
                  method,
                  // This is the cast that is "unchecked"
                  (Class<? extends Event>) parameters[0].getType(),
                  annotation.priority()));
        } else {
          throw new ListenerRegistrationException(
              "Method " + method + " in " + aClass + " parameter does not extend " + Event.class);
        }
      }
    }
  }

  /**
   * Get all the listeners for certain event.
   *
   * @param clazz the clazz of the event to get all the listeners
   * @return a sorted list of listeners for the event
   */
  @NonNull
  public List<EventListener> getListeners(@NonNull Class<? extends Event> clazz) {
    List<EventListener> listeners = new ArrayList<>();
    for (EventListener listener : this.listeners) {
      if (listener.getEvent().isAssignableFrom(clazz)) {
        listeners.add(listener);
      }
    }
    listeners.sort(Comparator.comparingInt(EventListener::getPriority));
    return listeners;
  }

  /**
   * Unregisters a listener
   *
   * @param object the listener to unregister, this is the object used to invoke the methods of the
   *     listeners
   */
  public void unregister(@NonNull Object object) {
    this.listeners.removeIf(listener -> listener.getListener() == object);
  }

  /**
   * Calls an event. This will get all the listeners for the event and call it for each of them
   *
   * @param event the event to be called
   */
  public void call(@NonNull Event event) {
    for (EventListener listener : this.getListeners(event.getClass())) {
      try {
        listener.getMethod().invoke(listener.getListener(), event);
      } catch (IllegalAccessException | InvocationTargetException e) {
        Validate.getFallback()
            .process(e, listener + " listener of " + event + " could not be invoked");
      }
    }
  }

  /**
   * Calls an event. As in {@link #call(Event)} but returns whether it was cancelled
   *
   * @param cancellable the event to be called
   * @return true if the event was cancelled
   * @throws IllegalArgumentException if the parameter cancellable is not an instance of {@link
   *     Event}
   */
  public boolean call(@NonNull Cancellable cancellable) {
    if (!(cancellable instanceof Event))
      throw new IllegalArgumentException(cancellable + " must extend " + Event.class);
    this.call((Event) cancellable);
    return cancellable.isCancelled();
  }
}
