package me.googas.commons.modules.placeholders;

import lombok.NonNull;
import org.bukkit.OfflinePlayer;

/** A place holder */
public interface Placeholder {

  /**
   * Builds the placeholder for the player
   *
   * @param player the player to get the placeholder built
   * @return the built placeholder
   */
  @NonNull
  String build(@NonNull OfflinePlayer player);

  /**
   * Get the name of the placeholder
   *
   * @return the name of the placeholder
   */
  @NonNull
  String getName();
}
