package me.googas.messaging.api;

import lombok.NonNull;
import me.googas.annotations.Nullable;

/** Thrown when the {@link Messenger} fails to {@link Messenger#listen()} */
public class MessengerListenFailException extends Exception {

  /**
   * Create the exception
   *
   * @param message the message to why it failed
   */
  public MessengerListenFailException(@Nullable String message) {
    super(message);
  }

  /**
   * Create the exception
   *
   * @param message the message to why it failed
   * @param cause the throwable that made if fail
   */
  public MessengerListenFailException(@Nullable String message, @NonNull Throwable cause) {
    super(message, cause);
  }
}
