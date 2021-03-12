package me.googas.starbox.events.gameplay.entity.team;

import lombok.Getter;
import lombok.NonNull;
import me.googas.starbox.gameplay.entity.MatchEntity;
import me.googas.starbox.gameplay.entity.player.MatchTeam;

/** This event is called when an entity joins the team */
public class MatchTeamJoinEvent extends MatchTeamEvent {

  @NonNull @Getter private final MatchEntity entity;

  /**
   * Create the event
   *
   * @param team the team to which the entity joined
   * @param entity the entity that joined the team
   */
  public MatchTeamJoinEvent(@NonNull MatchTeam team, @NonNull MatchEntity entity) {
    super(team);
    this.entity = entity;
  }
}
