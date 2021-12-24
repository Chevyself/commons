package me.googas.commons.events.gameplay.match;

import lombok.Getter;
import lombok.NonNull;
import me.googas.commons.gameplay.match.Match;
import me.googas.commons.gameplay.match.MatchState;

/** This event is called when a match changes from state */
public class MatchStatusUpdatedEvent extends MatchEvent {

  @NonNull @Getter private final MatchState state;

  /**
   * Create the event
   *
   * @param match the match that is changing from state
   * @param state the new state of the match
   */
  public MatchStatusUpdatedEvent(@NonNull Match match, @NonNull MatchState state) {
    super(match);
    this.state = state;
  }
}
