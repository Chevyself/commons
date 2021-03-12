package me.googas.starbox.gameplay.exception;

import com.starfishst.core.exceptions.type.SimpleException;
import org.jetbrains.annotations.NotNull;

/** Creates an exception that has a game involved */
public class GameException extends SimpleException {

  /**
   * Throw the game exception
   *
   * @param message the reason why t
   */
  public GameException(@NotNull String message) {
    super(message);
  }
}
