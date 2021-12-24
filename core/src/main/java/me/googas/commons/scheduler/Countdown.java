package me.googas.commons.scheduler;

import lombok.NonNull;
import me.googas.commons.time.Time;
import me.googas.commons.time.Unit;

/** This countdown is a task that repeats until it reaches 0 */
public interface Countdown extends Repetitive {

  /**
   * Refreshes the time of the countdown this means sets the {@link #getMillisLeft()} ()} to {@link
   * #getTime()}
   *
   * @return this countdown instance
   */
  @NonNull
  Countdown refresh();

  /**
   * Gets the time that the countdown has to reach 0
   *
   * @return the countdown
   */
  @NonNull
  Time getTime();

  /**
   * Get the milliseconds that the countdown has left to reach 0
   *
   * @return the milliseconds that the countdown has left to reach 0
   */
  long getMillisLeft();

  /**
   * Get the seconds left that the countdown has to reach 0
   *
   * @return the time left
   */
  @NonNull
  default Time getTimeLeft() {
    if (this.getMillisLeft() < 0) return new Time(0, Unit.SECONDS);
    return Time.fromMillis(this.getMillisLeft());
  }
}
