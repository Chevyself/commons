package me.googas.commons.gameplay.module;

import me.googas.annotations.Nullable;
import me.googas.commons.gameplay.match.Match;
import me.googas.commons.modules.Module;

/** This interface represents module which may be controlled by a match */
public interface MatchModule extends Module {

  /**
   * Set the match in which this module operates
   *
   * @param match the match
   * @return true if the match was set
   */
  boolean setMatch(@Nullable Match match);

  /**
   * Get the match which this module is operating
   *
   * @return the match
   */
  @Nullable
  Match getMatch();
}
