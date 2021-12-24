package me.googas.commons.gameplay;

import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.commons.gameplay.exception.GameLoadException;
import me.googas.commons.gameplay.match.Match;
import me.googas.commons.gameplay.match.MatchSettings;
import me.googas.commons.modules.ui.Displayable;
import org.bukkit.plugin.Plugin;

/**
 * This interface must be implemented by a mini-game which must be provided by some kind of plugin.
 * Mini-games have to be registered inside one match making module to matches an entities not clash
 */
public interface MiniGame extends Displayable {

  /**
   * Loads a new game for this mini-game
   *
   * @param settings the settings to start the game
   * @return the new loaded game
   * @throws GameLoadException if for some reason the game could not be loaded this really depends
   *     on each mini-game, perhaps the {@link MatchSettings} are wrong
   */
  @Nullable
  Match load(@NonNull MatchSettings settings) throws GameLoadException;

  /**
   * Get the name of the mini-game
   *
   * @return the name of the mini-game
   */
  @NonNull
  String getName();

  /**
   * Get the plugin in which this mini-game is running
   *
   * @return the plugin in which the mini-game is running
   */
  @NonNull
  Plugin getPlugin();
}
