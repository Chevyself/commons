package me.googas.commons.time;

import java.util.concurrent.TimeUnit;
import lombok.NonNull;
import lombok.experimental.Delegate;

/**
 * Just like {@link Time} but using the java unit {@link TimeUnit}. Only created for getting the
 * unit and the value use {@link Time} for other time queries
 */
public class ClassicTime {

  /** The unit of the time */
  @NonNull private final TimeUnit unit;
  /** The value of the time */
  private final long value;

  /**
   * Create an instance. If we want the time to be "10 seconds" we would give the value as "10" and
   * the unit is {@link TimeUnit#SECONDS}
   *
   * @param value the value of time
   * @param unit the unit of time
   */
  public ClassicTime(long value, @NonNull TimeUnit unit) {
    this.value = value;
    this.unit = unit;
  }

  /**
   * Get the time as {@link Time}. It will be converted using {@link Time#fromMillis(long)} and the
   * millis will be given by {@link Time#millis()}
   *
   * @return this instance a core type of time
   */
  @NonNull
  @Delegate
  public Time toTime() {
    return Time.fromMillis(this.millis());
  }

  @Override
  public String toString() {
    return this.value + " " + this.unit.toString().toLowerCase();
  }
}
