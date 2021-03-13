package me.googas.starbox.gameplay.exception;

import com.starfishst.core.exceptions.type.SimpleException;
import lombok.NonNull;

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
