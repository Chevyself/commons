package me.googas.samples.events;

import me.googas.commons.events.Cancellable;
import me.googas.commons.events.ListenPriority;
import me.googas.commons.events.Listener;
import me.googas.commons.events.ListenerManager;

public class EventsSample {

  public static void main(String[] args) {
    ListenerManager manager = new ListenerManager();
    manager.registerListeners(new EventsSample());
    manager.call(new SampleEvent("Hello!"));
    if (manager.call((Cancellable) new SampleCancellableEvent("Hello!"))) {
      // If it was cancelled call with another string
      Cancellable event = new SampleCancellableEvent("Dont cancel this one please!");
      System.out.println(
          "manager.call(event) = " + manager.call(event)); // True if event was cancelled
    }
  }

  // This will be called too with SampleCancellableEvent as it extends SampleEvent
  @Listener(priority = ListenPriority.MEDIUM)
  public void onSample(SampleEvent event) {
    System.out.println("Received the next string from an event: `" + event.getString() + "`");
  }

  @Listener(priority = ListenPriority.LOWEST)
  public void onSampleCancellable(SampleCancellableEvent event) {
    event.setCancelled(true);
  }

  @Listener(priority = ListenPriority.HIGHEST)
  public void onSampleCancellableHighest(SampleCancellableEvent event) {
    if (event.getString().equalsIgnoreCase("Hello") && event.isCancelled()) {
      event.setCancelled(false);
      // Event is hello and was cancelled by another listener but I have a higher
      // priority so I will make it pass
    }
  }
}
