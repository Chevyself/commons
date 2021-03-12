package me.googas.starbox.modules.channels;

import lombok.NonNull;
import me.googas.commons.maps.Maps;
import me.googas.starbox.Starbox;
import me.googas.starbox.channels.Channel;
import me.googas.starbox.modules.placeholders.Line;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class DefaultGlobalChannel implements GlobalChannelProvider, Channel {

  @Override
  public void sendMessage(@NotNull String string) {
    for (Player player : Bukkit.getOnlinePlayers()) {
      player.sendMessage(string);
    }
  }

  @Override
  public void sendLocalized(@NonNull String path) {
    for (Player player : Bukkit.getOnlinePlayers()) {
      player.sendMessage(Starbox.getLanguageModule().getLanguage(player).get(path));
    }
  }

  @Override
  public void sendLocalized(@NonNull String path, @NonNull Map<String, String> placeholders) {
    for (Player player : Bukkit.getOnlinePlayers()) {
      player.sendMessage(Starbox.getLanguageModule().getLanguage(player).get(path, placeholders));
    }
  }

  @Override
  public void sendLine(@NonNull Line line) {
    for (Player player : Bukkit.getOnlinePlayers()) {
      player.sendMessage(line.build(player));
    }
  }

  @Override
  public @NotNull String getLocale() {
    return "en";
  }

  @NonNull
  @Override
  public Channel provide(@NonNull OfflinePlayer player) {
    return this;
  }

  @NonNull
  @Override
  public Line provideLine(@NonNull OfflinePlayer player, @NonNull AsyncPlayerChatEvent event) {
    return new Line(
        "&r<%display%&r>: %message%",
        Maps.builder("display", event.getPlayer().getDisplayName())
            .append("message", event.getMessage())
            .build());
  }
}