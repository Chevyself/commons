package me.googas.starbox.gameplay.match;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.googas.annotations.Nullable;
import me.googas.commons.builder.ToStringBuilder;
import me.googas.starbox.Starbox;
import me.googas.starbox.events.gameplay.match.MatchPreStatusUpdateEvent;
import me.googas.starbox.events.gameplay.match.MatchStatusUpdatedEvent;
import me.googas.starbox.gameplay.MiniGame;
import me.googas.starbox.gameplay.entity.MatchEntity;
import me.googas.starbox.gameplay.module.MatchModuleRegistry;
import me.googas.starbox.gameplay.world.MatchWorld;
import me.googas.starbox.modules.gameplay.MatchMakingModule;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Match {

  @NonNull @Getter private final UUID uniqueId;
  @NonNull @Getter private final List<MatchWorld> worlds;
  @NonNull @Getter private final MatchScheduler scheduler;
  @NonNull @Getter private final MatchSettings settings;
  @NonNull @Getter private final MiniGame miniGame;
  @NonNull @Getter private final MatchModuleRegistry modules;
  @NonNull @Getter private final File directory;
  @NonNull @Getter private MatchState state = MatchState.LOADING;
  @Nullable @Getter @Setter private MatchEntity winner;

  public Match(
      @NonNull UUID uniqueId,
      @NonNull List<MatchWorld> worlds,
      @NonNull MatchScheduler scheduler,
      @NonNull MatchSettings settings,
      @NonNull MiniGame miniGame,
      @NonNull MatchModuleRegistry modules,
      @NonNull File directory) {
    this.uniqueId = uniqueId;
    this.worlds = worlds;
    this.scheduler = scheduler;
    this.settings = settings;
    this.miniGame = miniGame;
    this.modules = modules;
    this.directory = directory;
  }

  public boolean setState(@NonNull MatchState state) {
    if (new MatchPreStatusUpdateEvent(this, state).not()) {
      this.state = state;
      new MatchStatusUpdatedEvent(this, state).call();
      return true;
    }
    return false;
  }

  @NonNull
  public Match prepare() {
    this.scheduler.setMatch(this);
    this.scheduler.start();
    this.modules.prepare(this);
    return this;
  }

  @NonNull
  public Match load() {
    if (this.setState(MatchState.WAITING)) {
      this.modules.engage();
    }
    return this;
  }

  @NonNull
  public Match unload() {
    if (this.setState(MatchState.UNLOADED)) {
      this.scheduler.stop();
      this.modules.disengage();
    }
    return this;
  }

  @NonNull
  public List<MatchEntity> getParticipants() {
    return Starbox.getModuleRegistry().require(MatchMakingModule.class).getParticipants(this);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("uniqueId", this.uniqueId)
        .append("worlds", this.worlds)
        .append("settings", this.settings)
        .append("miniGame", this.miniGame)
        .append("modules", this.modules)
        .append("directory", this.directory)
        .append("state", this.state)
        .build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || this.getClass() != o.getClass()) return false;
    Match match = (Match) o;
    return this.uniqueId.equals(match.uniqueId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.uniqueId);
  }
}
