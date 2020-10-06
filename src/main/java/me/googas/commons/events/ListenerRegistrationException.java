package me.googas.commons.events;

import org.jetbrains.annotations.NotNull;

/** Thrown when a listener cannot be registered */
public class ListenerRegistrationException extends RuntimeException {

  /**
   * Throw a simple runtime exception
   *
   * @param message the message
   */
  public ListenerRegistrationException(@NotNull String message) {
    super(message);
  }
}
