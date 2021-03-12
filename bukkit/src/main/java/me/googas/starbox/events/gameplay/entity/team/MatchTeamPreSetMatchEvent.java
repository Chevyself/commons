package me.googas.starbox.events.gameplay.entity.team;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.googas.annotations.Nullable;
import me.googas.starbox.events.StarboxCancellable;
import me.googas.starbox.gameplay.entity.player.MatchTeam;
import me.googas.starbox.gameplay.match.Match;

/** This event is called before a team changes from match */
public class MatchTeamPreSetMatchEvent extends MatchTeamEvent implements StarboxCancellable {

  @Nullable @Getter private final Match match;
  @Getter @Setter private boolean cancelled;

  /**
   * Create the event
   *
   * @param team the team that is changing matches
   * @param match the match to which the team is changing
   */
  public MatchTeamPreSetMatchEvent(@NonNull MatchTeam team, @Nullable Match match) {
    super(team);
    this.match = match;
  }
}
