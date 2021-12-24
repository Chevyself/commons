package me.googas.commons.events.gameplay.entity.player;

import lombok.Getter;
import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.commons.gameplay.entity.player.MatchPlayer;
import me.googas.commons.gameplay.match.Match;

/** This event is called after a player has changed from match */
public class MatchPlayerSetMatchEvent extends MatchPlayerEvent {

  @Nullable @Getter private final Match match;

  /**
   * Create the event
   *
   * @param player the player that changed matches
   * @param match the new match which the player is on
   */
  public MatchPlayerSetMatchEvent(@NonNull MatchPlayer player, @Nullable Match match) {
    super(player);
    this.match = match;
  }
}
