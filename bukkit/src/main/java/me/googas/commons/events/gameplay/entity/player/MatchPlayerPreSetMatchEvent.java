package me.googas.commons.events.gameplay.entity.player;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.googas.annotations.Nullable;
import me.googas.commons.events.StarboxCancellable;
import me.googas.commons.gameplay.entity.player.MatchPlayer;
import me.googas.commons.gameplay.match.Match;

/** This event is called before a player changes matches */
public class MatchPlayerPreSetMatchEvent extends MatchPlayerEvent implements StarboxCancellable {

  @Nullable @Getter private final Match match;
  @Getter @Setter private boolean cancelled;

  /**
   * Create the event
   *
   * @param player the player that is changing matches
   * @param match the match to which the player is changing
   */
  public MatchPlayerPreSetMatchEvent(@NonNull MatchPlayer player, @Nullable Match match) {
    super(player);
    this.match = match;
  }
}
