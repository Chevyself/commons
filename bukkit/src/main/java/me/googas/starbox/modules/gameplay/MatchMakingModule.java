package me.googas.starbox.modules.gameplay;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.starbox.Lots;
import me.googas.starbox.gameplay.MiniGame;
import me.googas.starbox.gameplay.entity.MatchEntity;
import me.googas.starbox.gameplay.entity.MatchEntityState;
import me.googas.starbox.gameplay.entity.player.MatchPlayer;
import me.googas.starbox.gameplay.entity.player.MatchTeam;
import me.googas.starbox.gameplay.exception.GameLoadException;
import me.googas.starbox.gameplay.match.Match;
import me.googas.starbox.gameplay.match.MatchSettings;
import me.googas.starbox.gameplay.world.MatchWorld;
import me.googas.starbox.modules.Module;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLoginEvent;

public class MatchMakingModule implements Module {

  @NonNull @Getter private final List<MiniGame> miniGames = new ArrayList<>();
  @NonNull @Getter private final List<Match> matches = new ArrayList<>();
  @NonNull @Getter private final List<MatchEntity> entities = new ArrayList<>();

  @Nullable
  public MiniGame getMiniGame(@NonNull String name) {
    for (MiniGame miniGame : this.miniGames) {
      if (miniGame.getName().equalsIgnoreCase(name)) return miniGame;
    }
    return null;
  }

  @Nullable
  public Match load(@NonNull MiniGame miniGame, @NonNull MatchSettings settings)
      throws GameLoadException {
    Match match = miniGame.load(settings);
    if (match != null) this.matches.add(match);
    return match;
  }

  @Nullable
  public Match getMatch(@NonNull World world) {
    for (Match match : this.matches) {
      for (MatchWorld matchWorld : match.getWorlds()) {
        if (world.equals(matchWorld.getWorld())) return match;
      }
    }
    return null;
  }

  @Nullable
  public Match getMatch(@NonNull OfflinePlayer player) {
    MatchPlayer matchPlayer = this.getPlayer(player);
    for (Match match : this.matches) {
      if (this.contains(this.getParticipants(match), matchPlayer)) return match;
    }
    return null;
  }

  private boolean contains(@NonNull Collection<MatchEntity> entities, @NonNull MatchEntity entity) {
    for (MatchEntity matchEntity : entities) {
      if (matchEntity instanceof MatchTeam) {
        return this.contains(((MatchTeam) matchEntity).getMembers(), entity);
      } else {
        return matchEntity.equals(entity);
      }
    }
    return false;
  }

  @NonNull
  public List<MatchEntity> getParticipants(@NonNull Match match) {
    return this.getParticipants(match, MatchEntityState.values());
  }

  @NonNull
  public List<MatchEntity> getParticipants(
      @NonNull Match match, @NonNull MatchEntityState... states) {
    Set<MatchEntityState> query = Lots.set(states);
    List<MatchEntity> entities = new ArrayList<>();
    for (MatchEntity entity : this.entities) {
      if (match.equals(entity.getMatch()) && query.contains(entity.getState()))
        entities.add(entity);
    }
    return entities;
  }

  @NonNull
  public MatchPlayer getPlayer(@NonNull UUID uuid) {
    for (MatchEntity entity : this.entities) {
      if (entity instanceof MatchPlayer && ((MatchPlayer) entity).getUniqueId().equals(uuid))
        return (MatchPlayer) entity;
    }
    MatchPlayer player = new MatchPlayer(uuid);
    this.entities.add(player);
    return player;
  }

  @NonNull
  public MatchPlayer getPlayer(@NonNull OfflinePlayer player) {
    return this.getPlayer(player.getUniqueId());
  }

  @EventHandler(priority = EventPriority.NORMAL)
  public void onPlayerLogin(PlayerLoginEvent event) {
    // Add it to the list of entities
    this.getPlayer(event.getPlayer());
  }

  @Override
  public @NonNull String getName() {
    return "Match Making";
  }
}
