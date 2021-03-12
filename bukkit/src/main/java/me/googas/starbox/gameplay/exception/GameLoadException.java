package me.googas.starbox.gameplay.exception;

import org.jetbrains.annotations.NotNull;

/** An exception thrown when a game could not be loaded */
public class GameLoadException extends GameException {

  /**
   * Throw the game exception
   *
   * @param message the reason why t
   */
  public GameLoadException(@NotNull String message) {
    super(message);
  }
}
