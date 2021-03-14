package me.googas.starbox.modules.channels;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.googas.annotations.Nullable;
import me.googas.starbox.channels.Channel;
import me.googas.starbox.modules.placeholders.Line;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChannelsModule {

  @NonNull @Getter private final List<GlobalChannelProvider> channelProviders = new ArrayList<>();
  @NonNull @Getter @Setter private Channel fallbackChannel = new DefaultGlobalChannel();

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onMessageSent(AsyncPlayerChatEvent event) {
    if (event.isCancelled()) return;
    event.setCancelled(true);
    Line line = this.getLine(event.getPlayer(), event);
    if (line != null) this.getChannel(event.getPlayer()).sendLine(line);
  }

  @NonNull
  public Channel getChannel(@NonNull OfflinePlayer player) {
    for (GlobalChannelProvider provider : this.channelProviders) {
      Channel provided = provider.provide(player);
      if (provided != null) return provided;
    }
    return this.fallbackChannel;
  }

  @Nullable
  public Line getLine(@NonNull OfflinePlayer player, @NonNull AsyncPlayerChatEvent event) {
    for (GlobalChannelProvider provider : this.channelProviders) {
      Line line = provider.provideLine(player, event);
      if (line != null) return line;
    }
    if (this.fallbackChannel instanceof DefaultGlobalChannel) {
      return ((DefaultGlobalChannel) this.fallbackChannel).provideLine(player, event);
    }
    return null;
  }
}
