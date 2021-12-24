package me.googas.commons.events.player;

import lombok.NonNull;
import org.bukkit.entity.Player;

/** Called when the player respawns in the lobby */
public class PlayerLobbyRespawnEvent extends StarboxPlayerEvent {

  /**
   * Create the event
   *
   * @param player the player that respawned in the lobby
   */
  public PlayerLobbyRespawnEvent(@NonNull Player player) {
    super(player);
  }
}
