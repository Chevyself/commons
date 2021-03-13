package me.googas.starbox.gameplay.exception;

import lombok.NonNull;

/** An exception thrown when a game could not be loaded */
public class GameLoadException extends GameException {

  /**
   * Throw the game exception
   *
   * @param message the reason why t
   */
  public GameLoadException(@NonNull String message) {
    super(message);
  }
}
