package me.googas.commons.compatibilities.vault;

import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.commons.modules.data.DataModule;
import me.googas.commons.modules.data.type.Profile;
import me.googas.commons.modules.data.type.Rank;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;

/**
 * This interface is used to represent the three classes of vault: Chat, Economy and Permission this
 * includes some methods that those may use.
 */
public interface VaultImplementation {

  /**
   * Registers the implementation to Bukkit service manager
   *
   * @param plugin the plugin that will represent the service in the service manager
   * @param dataModule the data module required in order to make the services work
   * @see #getDataModule()
   */
  static void register(@NonNull Plugin plugin, @NonNull DataModule dataModule) {
    ServicesManager services = Bukkit.getServicesManager();
    StarboxPermission permissions = new StarboxPermission(dataModule);
    services.register(
        Economy.class, new StarboxEconomy(dataModule), plugin, ServicePriority.Normal);
    services.register(Permission.class, permissions, plugin, ServicePriority.Normal);
    services.register(
        Chat.class, new StarboxChat(permissions, dataModule), plugin, ServicePriority.Normal);
  }

  /**
   * Quick way to get a Player data
   *
   * @param player the name of the player to get
   * @return the player data if found in {@link me.googas.commons.modules.data.RanksHandler} null
   *     otherwise
   */
  @Nullable
  default Profile getPlayer(@NonNull String player) {
    return this.getDataModule().getPlayersHandler().getPlayer(player);
  }

  /**
   * Quick way to get a Rank aka group (from vault)
   *
   * @param group the name of the group to get
   * @return the rank if found in {@link me.googas.commons.modules.data.RanksHandler} null otherwise
   */
  @Nullable
  default Rank getGroup(@NonNull String group) {
    return this.getDataModule().getRanksHandler().get(group);
  }

  default Profile getPlayer(@NonNull OfflinePlayer player) {
    return this.getDataModule().getPlayersHandler().getPlayer(player);
  }

  /**
   * The data module must provide all the information required for Economy, Chat and Permission
   *
   * @return the data module
   */
  @NonNull
  DataModule getDataModule();
}
