package me.googas.commons.events.player;

import lombok.Getter;
import lombok.NonNull;
import me.googas.annotations.Nullable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * This even is called upon player death from the module {@link
 * me.googas.commons.modules.death.DeathModule}. The death module overrides the player death to
 * create different kinds of deaths for Matches or Mini-games
 */
public class StarboxPlayerDeathEvent extends StarboxPlayerEvent {

  @Nullable @Getter private final Entity killer;
  @NonNull @Getter private final EntityDamageEvent.DamageCause cause;

  /**
   * Create the event
   *
   * @param player the player that was killed
   * @param killer the killer of the player could be null if the player died due to natural reasons
   * @param cause the cause of the player death
   */
  public StarboxPlayerDeathEvent(
      @NonNull Player player,
      @Nullable Entity killer,
      @NonNull EntityDamageEvent.DamageCause cause) {
    super(player);
    this.killer = killer;
    this.cause = cause;
  }
}
