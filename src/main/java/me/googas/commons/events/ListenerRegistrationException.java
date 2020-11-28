package me.googas.commons.events;

import lombok.NonNull;

/** Thrown when a listener cannot be registered */
public class ListenerRegistrationException extends RuntimeException {

  /**
   * Throw a simple runtime exception
   *
   * @param message the message
   */
  public ListenerRegistrationException(@NonNull String message) {
    super(message);
  }
}
