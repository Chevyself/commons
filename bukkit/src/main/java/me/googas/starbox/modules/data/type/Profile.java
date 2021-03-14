package me.googas.starbox.modules.data.type;

import java.util.UUID;
import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.starbox.cache.Catchable;
import me.googas.starbox.channels.Channel;
import me.googas.starbox.modules.placeholders.Line;
import me.googas.starbox.utility.Players;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * This object represents the data of a {@link org.bukkit.entity.Player} which may have money,
 * permissions, cosmetics, etc.
 */
public interface Profile
    extends Catchable, Data, Account, Permissible, Decorative, Stateable, Channel {

  @NonNull
  String getName();

  /**
   * Get the unique id of the player
   *
   * @return the unique id of the player
   */
  @NonNull
  UUID getUniqueId();

  /**
   * Get the offline player of this profile
   *
   * @return the offline player
   */
  @NonNull
  default OfflinePlayer getOfflinePlayer() {
    return Bukkit.getOfflinePlayer(this.getUniqueId());
  }

  /**
   * Get the player of this profile or null if it is not online
   *
   * @return the player or null if not online
   */
  @Nullable
  default Player getPlayer() {
    return Bukkit.getPlayer(this.getUniqueId());
  }

  @Override
  default void sendMessage(@NonNull String string) {
    Player player = this.getPlayer();
    if (player != null) player.sendMessage(string);
  }

  @Override
  default void sendLine(@NonNull Line line) {
    Player player = this.getPlayer();
    if (player != null) player.sendMessage(line.build(player));
  }

  @Override
  default @NonNull String getLocale() {
    Player player = this.getPlayer();
    return player == null ? "en" : Players.getLocale(player);
  }
}
