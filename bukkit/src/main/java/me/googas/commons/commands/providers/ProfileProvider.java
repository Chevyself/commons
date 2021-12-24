package me.googas.commons.commands.providers;

import java.util.List;
import lombok.NonNull;
import me.googas.commands.bukkit.context.CommandContext;
import me.googas.commands.bukkit.providers.type.BukkitArgumentProvider;
import me.googas.commands.exceptions.ArgumentProviderException;
import me.googas.commons.Starbox;
import me.googas.commons.modules.data.DataModule;
import me.googas.commons.modules.data.type.Profile;
import me.googas.commons.utility.Players;
import org.bukkit.OfflinePlayer;

public class ProfileProvider implements BukkitArgumentProvider<Profile> {

  @Override
  public @NonNull Class<Profile> getClazz() {
    return Profile.class;
  }

  @Override
  public @NonNull Profile fromString(@NonNull String s, @NonNull CommandContext context)
      throws ArgumentProviderException {
    OfflinePlayer player = context.get(s, OfflinePlayer.class, context);
    return Starbox.getModuleRegistry()
        .require(DataModule.class)
        .getPlayersHandler()
        .getPlayer(player);
  }

  @Override
  public @NonNull List<String> getSuggestions(@NonNull String s, CommandContext context) {
    return Players.getOnlinePlayersNames();
  }
}
