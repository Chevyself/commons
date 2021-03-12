package me.googas.starbox.modules.data;

import com.starfishst.commands.bukkit.utils.BukkitUtils;
import lombok.Getter;
import lombok.NonNull;
import me.googas.starbox.Starbox;
import me.googas.starbox.events.player.PlayerUpdateDisplayNameEvent;
import me.googas.starbox.modules.Module;
import me.googas.starbox.modules.data.economy.EconomyHandler;
import me.googas.starbox.modules.data.type.Profile;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

/** The data module centralizes permissions and economy */
public class DataModule implements Module {

  /** The map of permissions attachments from players */
  @NonNull @Getter private final Map<UUID, PermissionAttachment> attachments = new HashMap<>();
  /** The map of display names from players */
  @NonNull private final Map<UUID, String> displayNames = new HashMap<>();
  /** Whether to update the display name of the players */
  @Getter private final boolean updateNames = false;
  /** Handler for ranks */
  @NonNull @Getter private RanksHandler ranksHandler = new RanksHandler();
  /** Handler for players */
  @NonNull @Getter private PlayersHandler playersHandler = new PlayersHandler();
  /** Handler for economy */
  @NonNull @Getter private EconomyHandler economyHandler = new EconomyHandler();

  /**
   * Enables a permission for a player
   *
   * @param node the node of the permission
   * @param player the player to enable the permission
   */
  public void enablePermission(@NonNull String node, @NonNull Player player) {
    this.getAttachment(player).setPermission(node, true);
    player.recalculatePermissions();
  }

  /**
   * When the player joins the game give it the permissions
   *
   * @param event the event of a player joining the game
   */
  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerLogin(PlayerLoginEvent event) {
    Player player = event.getPlayer();
    this.updatePermissions(player);
  }

  /**
   * Update the permissions of the player. This should be run after {@link #clear(Player)} has been
   * fired. This will also update the display name of the player
   *
   * @param player the player to update the permissions.
   */
  private void updatePermissions(@NonNull Player player) {
    Profile data = this.playersHandler.getPlayer(player);
    Map<String, Boolean> permissions = data.getPermissions(player.getWorld());
    for (Map.Entry<String, Boolean> entry : permissions.entrySet()) {
      if (entry == null) return;
      String node = entry.getKey();
      Boolean value = entry.getValue();
      if (node == null || value == null) return;
      if (entry.getValue()) {
        this.enablePermission(node, player);
      } else {
        this.disablePermission(node, player, true);
      }
    }
    this.updateDisplayName(player);
  }

  @EventHandler
  public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
    Player player = event.getPlayer();
    this.clear(player);
    this.updatePermissions(player);
  }

  /**
   * Clear the attachment from a player
   *
   * @param player the player to clear the attachment
   */
  private void clear(@NonNull Player player) {
    PermissionAttachment attachment = this.getAttachments().get(player.getUniqueId());
    if (attachment != null) {
      for (Map.Entry<String, Boolean> entry :
          new HashSet<>(attachment.getPermissions().entrySet())) {
        if (entry == null) continue;
        String key = entry.getKey();
        if (key == null) continue;
        attachment.unsetPermission(key);
      }
    }
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    player.removeAttachment(this.getAttachment(player));
    this.attachments.remove(player.getUniqueId());
    this.displayNames.remove(player.getUniqueId());
  }

  /**
   * Disables a permission for a player
   *
   * @param node the node of the permission
   * @param player the player to remove the permission
   * @param force set this to true when a permission is not being removed when it is set in false.
   *     Some permissions cannot be removed, just disabled.
   */
  public void disablePermission(@NonNull String node, @NonNull Player player, boolean force) {
    this.getAttachment(player).unsetPermission(node);
    if (player.hasPermission(node) && force) {
      this.getAttachment(player).setPermission(node, false);
    }
    player.recalculatePermissions();
  }

  /**
   * Get the permission attachment for a player. If it does not have one it will be created
   *
   * @param player the player that needs the attachment
   * @return the permission attachment for the player
   */
  @NonNull
  public PermissionAttachment getAttachment(@NonNull Player player) {
    PermissionAttachment attachment = this.attachments.get(player.getUniqueId());
    if (attachment == null) {
      attachment = player.addAttachment(Starbox.getPlugin());
      this.attachments.put(player.getUniqueId(), attachment);
    }
    return attachment;
  }

  /**
   * Update the display name of the player
   *
   * @param player the player to update the display name to
   */
  public void updateDisplayName(@NonNull Player player) {
    String name =
        this.ranksHandler.getPrefix(player)
            + "&r"
            + player.getName()
            + "&r"
            + this.ranksHandler.getSuffix(player);
    String tabName =
        this.ranksHandler.getPrefix(player)
            + "&r"
            + player.getName()
            + "&r"
            + this.ranksHandler.getSuffix(player);
    if (this.updateNames) {
      PlayerUpdateDisplayNameEvent event =
          new PlayerUpdateDisplayNameEvent(
              player, BukkitUtils.build(name), BukkitUtils.build(tabName));
      player.setDisplayName(event.getName());
      player.setPlayerListName(event.getTabName());
      this.displayNames.put(player.getUniqueId(), event.getName());
    }
  }

  @NonNull
  public String getDisplayName(@NonNull OfflinePlayer player) {
    return this.displayNames.getOrDefault(player.getUniqueId(), player.getName());
  }

  @NonNull
  public DataModule setEconomyHandler(EconomyHandler economyHandler) {
    this.economyHandler = economyHandler;
    return this;
  }

  @NonNull
  public DataModule setRanksHandler(RanksHandler ranksHandler) {
    this.ranksHandler = ranksHandler;
    return this;
  }

  @NonNull
  public DataModule setPlayersHandler(PlayersHandler playersHandler) {
    this.playersHandler = playersHandler;
    return this;
  }

  @Override
  public @NonNull String getName() {
    return "Permissions";
  }
}
