package me.googas.commons.events;

import java.lang.reflect.Method;
import lombok.NonNull;
import me.googas.commons.builder.ToStringBuilder;

/**
 * This class represents each method that is listening to an event. {@link #listener} is the object
 * from which the {@link #method} will be invoked. The method includes the only parameter that is
 * the {@link #event} that it is listening to
 */
public class EventListener {

  /** The object to invoke the method */
  @NonNull private final Object listener;

  /** The method that is listing for the method to be invoked */
  @NonNull private final Method method;

  /** The event parameter that is used to invoke the method */
  @NonNull private final Class<? extends Event> event;

  /** The priority for the event to be called in the listener */
  private final int priority;

  /**
   * Create an event listener instance which can be used inside the {@link ListenerManager} to be
   * called
   *
   * @param listener the object used to call the method
   * @param method the method that is listening to the event
   * @param event the event that is being listened to. Required to invoke the method
   * @param priority the priority for the event to be called in the listener
   */
  public EventListener(
      @NonNull Object listener,
      @NonNull Method method,
      @NonNull Class<? extends Event> event,
      int priority) {
    this.listener = listener;
    this.method = method;
    this.event = event;
    this.priority = priority;
  }

  /**
   * Get the object to invoke the method
   *
   * @return the object to invoke the method
   */
  @NonNull
  public Object getListener() {
    return this.listener;
  }

  /**
   * Get the method that is listening to the event
   *
   * @return the method that is listening to the event
   */
  @NonNull
  public Method getMethod() {
    return this.method;
  }

  /**
   * Get the event that is being listening to by the method
   *
   * @return the event that is being listening to by the method
   */
  @NonNull
  public Class<? extends Event> getEvent() {
    return this.event;
  }

  /**
   * Get the priority that the method is listening to
   *
   * @return the priority in which the event will be called for this listener
   */
  public int getPriority() {
    return this.priority;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("listener", this.listener)
        .append("method", this.method)
        .append("event", this.event)
        .append("priority", this.priority)
        .build();
  }
}
