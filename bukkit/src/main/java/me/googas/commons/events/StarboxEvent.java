package me.googas.commons.events;

import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/** This class is extended if the event is fired by Starbox */
public class StarboxEvent extends Event {
  @NonNull private static final HandlerList handlers = new HandlerList();

  public void call() {
    Bukkit.getServer().getPluginManager().callEvent(this);
  }

  /**
   * This method is being used by Bukkit to get all the handlers for this events
   *
   * @return the handler list of the event
   */
  @NonNull
  @SuppressWarnings("unused")
  public static HandlerList getHandlerList() {
    return StarboxEvent.handlers;
  }

  @Override
  public @NonNull HandlerList getHandlers() {
    return StarboxEvent.handlers;
  }
}
