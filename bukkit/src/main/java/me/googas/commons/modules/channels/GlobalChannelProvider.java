package me.googas.commons.modules.channels;

import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.commons.channels.Channel;
import me.googas.commons.modules.placeholders.Line;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Provides the message in which a global message is sent {@link
 * org.bukkit.event.player.AsyncPlayerChatEvent}
 */
public interface GlobalChannelProvider {
  @Nullable
  Channel provide(@NonNull OfflinePlayer player);

  @Nullable
  Line provideLine(@NonNull OfflinePlayer player, @NonNull AsyncPlayerChatEvent event);
}
