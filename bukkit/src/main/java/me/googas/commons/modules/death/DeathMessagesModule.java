package me.googas.commons.modules.death;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.googas.commons.events.player.StarboxPlayerDeathEvent;
import me.googas.commons.maps.Maps;
import me.googas.commons.modules.Module;
import me.googas.commons.modules.placeholders.Line;
import me.googas.commons.modules.placeholders.LocalizedLine;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class DeathMessagesModule implements Module {

  @NonNull private final Map<EntityDamageEvent.DamageCause, String> paths = new HashMap<>();
  @NonNull @Getter @Setter private SendCheck sendCheck = (player, event) -> true;

  @EventHandler
  public void onStarboxPlayerDeath(StarboxPlayerDeathEvent event) {
    Player player = event.getPlayer();
    Entity killer = event.getKiller();
    Line line;
    Map<String, String> map = Maps.singleton("name", event.getPlayer().getDisplayName());
    if (killer != null) {
      if (killer instanceof Player) {
        map.put("killer", ((Player) killer).getDisplayName());
      } else {
        map.put("killer", killer.getName());
      }
    }
    switch (event.getCause()) {
      case BLOCK_EXPLOSION:
      case ENTITY_EXPLOSION:
        if (killer != null) {
          if (killer == player.getPlayer()) {
            line = new LocalizedLine("death.blow.self", map);
          } else {
            line = new LocalizedLine("death.blow.other", map);
          }
        } else {
          line = new LocalizedLine("death.blow.none", map);
        }
        break;
      case ENTITY_ATTACK:
        if (event.getKiller() != null) {
          line = new LocalizedLine("death.slain.other", map);
        } else {
          line = new LocalizedLine("death.slain.none", map);
        }
        break;
      case FALL:
        int fallDistance =
            Math.round(
                player.isInsideVehicle()
                    ? Objects.requireNonNull(player.getVehicle()).getFallDistance()
                    : player.getFallDistance());
        map.put("fall-distance", String.valueOf(fallDistance));
        line = new LocalizedLine("death.fall", map);
        break;
      case PROJECTILE:
        if (killer != null) {
          int distance = (int) Math.round(player.getLocation().distance(killer.getLocation()));
          map.put("distance", String.valueOf(distance));
          if (distance == 1) {
            line = new LocalizedLine("death.projectile.single", map);
          } else {
            line = new LocalizedLine("death.projectile.blocks", map);
          }
        } else {
          line = new LocalizedLine("death.projectile.no-killer", map);
        }
        break;
      case MAGIC:
        if (event.getKiller() != null) {
          line = new LocalizedLine("death.magic.other", map);
        } else {
          line = new LocalizedLine("death.magic.none", map);
        }
        break;
      default:
        line = new LocalizedLine(this.paths.getOrDefault(event.getCause(), "death.generic"), map);
        break;
    }
    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
      if (this.sendCheck.shouldSee(onlinePlayer, event)) {
        player.sendMessage(line.build(player));
      }
    }
  }

  @Override
  public @NonNull String getName() {
    return "Death Messages";
  }

  public interface SendCheck {
    boolean shouldSee(@NonNull Player player, @NonNull StarboxPlayerDeathEvent event);
  }
}
