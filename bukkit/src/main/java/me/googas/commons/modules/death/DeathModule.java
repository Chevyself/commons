package me.googas.commons.modules.death;

import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.commons.events.player.StarboxPlayerDeathEvent;
import me.googas.commons.modules.Module;
import me.googas.commons.utility.Players;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class DeathModule implements Module {

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onEntityDamage(EntityDamageEvent event) {
    if (this.survives(event)) return;
    Player player = (Player) event.getEntity();
    Players.setHealthToMax(player);
    player.setLastDamageCause(event);
    this.killPlayer(player, null, event.getCause());
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
    if (this.survives(event)) return;
    Player player = (Player) event.getEntity();
    Players.setHealthToMax(player);
    player.setLastDamageCause(event);
    if (event.getDamager() instanceof Projectile) {
      this.killPlayer(
          player, (Entity) ((Projectile) event.getDamager()).getShooter(), event.getCause());
    } else {
      this.killPlayer(player, event.getDamager(), event.getCause());
    }
  }

  public boolean survives(@NonNull EntityDamageEvent event) {
    if (event.isCancelled() || !(event.getEntity() instanceof Player)) return true;
    Player player = (Player) event.getEntity();
    return !(player.getHealth() - event.getFinalDamage() < 0.01);
  }

  private void killPlayer(
      @NonNull Player player,
      @Nullable Entity killer,
      @NonNull EntityDamageEvent.DamageCause cause) {
    Location deathLocation = player.getLocation();
    new StarboxPlayerDeathEvent(player, killer, cause).call();
    for (ItemStack item : player.getInventory()) {
      player.getWorld().dropItem(deathLocation, item);
    }
    player.getInventory().clear();
  }

  @Override
  public @NonNull String getName() {
    return "Death";
  }
}
