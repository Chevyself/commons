package me.googas.starbox.compatibilities.vault;

import lombok.Getter;
import lombok.NonNull;
import me.googas.starbox.Starbox;
import me.googas.starbox.modules.data.DataModule;
import me.googas.starbox.modules.data.type.Profile;
import me.googas.starbox.modules.data.type.Rank;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class StarboxChat extends Chat implements VaultImplementation {

  @Getter @NonNull private DataModule dataModule;

  public StarboxChat(@NonNull Permission perms, @NonNull DataModule dataModule) {
    super(perms);
    this.dataModule = dataModule;
  }

  @NonNull
  public StarboxChat setDataModule(@NonNull DataModule dataModule) {
    this.dataModule = dataModule;
    return this;
  }

  @Override
  public String getName() {
    return "Starbox";
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Deprecated
  @Override
  public String getPlayerPrefix(String world, String player) {
    Profile profile = this.getPlayer(player);
    if (profile != null) return profile.getPrefix(Starbox.buildContext(world));
    return null;
  }

  @Deprecated
  @Override
  public void setPlayerPrefix(String world, String player, String prefix) {
    Profile profile = this.getPlayer(player);
    if (profile != null) profile.setPrefix(Starbox.buildContext(world), prefix);
  }

  @Deprecated
  @Override
  public String getPlayerSuffix(String world, String player) {
    Profile profile = this.getPlayer(player);
    if (profile != null) return profile.getSuffix(Starbox.buildContext(world));
    return null;
  }

  @Deprecated
  @Override
  public void setPlayerSuffix(String world, String player, String suffix) {
    Profile profile = this.getPlayer(player);
    if (profile != null) profile.setSuffix(Starbox.buildContext(world), suffix);
  }

  @Override
  public String getGroupPrefix(String world, String group) {
    Rank rank = this.getGroup(group);
    if (rank != null) return rank.getPrefix(Starbox.buildContext(world));
    return null;
  }

  @Override
  public void setGroupPrefix(String world, String group, String prefix) {
    Rank rank = this.getGroup(group);
    if (rank != null) rank.setPrefix(Starbox.buildContext(world), prefix);
  }

  @Override
  public String getGroupSuffix(String world, String group) {
    Rank rank = this.getGroup(group);
    if (rank != null) return rank.getSuffix(Starbox.buildContext(world));
    return null;
  }

  @Override
  public void setGroupSuffix(String world, String group, String prefix) {
    Rank rank = this.getGroup(group);
    if (rank != null) rank.setSuffix(Starbox.buildContext(world), prefix);
  }

  @Deprecated
  @Override
  public int getPlayerInfoInteger(String world, String player, String node, int def) {
    Profile profile = this.getPlayer(player);
    if (profile != null) return profile.getInt(Starbox.buildContext(world), node, def);
    return def;
  }

  @Deprecated
  @Override
  public void setPlayerInfoInteger(String world, String player, String node, int value) {
    Profile profile = this.getPlayer(player);
    if (profile != null) profile.setInt(Starbox.buildContext(world), node, value);
  }

  @Override
  public int getGroupInfoInteger(String world, String group, String node, int def) {
    Rank rank = this.getGroup(group);
    if (rank != null) rank.getInt(Starbox.buildContext(world), node, def);
    return def;
  }

  @Override
  public void setGroupInfoInteger(String world, String group, String node, int value) {
    Rank rank = this.getGroup(group);
    if (rank != null) rank.setInt(Starbox.buildContext(world), node, value);
  }

  @Deprecated
  @Override
  public double getPlayerInfoDouble(String world, String player, String node, double def) {
    Profile profile = this.dataModule.getPlayersHandler().getPlayer(player);
    if (profile != null) return profile.getDouble(Starbox.buildContext(world), node, def);
    return def;
  }

  @Deprecated
  @Override
  public void setPlayerInfoDouble(String world, String player, String node, double value) {
    Profile profile = this.getPlayer(player);
    if (profile != null) profile.setDouble(Starbox.buildContext(world), node, value);
  }

  @Override
  public double getGroupInfoDouble(String world, String group, String node, double def) {
    Rank rank = this.getGroup(group);
    if (rank != null) rank.setDouble(Starbox.buildContext(world), node, def);
    return def;
  }

  @Override
  public void setGroupInfoDouble(String world, String group, String node, double value) {
    Rank rank = this.getGroup(group);
    if (rank != null) rank.setDouble(Starbox.buildContext(world), node, value);
  }

  @Deprecated
  @Override
  public boolean getPlayerInfoBoolean(String world, String player, String node, boolean def) {
    Profile profile = this.getPlayer(player);
    if (profile != null) return profile.getBoolean(Starbox.buildContext(world), node, def);
    return def;
  }

  @Deprecated
  @Override
  public void setPlayerInfoBoolean(String world, String player, String node, boolean value) {
    Profile profile = this.getPlayer(player);
    if (profile != null) profile.setBoolean(Starbox.buildContext(world), node, value);
  }

  @Override
  public boolean getGroupInfoBoolean(String world, String group, String node, boolean def) {
    Rank rank = this.getGroup(group);
    if (rank != null) rank.getBoolean(Starbox.buildContext(world), node, def);
    return def;
  }

  @Override
  public void setGroupInfoBoolean(String world, String group, String node, boolean value) {
    Rank rank = this.getGroup(group);
    if (rank != null) rank.setBoolean(Starbox.buildContext(world), node, value);
  }

  @Deprecated
  @Override
  public String getPlayerInfoString(String world, String player, String node, String def) {
    Profile profile = this.getPlayer(player);
    if (profile != null) return profile.getString(Starbox.buildContext(world), node, def);
    return def;
  }

  @Deprecated
  @Override
  public void setPlayerInfoString(String world, String player, String node, String value) {
    Profile profile = this.getPlayer(player);
    if (profile != null) profile.setString(Starbox.buildContext(world), node, value);
  }

  @Override
  public String getGroupInfoString(String world, String group, String node, String def) {
    Rank rank = this.getGroup(group);
    if (rank != null) rank.getString(Starbox.buildContext(world), node, def);
    return def;
  }

  @Override
  public void setGroupInfoString(String world, String group, String node, String value) {
    Rank rank = this.getGroup(group);
    if (rank != null) rank.setString(Starbox.buildContext(world), node, value);
  }

  @Override
  public String getPlayerPrefix(String world, OfflinePlayer player) {
    return this.getPlayer(player).getPrefix(Starbox.buildContext(world));
  }

  @Override
  public String getPlayerPrefix(Player player) {
    return this.getPlayer(player).getPrefix(Starbox.buildContext(player));
  }

  @Override
  public void setPlayerPrefix(String world, OfflinePlayer player, String prefix) {
    this.getPlayer(player).setPrefix(Starbox.buildContext(world), prefix);
  }

  @Override
  public void setPlayerPrefix(Player player, String prefix) {
    this.getPlayer(player).setPrefix(Starbox.buildContext(player), prefix);
  }

  @Override
  public String getPlayerSuffix(String world, OfflinePlayer player) {
    return this.getPlayer(player).getSuffix(Starbox.buildContext(world));
  }

  @Override
  public String getPlayerSuffix(Player player) {
    return this.getPlayer(player).getSuffix(Starbox.buildContext(player));
  }

  @Override
  public void setPlayerSuffix(String world, OfflinePlayer player, String suffix) {
    this.getPlayer(player).setSuffix(Starbox.buildContext(world), suffix);
  }

  @Override
  public void setPlayerSuffix(Player player, String suffix) {
    this.getPlayer(player).setSuffix(Starbox.buildContext(player), suffix);
  }

  @Override
  public int getPlayerInfoInteger(
      String world, OfflinePlayer player, String node, int defaultValue) {
    return this.getPlayer(player).getInt(Starbox.buildContext(world), node, defaultValue);
  }

  @Override
  public int getPlayerInfoInteger(Player player, String node, int defaultValue) {
    return this.getPlayer(player).getInt(Starbox.buildContext(player), node, defaultValue);
  }

  @Override
  public void setPlayerInfoInteger(String world, OfflinePlayer player, String node, int value) {
    this.getPlayer(player).setInt(Starbox.buildContext(world), node, value);
  }

  @Override
  public void setPlayerInfoInteger(Player player, String node, int value) {
    this.getPlayer(player).setInt(Starbox.buildContext(player), node, value);
  }

  @Override
  public double getPlayerInfoDouble(
      String world, OfflinePlayer player, String node, double defaultValue) {
    return this.getPlayer(player).getDouble(Starbox.buildContext(world), node, defaultValue);
  }

  @Override
  public double getPlayerInfoDouble(Player player, String node, double defaultValue) {
    return this.getPlayer(player).getDouble(Starbox.buildContext(player), node, defaultValue);
  }

  @Override
  public void setPlayerInfoDouble(String world, OfflinePlayer player, String node, double value) {
    this.getPlayer(player).setDouble(Starbox.buildContext(world), node, value);
  }

  @Override
  public void setPlayerInfoDouble(Player player, String node, double value) {
    this.getPlayer(player).setDouble(Starbox.buildContext(player), node, value);
  }

  @Override
  public boolean getPlayerInfoBoolean(
      String world, OfflinePlayer player, String node, boolean defaultValue) {
    return this.getPlayer(player).getBoolean(Starbox.buildContext(world), node, defaultValue);
  }

  @Override
  public boolean getPlayerInfoBoolean(Player player, String node, boolean defaultValue) {
    return this.getPlayer(player).getBoolean(Starbox.buildContext(player), node, defaultValue);
  }

  @Override
  public void setPlayerInfoBoolean(String world, OfflinePlayer player, String node, boolean value) {
    this.getPlayer(player).setBoolean(Starbox.buildContext(world), node, value);
  }

  @Override
  public void setPlayerInfoBoolean(Player player, String node, boolean value) {
    this.getPlayer(player).setBoolean(Starbox.buildContext(player), node, value);
  }

  @Override
  public String getPlayerInfoString(
      String world, OfflinePlayer player, String node, String defaultValue) {
    return this.getPlayer(player).getString(Starbox.buildContext(world), node, defaultValue);
  }

  @Override
  public String getPlayerInfoString(Player player, String node, String defaultValue) {
    return this.getPlayer(player).getString(Starbox.buildContext(player), node, defaultValue);
  }

  @Override
  public void setPlayerInfoString(String world, OfflinePlayer player, String node, String value) {
    this.getPlayer(player).setString(Starbox.buildContext(world), node, value);
  }

  @Override
  public void setPlayerInfoString(Player player, String node, String value) {
    this.getPlayer(player).setString(Starbox.buildContext(player), node, value);
  }
}
