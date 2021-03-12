package me.googas.starbox.commands.providers;

import com.starfishst.commands.bukkit.context.CommandContext;
import com.starfishst.commands.bukkit.providers.type.BukkitArgumentProvider;
import com.starfishst.core.exceptions.ArgumentProviderException;
import lombok.NonNull;
import me.googas.starbox.Starbox;
import me.googas.starbox.modules.data.DataModule;
import me.googas.starbox.modules.data.type.Profile;
import me.googas.starbox.utility.Players;
import org.bukkit.OfflinePlayer;

import java.util.List;

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
