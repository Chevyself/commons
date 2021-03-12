package me.googas.starbox.events.gameplay.entity.team;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.googas.starbox.events.StarboxCancellable;
import me.googas.starbox.gameplay.entity.MatchEntity;
import me.googas.starbox.gameplay.entity.player.MatchTeam;

/** This team is called before an entity joins a team */
public class MatchTeamPreJoinEvent extends MatchTeamEvent implements StarboxCancellable {

  @NonNull @Getter private final MatchEntity entity;
  @Getter @Setter private boolean cancelled;

  /**
   * Create the event
   *
   * @param team the team that the entity is joining
   * @param entity the entity that is joining the team
   */
  public MatchTeamPreJoinEvent(@NonNull MatchTeam team, @NonNull MatchEntity entity) {
    super(team);
    this.entity = entity;
  }
}
