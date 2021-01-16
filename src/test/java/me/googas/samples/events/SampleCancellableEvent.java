package me.googas.samples.events;

import me.googas.commons.events.Cancellable;

public class SampleCancellableEvent extends SampleEvent implements Cancellable {

  private boolean cancelled;

  public SampleCancellableEvent(String string) {
    super(string);
  }

  @Override
  public void setCancelled(boolean bol) {
    this.cancelled = bol;
  }

  @Override
  public boolean isCancelled() {
    return this.cancelled;
  }
}
