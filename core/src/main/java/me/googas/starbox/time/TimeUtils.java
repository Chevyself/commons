package me.googas.starbox.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import lombok.NonNull;

/** Static utilities for time */
public class TimeUtils {

  /**
   * Get local date from toMillis.
   *
   * @param millis the toMillis to get the date from
   * @return the date
   */
  @NonNull
  public static LocalDateTime getLocalDateFromMillis(long millis) {
    return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDateTime();
  }

  /**
   * Get the offset date from toMillis
   *
   * @param millis the toMillis to get the offset date
   * @return the offset date
   */
  @NonNull
  public static OffsetDateTime getOffsetDateFromMillis(long millis) {
    return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toOffsetDateTime();
  }

  /**
   * Get the toMillis from a local date
   *
   * @param date to get the toMillis from
   * @return the toMillis
   */
  public static long getMillisFromLocalDate(@NonNull LocalDateTime date) {
    return date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
  }
  /**
   * Get the toMillis from a local date
   *
   * @param date to get the toMillis from
   * @return the toMillis
   */
  public static long getMillisFromOffsetDate(@NonNull OffsetDateTime date) {
    return date.toInstant().toEpochMilli();
  }

  /**
   * Get the time since a date
   *
   * @param date the date to get the time from
   * @return the time since a date
   */
  @NonNull
  public static Time getTimeFromToday(@NonNull LocalDateTime date) {
    return TimeUtils.getTimeDifference(
        date, TimeUtils.getLocalDateFromMillis(System.currentTimeMillis()));
  }

  /**
   * Get the time since an offset date
   *
   * @param date the date to get the time from
   * @return the time since a date
   */
  @NonNull
  public static Time getTimeFromToday(@NonNull OffsetDateTime date) {
    return TimeUtils.getTimeDifference(
        date, TimeUtils.getOffsetDateFromMillis(System.currentTimeMillis()));
  }

  /**
   * Get the time difference from two dates
   *
   * @param date the first date
   * @param compare the second date
   * @return the two dates to compare the time from
   */
  @NonNull
  public static Time getTimeDifference(
      @NonNull LocalDateTime date, @NonNull LocalDateTime compare) {
    long millis =
        TimeUtils.getMillisFromLocalDate(date) - TimeUtils.getMillisFromLocalDate(compare);
    if (millis < 0) millis *= -1;
    return Time.fromMillis(millis);
  }

  /**
   * Get the time difference from two offset dates
   *
   * @param date the first date
   * @param compare the second date
   * @return the two dates to compare the time from
   */
  public static Time getTimeDifference(
      @NonNull OffsetDateTime date, @NonNull OffsetDateTime compare) {
    long millis =
        TimeUtils.getMillisFromOffsetDate(date) - TimeUtils.getMillisFromOffsetDate(compare);
    if (millis < 0) millis *= -1;
    return Time.fromMillis(millis);
  }
}
