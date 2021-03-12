package me.googas.starbox.gameplay.entity;

public enum MatchEntityState {
  /** The state when a gaming entity is playing in a match */
  PLAYING,
  /** The state when a gaming entity is not in a match or doing anything */
  STANDARD,
  /** The state when a gaming entity has just joined in and is still loading */
  NONE,
  /** The state when a gaming entity is spectating the match */
  SPECTATING,
}
