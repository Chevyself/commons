package me.googas.commons.scheduler;

public interface Task extends Runnable {

  /**
   * Cancel task
   *
   * @return true if the task was cancelled
   */
  boolean cancel();

  /**
   * Pauses the task
   *
   * @return whether the task was paused
   */
  boolean pause();

  /**
   * Get the id of the task
   *
   * @return the id of the task as an integer
   */
  int getId();

  /**
   * Get whether the task is cancelled
   *
   * @return true if the task is cancelled
   */
  boolean isCancelled();

  /**
   * Get whether the task is paused and can run again
   *
   * @return true if the task is paused
   */
  boolean isPaused();
}
