package me.googas.starbox.events.gameplay.match;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.googas.starbox.events.StarboxCancellable;
import me.googas.starbox.gameplay.match.Match;
import me.googas.starbox.gameplay.match.MatchState;

/** This event is called before a match changes from state */
public class MatchPreStatusUpdateEvent extends MatchEvent implements StarboxCancellable {

  @NonNull @Getter private final MatchState state;
  @Getter @Setter private boolean cancelled;

  /**
   * Create the event
   *
   * @param match the match that is changing from state
   * @param state the state to which the match is changing
   */
  public MatchPreStatusUpdateEvent(@NonNull Match match, @NonNull MatchState state) {
    super(match);
    this.state = state;
  }
}
