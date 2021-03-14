package me.googas.starbox.gameplay.exception;

import lombok.NonNull;
import me.googas.commands.exceptions.type.SimpleException;

/** Creates an exception that has a game involved */
public class GameException extends SimpleException {

  /**
   * Throw the game exception
   *
   * @param message the reason why t
   */
  public GameException(@NonNull String message) {
    super(message);
  }
}
