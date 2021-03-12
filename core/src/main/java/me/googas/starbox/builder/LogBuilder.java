package me.googas.starbox.builder;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Delegate;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/** This helps build logs to later send in a logger */
public class LogBuilder implements Builder<LogRecord> {

  /** The level of logging to use */
  @NonNull @Getter private final Level level;

  /** The buffer that is building the string message */
  @NonNull @Delegate @Getter private final StringBuffer buffer;

  /**
   * Create the log builder
   *
   * @param level the level to log the message
   * @param buffer the string buffer to build the message
   * @param initial the initial message of the log
   */
  public LogBuilder(@NonNull Level level, @NonNull StringBuffer buffer, Object initial) {
    this.level = level;
    this.buffer = buffer;
    if (initial != null) this.buffer.append(initial);
  }

  /**
   * Create the log builder
   *
   * @param level the level to log the message
   * @param initial the initial message of the log
   */
  public LogBuilder(@NonNull Level level, Object initial) {
    this(level, new StringBuffer(), initial);
  }

  /**
   * Create the log builder
   *
   * @param level the level to log the message
   */
  public LogBuilder(@NonNull Level level) {
    this(level, null);
  }

  /**
   * Create the log builder
   *
   * @param initial the initial message of the log
   */
  public LogBuilder(Object initial) {
    this(Level.FINEST, new StringBuffer(), initial);
  }

  /** Create a log builder with level {@link Level#FINEST} */
  public LogBuilder() {
    this(Level.FINEST);
  }

  /**
   * Send the composed message to the logger
   *
   * @param logger the logger in which the message will be logged
   */
  public void send(@NonNull Logger logger) {
    logger.log(this.build());
  }

  /**
   * Build the requested type object
   *
   * @return the built object
   */
  @NonNull
  @Override
  public LogRecord build() {
    return new LogRecord(this.level, this.buffer.toString());
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("level", this.level)
        .append("buffer", this.buffer)
        .build();
  }
}
