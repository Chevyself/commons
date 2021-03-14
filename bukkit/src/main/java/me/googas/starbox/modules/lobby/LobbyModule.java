package me.googas.starbox.modules.lobby;

import lombok.Getter;
import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.starbox.Starbox;
import me.googas.starbox.events.player.PlayerLobbyRespawnEvent;
import me.googas.starbox.gameplay.match.Match;
import me.googas.starbox.gameplay.match.MatchState;
import me.googas.starbox.math.geometry.Point;
import me.googas.starbox.math.geometry.Shape;
import me.googas.starbox.modules.Module;
import me.googas.starbox.modules.gameplay.MatchMakingModule;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class LobbyModule implements Module {

  @NonNull @Getter private World lobby = Bukkit.getWorlds().get(0);
  @Nullable @Getter private Shape arena;

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    MatchMakingModule matchMaking = Starbox.getModuleRegistry().get(MatchMakingModule.class);
    if (matchMaking != null) {
      Match match = matchMaking.getPlayer(player).getMatch();
      if (match != null && match.getState() != MatchState.WAITING) return;
    }
    player.teleport(this.lobby.getSpawnLocation());
    new PlayerLobbyRespawnEvent(player);
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    if (!this.lobby.equals(event.getBlock().getWorld())) return;
    if (event.getPlayer().getGameMode() != GameMode.CREATIVE) event.setCancelled(true);
  }

  @EventHandler
  public void onBlockPlace(BlockPlaceEvent event) {
    if (!this.lobby.equals(event.getBlock().getWorld())) return;
    if (event.getPlayer().getGameMode() != GameMode.CREATIVE) event.setCancelled(true);
  }

  @EventHandler
  public void onPLayerFoodLevelChange(FoodLevelChangeEvent event) {
    if (!this.lobby.equals(event.getEntity().getWorld())) return;
    event.setCancelled(true);
  }

  @EventHandler
  public void onEntityInteract(EntityInteractEvent event) {
    if (!this.lobby.equals(event.getEntity().getWorld())) return;
    event.setCancelled(true);
  }

  @EventHandler
  public void onEntityDamage(EntityDamageEvent event) {
    if (!this.lobby.equals(event.getEntity().getWorld())) return;
    if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
      event.setCancelled(true);
      event.getEntity().teleport(this.lobby.getSpawnLocation());
      return;
    }
    Location location = event.getEntity().getLocation();
    if (this.arena == null
        || !this.arena.contains(new Point(location.getX(), location.getY(), location.getZ()))) {
      event.setCancelled(true);
    }
  }

  @NonNull
  public LobbyModule setLobby(World lobby) {
    this.lobby = lobby;
    return this;
  }

  @NonNull
  public LobbyModule setArena(@Nullable Shape arena) {
    this.arena = arena;
    return this;
  }

  @Override
  public @NonNull String getName() {
    return "Lobby";
  }
}
