package me.googas.commons.gameplay.entity;

import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.commons.channels.Channel;
import me.googas.commons.gameplay.match.Match;
import me.googas.commons.gameplay.world.MatchWorld;
import org.bukkit.potion.PotionEffect;

public interface MatchEntity extends Channel {

  /**
   * Set the match where the entity is participating
   *
   * @param match the match where the entity is participating
   * @return the match
   */
  boolean setMatch(@Nullable Match match);

  /**
   * Teleport this entity to the given world and desire whether to make the teleport random
   *
   * @param world the world to teleport the entity to
   * @param random whether the location should be random
   */
  void teleport(@NonNull MatchWorld world, boolean random);

  /**
   * Add a potion effect to this entity
   *
   * @param effect the effect to add
   * @return whether the potion effect was applied
   */
  boolean addPotionEffect(@NonNull PotionEffect effect);

  /**
   * Get the name or a way to identify the entity
   *
   * @return the name of the entity
   */
  @NonNull
  String getName();

  /**
   * Get the match where the entity is participating
   *
   * @return the match
   */
  @Nullable
  Match getMatch();

  /**
   * Get the state of the entity
   *
   * @return the state
   */
  MatchEntityState getState();
}
