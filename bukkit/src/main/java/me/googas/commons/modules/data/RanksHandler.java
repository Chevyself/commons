package me.googas.commons.modules.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Delegate;
import me.googas.annotations.Nullable;
import me.googas.commands.bukkit.utils.BukkitUtils;
import me.googas.commons.modules.data.type.Profile;
import me.googas.commons.modules.data.type.Rank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

/** Handles ranks for the data module */
public class RanksHandler {

  /** The list of ranks */
  @NonNull @Getter @Delegate private final List<Rank> ranks = new ArrayList<>();

  /**
   * Add a rank
   *
   * @param rank the rank to add
   * @param register whether to register the rank to bukkit
   * @return true if the rank was added
   */
  public boolean add(Rank rank, boolean register) {
    if (!register) return this.add(rank);
    if (this.add(rank)) {
      for (Permission permission : rank.toBukkit()) {
        Bukkit.getServer().getPluginManager().addPermission(permission);
      }
      return true;
    }
    return false;
  }

  /**
   * Add a collection of ranks
   *
   * @param ranks the ranks to add
   * @param register whether to register each rank to bukkit
   * @return true if at least one rank was added
   */
  public boolean addAll(Collection<? extends Rank> ranks, boolean register) {
    if (!register) return this.addAll(ranks);
    boolean changed = false;
    for (Rank rank : ranks) {
      if (this.add(rank, true)) changed = true;
    }
    return changed;
  }

  /**
   * Get the ranks of a player
   *
   * @param player the player to get the ranks to
   * @return the list of ranks sorted by weight
   */
  @NonNull
  public List<Rank> getRanks(@NonNull Player player) {
    List<Rank> playerRanks = new ArrayList<>();
    for (Rank rank : this.ranks) {
      if (player.hasPermission(rank.getNode())) playerRanks.add(rank);
    }
    return playerRanks;
  }

  /**
   * Get all the ranks which may be displayed as prefix and suffix for a player
   *
   * @param player the player to get the display ranks
   * @return the display ranks
   */
  @NonNull
  public List<Rank> getDisplayableRank(@NonNull Player player) {
    List<Rank> ranks = this.getRanks();
    int weight = Rank.getMinWeight(ranks);
    ranks.removeIf(rank -> rank.getWeight() > weight);
    return ranks;
  }

  /**
   * Build the prefix for a player
   *
   * @param player the player to build the prefix
   * @return the built prefix for the player
   */
  @NonNull
  public String getPrefix(@NonNull Player player) {
    StringBuilder builder = new StringBuilder();
    List<Rank> ranks = this.getDisplayableRank(player);
    for (Rank rank : ranks) {
      builder.append(rank.getPrefix(player.getWorld()));
    }
    return BukkitUtils.format(builder.toString());
  }

  /**
   * Get the suffix for a player
   *
   * @param player the player to get the suffix to
   * @return the suffix
   */
  @NonNull
  public String getSuffix(@NonNull Player player) {
    StringBuilder builder = new StringBuilder();
    List<Rank> ranks = this.getDisplayableRank(player);
    for (Rank rank : ranks) {
      builder.append(rank.getSuffix(player.getWorld()));
    }
    return BukkitUtils.format(builder.toString());
  }

  /**
   * Get a rank by its name
   *
   * @param name the name to match
   * @return the rank if the name matches
   */
  @Nullable
  public Rank get(@NonNull String name) {
    for (Rank rank : this.ranks) {
      if (rank.getName().equalsIgnoreCase(name)) return rank;
    }
    return null;
  }

  @NonNull
  public String[] getRanksArray(@Nullable String world, @NonNull Profile profile) {
    List<Rank> ranks = this.getRanks(world, profile);
    String[] names = new String[ranks.size()];
    for (int i = 0; i < ranks.size(); i++) {
      names[i] = ranks.get(i).getName();
    }
    return names;
  }

  @NonNull
  private List<Rank> getRanks(@Nullable String world, @NonNull Profile profile) {
    List<Rank> ranks = new ArrayList<>();
    for (Rank rank : this.ranks) {
      if (profile.hasPermission(world, rank.getNode())) ranks.add(rank);
    }
    return ranks;
  }

  @Nullable
  public Rank getPrimaryRank(@NonNull String context, @NonNull Profile profile) {
    List<Rank> ranks = this.getRanks(context, profile);
    if (ranks.isEmpty()) return null;
    return Rank.sort(ranks).get(0);
  }

  public String[] getRanksArray() {
    String[] names = new String[this.ranks.size()];
    for (int i = 0; i < this.ranks.size(); i++) {
      names[i] = this.ranks.get(i).getName();
    }
    return names;
  }
}
