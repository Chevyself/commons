package me.googas.commons.time;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;
import lombok.NonNull;

/**
 * This object represents time in a simple fashion: a time {@link Unit} and a value that is given in
 * toMillis. As seen this uses a custom type of unit, to use {@link TimeUnit} see {@link
 * ClassicTime}.
 */
public class Time {

  /** The value of time */
  private final long value;
  /** The unit of time */
  @NonNull private final Unit unit;

  /**
   * Create a time instance. If we want the time to be "10 seconds" we would give the value as "10"
   * and the unit is {@link Unit#SECONDS}
   *
   * @param value the value of time
   * @param unit the unit of time
   */
  public Time(final long value, @NonNull final Unit unit) {
    this.value = value;
    this.unit = unit;
  }

  /**
   * Parse a string of time: Should look like "1s" = one second. It will take the last character of
   * the string and get the unit using {@link Unit#fromChar(char)}, the rest of the string will be
   * parsed as a long using {@link Long#parseLong(String)}
   *
   * @param string the string to parse
   * @return a new time instance if correctly formatted
   * @throws IllegalArgumentException if the string is null, if the long cannot be parsed, if the
   *     unit cannot be parsed
   */
  public static Time fromString(final String string) {
    if (string == null || string.isEmpty())
      throw new IllegalArgumentException("Cannot get time from a null string");
    final long value = Long.parseLong(string.substring(0, string.length() - 1));
    final Unit unit = Unit.fromChar(string.charAt(string.length() - 1));
    return new Time(value, unit);
  }

  /**
   * Get a time instance using toMillis. It will create a new instance of time with <br>
   * {@link Unit#MILLISECONDS} as the provided value is given in toMillis
   *
   * @param millis the toMillis that will be used as the value of time.
   * @return the new time instance
   */
  public static Time fromMillis(long millis) {
    return new Time(millis, Unit.MILLISECONDS);
  }

  /**
   * Get this same time but in other unit. It will also change the value depending on the unit
   * provided dividing this instance {@link #millis()} with the param {@link Unit#millis()}. For
   * example:
   *
   * <p>If we have 60 seconds and we want to get them as {@link Unit#MINUTES} this will be the
   * operation:
   *
   * <p>time = (60 * 1000) / (1 * 60000) time = 1
   *
   * <p>That is 1 minute and the time instance will have the value 1 and the unit {@link
   * Unit#MINUTES}
   *
   * @param unit the unit to convert this time to
   * @return the time with the new unit and value
   */
  public Time getAs(@NonNull final Unit unit) {
    return new Time(this.millis() / unit.millis(), unit);
  }

  /**
   * Get this time in toMillis.
   *
   * <p>Basically multiply {@link #value} with the {@link Unit#millis()}
   *
   * @return the time in toMillis as a long
   */
  public long millis() {
    return this.value * this.unit.millis();
  }

  /**
   * Get the next date since today using this time. Basically using {@link #nextDateMillis()} it
   * will be converted in {@link LocalDateTime} using {@link TimeUtils#getLocalDateFromMillis(long)}
   *
   * @return the next date since right now using this time instance
   */
  public LocalDateTime nextDate() {
    return TimeUtils.getLocalDateFromMillis(this.nextDateMillis());
  }

  /**
   * Get the previous date since today using this time. Basically current {@link
   * #previousDateMillis()} it will be converted in {@link LocalDateTime} using {@link
   * TimeUtils#getLocalDateFromMillis(long)}
   *
   * @return the previous date since right now using this time instance
   */
  public LocalDateTime previousDate() {
    return TimeUtils.getLocalDateFromMillis(this.previousDateMillis());
  }

  /**
   * Get the next offset date since today using this time. Just like {@link #nextDate()} and using
   * {@link TimeUtils#getOffsetDateFromMillis(long)}
   *
   * @return the next date since right now using this time instance
   */
  public OffsetDateTime nextDateOffset() {
    return TimeUtils.getOffsetDateFromMillis(this.nextDateMillis());
  }

  /**
   * s Get the previous offset date since today using this time. Just like {@link #previousDate()}
   * ()} and using {@link TimeUtils#getOffsetDateFromMillis(long)}
   *
   * @return the previous date since right now using this time instance
   */
  public OffsetDateTime previousDateOffset() {
    return TimeUtils.getOffsetDateFromMillis(this.previousDateMillis());
  }

