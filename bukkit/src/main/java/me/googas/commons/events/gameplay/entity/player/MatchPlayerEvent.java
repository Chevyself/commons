package me.googas.commons.events.gameplay.entity.player;

import lombok.Getter;
import lombok.NonNull;
import me.googas.commons.events.StarboxEvent;
import me.googas.commons.gameplay.entity.player.MatchPlayer;

/** This event is called when a player is involved */
public class MatchPlayerEvent extends StarboxEvent {

  @NonNull @Getter private final MatchPlayer player;

  /**
   * Create the event
   *
   * @param player the match player involved in the event
   */
  public MatchPlayerEvent(@NonNull MatchPlayer player) {
    this.player = player;
  }
}
