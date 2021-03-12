package me.googas.starbox.compatibilities.vault;

import lombok.Getter;
import lombok.NonNull;
import me.googas.starbox.Starbox;
import me.googas.starbox.modules.data.DataModule;
import me.googas.starbox.modules.data.type.Profile;
import me.googas.starbox.modules.data.type.Rank;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class StarboxPermission extends Permission implements VaultImplementation {

  @Getter @NonNull private DataModule dataModule;

  public StarboxPermission(@NonNull DataModule dataModule) {
    this.dataModule = dataModule;
  }

  @NonNull
  public StarboxPermission setDataModule(DataModule dataModule) {
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

  @Override
  public boolean hasSuperPermsCompat() {
    return false;
  }

  @Deprecated
  @Override
  public boolean playerHas(String world, String player, String node) {
    Profile profile = this.getPlayer(player);
    if (profile != null) return profile.hasPermission(Starbox.buildContext(world), node);
    return false;
  }

  @Deprecated
  @Override
  public boolean playerAdd(String world, String player, String permission) {
    Profile profile = this.getPlayer(player);
    if (profile != null) return profile.addPermission(Starbox.buildContext(world), permission);
    return false;
  }

  @Deprecated
  @Override
  public boolean playerRemove(String world, String player, String permission) {
    Profile profile = this.getPlayer(player);
    if (profile != null) return profile.removePermission(Starbox.buildContext(world), permission);
    return false;
  }

  @Override
  public boolean groupHas(String world, String group, String permission) {
    Rank rank = this.getGroup(group);
    if (rank != null) return rank.hasPermission(Starbox.buildContext(world), permission);
    return false;
  }

  @Override
  public boolean groupAdd(String world, String group, String permission) {
    Rank rank = this.getGroup(group);
    if (rank != null) return rank.addPermission(Starbox.buildContext(world), permission);
    return false;
  }

  @Override
  public boolean groupRemove(String world, String group, String permission) {
    Rank rank = this.getGroup(group);
    if (rank != null) return rank.removePermission(Starbox.buildContext(world), permission);
    return false;
  }

  @Deprecated
  @Override
  public boolean playerInGroup(String world, String player, String group) {
    Profile data = this.getPlayer(player);
    Rank rank = this.getGroup(group);
    if (data != null && rank != null) return data.hasRank(Starbox.buildContext(world), rank);
    return false;
  }

  @Deprecated
  @Override
  public boolean playerAddGroup(String world, String player, String group) {
    Profile data = this.getPlayer(player);
    Rank rank = this.getGroup(group);
    if (data != null && rank != null) return data.addRank(Starbox.buildContext(world), rank);
    return false;
  }

  @Deprecated
  @Override
  public boolean playerRemoveGroup(String world, String player, String group) {
    Profile data = this.getPlayer(player);
    Rank rank = this.getGroup(group);
    if (data != null && rank != null) return data.removeRank(Starbox.buildContext(world), rank);
    return false;
  }

  @Deprecated
  @Override
  public String[] getPlayerGroups(String world, String player) {
    Profile profile = this.getPlayer(player);
    if (profile != null) {
      return this.dataModule.getRanksHandler().getRanksArray(Starbox.buildContext(world), profile);
    }
    return null;
  }

  @Deprecated
  @Override
  public String getPrimaryGroup(String world, String player) {
    Profile profile = this.getPlayer(player);
    if (profile == null) return null;
    Rank rank = this.dataModule.getRanksHandler().getPrimaryRank(world, profile);
    return rank == null ? null : rank.getName();
  }

  @Override
  public String[] getGroups() {
    return this.dataModule.getRanksHandler().getRanksArray();
  }

  @Override
  public boolean hasGroupSupport() {
    return true;
  }

  @Override
  public boolean has(Player player, String permission) {
    return this.getPlayer(player).hasPermission(Starbox.getContext(), permission);
  }

  @Override
  public boolean playerHas(String world, OfflinePlayer player, String permission) {
    return this.getPlayer(player).hasPermission(Starbox.buildContext(world), permission);
  }

  @Override
  public boolean playerHas(Player player, String permission) {
    return this.getPlayer(player).hasPermission(Starbox.buildContext(player), permission);
  }

  @Override
  public boolean playerAdd(String world, OfflinePlayer player, String permission) {
    return this.getPlayer(player).addPermission(Starbox.buildContext(world), permission);
  }

  @Override
  public boolean playerAdd(Player player, String permission) {
    return this.getPlayer(player).addPermission(Starbox.buildContext(player), permission);
  }

  @Override
  public boolean playerRemove(String world, OfflinePlayer player, String permission) {
    return this.getPlayer(player).removePermission(Starbox.buildContext(world), permission);
  }

  @Override
  public boolean playerRemove(Player player, String permission) {
    return this.getPlayer(player).removePermission(Starbox.buildContext(player), permission);
  }

  @Override
  public boolean playerInGroup(String world, OfflinePlayer player, String group) {
    Rank rank = this.getGroup(group);
    if (rank == null) return false;
    return this.getPlayer(player).hasRank(Starbox.buildContext(world), rank);
  }

  @Override
  public boolean playerInGroup(Player player, String group) {
    Rank rank = this.getGroup(group);
    if (rank == null) return false;
    return this.getPlayer(player).hasRank(Starbox.buildContext(player), rank);
  }

  @Override
  public boolean playerAddGroup(String world, OfflinePlayer player, String group) {
    Rank rank = this.getGroup(group);
    if (rank == null) return false;
    return this.getPlayer(player).addRank(Starbox.buildContext(world), rank);
  }

  @Override
  public boolean playerAddGroup(Player player, String group) {
    Rank rank = this.getGroup(group);
    if (rank == null) return false;
    return this.getPlayer(player).addRank(Starbox.buildContext(player), rank);
  }

  @Override
  public boolean playerRemoveGroup(String world, OfflinePlayer player, String group) {
    Rank rank = this.getGroup(group);
    if (rank == null) return false;
    return this.getPlayer(player).removeRank(Starbox.buildContext(world), rank);
  }

  @Override
  public boolean playerRemoveGroup(Player player, String group) {
    Rank rank = this.getGroup(group);
    if (rank == null) return false;
    return this.getPlayer(player).removeRank(Starbox.buildContext(player), rank);
  }

  @Override
  public String[] getPlayerGroups(String world, OfflinePlayer player) {
    return this.dataModule
        .getRanksHandler()
        .getRanksArray(Starbox.buildContext(world), this.getPlayer(player));
  }

  @Override
  public String[] getPlayerGroups(Player player) {
    return this.dataModule
        .getRanksHandler()
        .getRanksArray(Starbox.buildContext(player), this.getPlayer(player));
  }

  @Override
  public String getPrimaryGroup(String world, OfflinePlayer player) {
    Rank rank =
        this.dataModule
            .getRanksHandler()
            .getPrimaryRank(Starbox.buildContext(world), this.getPlayer(player));
    return rank == null ? null : rank.getName();
  }

  @Override
  public String getPrimaryGroup(Player player) {
    Rank rank =
        this.dataModule
            .getRanksHandler()
            .getPrimaryRank(Starbox.buildContext(player), this.getPlayer(player));
    return rank == null ? null : rank.getName();
  }
}