  /**
   * Get the next date since today in toMillis. Calculated as follows
   *
   * <p>next date = {@link System#currentTimeMillis()} + {@link #millis()}
   *
   * @return the next date in toMillis
   */
  private long nextDateMillis() {
    return System.currentTimeMillis() + this.millis();
  }

  /**
   * Get the previous date since today in toMillis. Calculated as follows
   *
   * <p>previous date = {@link System#currentTimeMillis()} - {@link #millis()}
   *
   * @return the previous date in toMillis
   */
  private long previousDateMillis() {
    return System.currentTimeMillis() - this.millis();
  }

  /**
   * Get an effective string and readable string of time.
   *
   * <p>This means that if there is "60 seconds" the effective string will be "1 minute"
   *
   * @return the effective string for this time
   */
  @NonNull
  public String toEffectiveString() {
    return this.getAs(Unit.fromMillis(this.millis())).toString();
  }

  /**
   * Get a string that can be used in {@link Time#fromString(String)}
   *
   * @return the string that can be saved for later parsing
   */
  @NonNull
  public String toDatabaseString() {
    return this.value + this.unit.getSimple();
  }

  /**
   * Get the time as {@link ClassicTime}. The current unit will be converted to {@link TimeUnit}
   * using {@link Unit#toTimeUnit()} and the value (if the unit is one that is not in classic time)
   * with {@link #getValue(TimeUnit)})}
   *
   * @return this instance of time as classic time
   */
  @NonNull
  public ClassicTime toClassicTime() {
    TimeUnit newUnit = this.unit.toTimeUnit();
    long newValue = this.getValue(newUnit);
    return new ClassicTime(newValue, newUnit);
  }

  /**
   * Just like {@link Time#getValue()} but getting as another java unit
   *
   * @param unit the unit to get the value from
   * @return the value as certain unit
   */
  public long getValue(@NonNull TimeUnit unit) {
    return this.getAs(Unit.fromTimeUnit(unit)).getValue();
  }

  /**
   * Just like {@link Time#getValue()} but getting the value as another unit. First getting time
   * instance using {@link #getAs(Unit)} and getting the value of the instance
   *
   * @param unit the unit to get the value as
   * @return the value as the given unit
   */
  public long getValue(@NonNull Unit unit) {
    return this.getAs(unit).getValue();
  }

  /**
   * Get the value of this time instance
   *
   * @return the value of this time instance
   */
  public long getValue() {
    return this.value;
  }

  /**
   * Subtract this time with another. Calculated as follows:
   *
   * <p>time = {@link #millis()} - param {@link #millis()}
   *
   * @param time the time to subtract with
   * @return the subtraction and it will not be negative it would be 0
   */
  @NonNull
  public Time subtract(@NonNull Time time) {
    long millis = this.millis() - time.millis();
    return Time.fromMillis(millis < 0 ? 0 : millis);
  }

  /**
   * Sums this time with another one. Calculated as follows:
   *
   * <p>time = {@link #millis()} - param {@link #millis()}
   *
   * @param time the time to sum with
   * @return the result of the operation
   */
  @NonNull
  public Time sum(@NonNull Time time) {
    return Time.fromMillis(this.millis() + time.millis());
  }

  /**
   * Compares if this time is greater than another time instance. Calculated as follows:
   *
   * @param time the time to check if is lower than this instance
   * @return true if the parameter time is lower than this instance
   */
  public boolean greaterThan(@NonNull Time time) {
    return this.millis() > time.millis();
  }

  /**
   * Compares if this time is lower than another time
   *
   * @param time the time to check if is higher than this instance
   * @return true if the parameter time is higher than this instance
   */
  public boolean lowerThan(@NonNull Time time) {
    return this.millis() < time.millis();
  }

  /**
   * Get the unit of this time instance
   *
   * @return the unit of this time instance
   */
  @NonNull
  public Unit getUnit() {
    return this.unit;
  }

  /**
   * Get the time as duration
   *
   * @return the duration given from the millis of this time
   */
  @NonNull
  public Duration toDuration() {
    return Duration.ofMillis(this.millis());
  }

  @Override
  public String toString() {
    return this.value + " " + this.unit.toString().toLowerCase();
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object instanceof Long) {
      return this.millis() == (long) object;
    }
    if (object instanceof Time) {
      return this.millis() == ((Time) object).millis();
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    int result = (int) (this.value ^ (this.value >>> 32));
    result = 31 * result + this.unit.hashCode();
    return result;
  }
}
