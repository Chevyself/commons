package me.googas.commons.modules.channels;

import java.util.Map;
import lombok.NonNull;
import me.googas.commons.Starbox;
import me.googas.commons.channels.Channel;
import me.googas.commons.maps.Maps;
import me.googas.commons.modules.placeholders.Line;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class DefaultGlobalChannel implements GlobalChannelProvider, Channel {

  @Override
  public void sendMessage(@NonNull String string) {
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
  public @NonNull String getLocale() {
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
