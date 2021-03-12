package me.googas.starbox.events.gameplay.match;

import lombok.Getter;
import lombok.NonNull;
import me.googas.starbox.events.StarboxEvent;
import me.googas.starbox.gameplay.match.Match;

/** This event is fired when a match is involved */
public class MatchEvent extends StarboxEvent {

  @NonNull @Getter private final Match match;

  /**
   * Create the event
   *
   * @param match the match that is involved in the event
   */
  public MatchEvent(@NonNull Match match) {
    this.match = match;
  }
}
