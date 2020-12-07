package me.googas.commons.scheduler;

/** This task is scheduled to run later */
public interface RunLater extends Task {

  @Override
  default boolean pause() {
    return false;
  }

  @Override
  default boolean isPaused() {
    return false;
  }
}
