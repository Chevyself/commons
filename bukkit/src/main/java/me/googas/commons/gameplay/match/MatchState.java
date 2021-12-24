package me.googas.commons.gameplay.match;

public enum MatchState {
  /** When the game is starting */
  STARTING,
  /** When the game just started loading */
  LOADING,
  /** When the match is waiting for {@link me.googas.commons.gameplay.entity.MatchEntity} to join */
  WAITING,
  /** When the match has been unloaded already */
  UNLOADED,
  /** When the match is running */
  PLAYING,
  /** When the match has finished playing */
  FINISH
}
