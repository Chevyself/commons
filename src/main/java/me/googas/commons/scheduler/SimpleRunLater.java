package me.googas.commons.scheduler;

import lombok.NonNull;

public class SimpleRunLater implements RunLater {

  private final int id;
  @NonNull private final Runnable onEnd;
  private boolean cancelled;

  public SimpleRunLater(int id, @NonNull Runnable onEnd) {
    this.id = id;
    this.onEnd = onEnd;
  }

  @Override
  public void run() {
    if (!this.cancelled) this.onEnd.run();
  }

  @Override
  public int getId() {
    return this.id;
  }

  @Override
  public boolean cancel() {
    if (this.cancelled) return false;
    this.cancelled = true;
    return true;
  }

  @Override
  public boolean isCancelled() {
    return this.cancelled;
  }
}
