package me.googas.messaging;

import lombok.NonNull;

/** Handles the exceptions in messengers */
public interface ThrowableHandler {

  /**
   * Handle the thrown throwable
   *
   * @param e a given throwable to handle
   */
  void handle(@NonNull Throwable e);
}
