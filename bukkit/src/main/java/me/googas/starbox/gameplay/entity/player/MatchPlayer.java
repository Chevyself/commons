package me.googas.starbox.gameplay.entity.player;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Delegate;
import me.googas.annotations.Nullable;
import me.googas.commons.Validate;
import me.googas.commons.builder.ToStringBuilder;
import me.googas.starbox.events.gameplay.entity.player.MatchPlayerPreSetMatchEvent;
import me.googas.starbox.events.gameplay.entity.player.MatchPlayerSetMatchEvent;
import me.googas.starbox.gameplay.entity.MatchEntity;
import me.googas.starbox.gameplay.entity.MatchEntityState;
import me.googas.starbox.gameplay.match.Match;
import me.googas.starbox.gameplay.world.MatchWorld;
import me.googas.starbox.modules.placeholders.Line;
import me.googas.starbox.utility.Players;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class MatchPlayer implements MatchEntity {

  @NonNull @Getter private final UUID uniqueId;
  @NonNull @Getter private final MatchEntityState state = MatchEntityState.NONE;
  @Nullable @Getter private Match match;

  public MatchPlayer(@NonNull UUID uniqueId) {
    this.uniqueId = uniqueId;
  }

  @NonNull
  @Delegate(excludes = IgnoredMethods.class)
  public Player requirePlayer() {
    return Validate.notNull(
        this.getPlayer(), "Called #requirePlayer() without check if #isOnline()");
  }

  @NonNull
  public OfflinePlayer getOfflinePlayer() {
    return Validate.notNull(Bukkit.getOfflinePlayer(this.uniqueId), "Offline player was not found");
  }

  @Nullable
  public Player getPlayer() {
    return Bukkit.getPlayer(this.uniqueId);
  }

  public boolean isOnline() {
    return this.getOfflinePlayer().isOnline();
  }

  @Override
  public void sendLine(@NonNull Line line) {
    this.sendMessage(line.build(this.getOfflinePlayer()));
  }

  @Override
  public @NonNull String getLocale() {
    return this.isOnline() ? Players.getLocale(this.requirePlayer()) : "en";
  }

  @Override
  public boolean setMatch(@Nullable Match match) {
    if (new MatchPlayerPreSetMatchEvent(this, match).not()) {
      this.match = match;
      new MatchPlayerSetMatchEvent(this, match).call();
      return true;
    }
    return false;
  }

  @Override
  public void teleport(@NonNull MatchWorld world, boolean random) {
    if (!random) {
      this.teleport(world.getSpawnLocation());
    } else {
      this.teleport(world.getRandomLocation(true));
    }
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("uniqueId", this.uniqueId)
        .append("state", this.state)
        .append("match", this.match)
        .build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || this.getClass() != o.getClass()) return false;
    MatchPlayer that = (MatchPlayer) o;
    return this.uniqueId.equals(that.uniqueId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.uniqueId);
  }

  @SuppressWarnings("unused")
  private interface IgnoredMethods {
    Player getPlayer();

    String getLocale();

    UUID getUniqueId();

    boolean isOnline();
  }
}
