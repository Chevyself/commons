package me.googas.commons.gameplay.exception;

import lombok.NonNull;
import me.googas.commands.exceptions.type.StarboxException;

/** Creates an exception that has a game involved */
public class GameException extends StarboxException {

  /**
   * Throw the game exception
   *
   * @param message the reason why t
   */
  public GameException(@NonNull String message) {
    super(message);
  }
}
