package me.googas.commons.events.gameplay.entity.team;

import lombok.Getter;
import lombok.NonNull;
import me.googas.commons.gameplay.entity.MatchEntity;
import me.googas.commons.gameplay.entity.player.MatchTeam;

/** This event is called when an entity leaves a team */
public class MatchTeamLeaveEvent extends MatchTeamEvent {
  @NonNull @Getter private final MatchEntity entity;

  /**
   * Create the event
   *
   * @param team the team that the entity has left
   * @param entity the entity that left the team
   */
  public MatchTeamLeaveEvent(@NonNull MatchTeam team, @NonNull MatchEntity entity) {
    super(team);
    this.entity = entity;
  }
}
