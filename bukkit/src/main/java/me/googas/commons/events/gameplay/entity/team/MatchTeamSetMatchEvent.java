package me.googas.commons.events.gameplay.entity.team;

import lombok.Getter;
import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.commons.gameplay.entity.player.MatchTeam;
import me.googas.commons.gameplay.match.Match;

/** This event is called when a team changes from match */
public class MatchTeamSetMatchEvent extends MatchTeamEvent {

  @Nullable @Getter private final Match match;

  /**
   * Create the event
   *
   * @param team the team that changed matches
   * @param match the match to which the team changed
   */
  public MatchTeamSetMatchEvent(@NonNull MatchTeam team, @Nullable Match match) {
    super(team);
    this.match = match;
  }
}
