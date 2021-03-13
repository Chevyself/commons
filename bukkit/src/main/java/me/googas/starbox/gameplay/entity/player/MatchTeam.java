package me.googas.starbox.gameplay.entity.player;

import lombok.Getter;
import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.commons.builder.ToStringBuilder;
import me.googas.starbox.events.gameplay.entity.team.MatchTeamJoinEvent;
import me.googas.starbox.events.gameplay.entity.team.MatchTeamLeaveEvent;
import me.googas.starbox.events.gameplay.entity.team.MatchTeamPreJoinEvent;
import me.googas.starbox.events.gameplay.entity.team.MatchTeamPreLeaveEvent;
import me.googas.starbox.events.gameplay.entity.team.MatchTeamPreSetMatchEvent;
import me.googas.starbox.events.gameplay.entity.team.MatchTeamSetMatchEvent;
import me.googas.starbox.gameplay.entity.MatchEntity;
import me.googas.starbox.gameplay.entity.MatchEntityState;
import me.googas.starbox.gameplay.match.Match;
import me.googas.starbox.gameplay.world.MatchWorld;
import me.googas.starbox.modules.placeholders.Line;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class MatchTeam implements MatchEntity {

  @NonNull @Getter private final UUID uniqueId;
  @NonNull @Getter private final List<MatchEntity> members = new ArrayList<>();
  @NonNull @Getter private final String name;
  @NonNull @Getter private final MatchEntityState state = MatchEntityState.NONE;
  @Nullable @Getter private Match match;

  public MatchTeam(@NonNull UUID uniqueId, @NonNull String name) {
    this.uniqueId = uniqueId;
    this.name = name;
  }

  public boolean join(@NonNull MatchEntity entity) {
    if (new MatchTeamPreJoinEvent(this, entity).not()) {
      this.members.add(entity);
      new MatchTeamJoinEvent(this, entity).call();
      return true;
    }
    return false;
  }

  public boolean leave(@NonNull MatchEntity entity) {
    if (new MatchTeamPreLeaveEvent(this, entity).not()) {
      this.members.add(entity);
      new MatchTeamLeaveEvent(this, entity).call();
      return true;
    }
    return false;
  }

  @Nullable
  public MatchEntity getLeader() {
    if (this.members.isEmpty()) {
      return null;
    }
    return this.members.get(0);
  }

  @Override
  public boolean setMatch(@Nullable Match match) {
    if (new MatchTeamPreSetMatchEvent(this, match).not()) {
      this.match = match;
      new MatchTeamSetMatchEvent(this, match);
      return true;
    }
    return false;
  }

  @Override
  public void teleport(@NonNull MatchWorld world, boolean random) {
    for (MatchEntity member : this.members) {
      member.teleport(world, random);
    }
  }

  @Override
  public boolean addPotionEffect(@NonNull PotionEffect effect) {
    boolean applied = false;
    for (MatchEntity member : this.members) {
      if (member.addPotionEffect(effect)) applied = true;
    }
    return applied;
  }

  @Override
  public void sendMessage(@NonNull String string, @NonNull Map<String, String> placeholders) {
    for (MatchEntity member : this.members) {
      member.sendMessage(string, placeholders);
    }
  }

  @Override
  public void sendMessage(@NonNull String string) {
    for (MatchEntity member : this.members) {
      member.sendMessage(string);
    }
  }

  @Override
  public void sendLine(@NonNull Line line) {
    for (MatchEntity member : this.members) {
      member.sendLine(line);
    }
  }

  @Override
  public void sendLocalized(@NonNull String path) {
    for (MatchEntity member : this.members) {
      member.sendLocalized(path);
    }
  }

  @Override
  public void sendLocalized(@NonNull String path, @NonNull Map<String, String> placeholders) {
    for (MatchEntity member : this.members) {
      member.sendLocalized(path, placeholders);
    }
  }

  @Override
  public @NonNull String getLocale() {
    return "en";
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("uniqueId", this.uniqueId)
        .append("members", this.members)
        .append("name", this.name)
        .append("state", this.state)
        .append("match", this.match)
        .build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || this.getClass() != o.getClass()) return false;
    MatchTeam matchTeam = (MatchTeam) o;
    return this.uniqueId.equals(matchTeam.uniqueId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.uniqueId);
  }
}
