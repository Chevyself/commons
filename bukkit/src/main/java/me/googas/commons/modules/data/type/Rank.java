package me.googas.commons.modules.data.type;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.commons.modules.ui.Displayable;
import org.bukkit.permissions.Permission;

/** A rank is an object which players may posses: it gives the player permissions */
public interface Rank extends Displayable, Data, Permissible, Decorative {

  /**
   * This function will sort the given list of ranks from the rank of lowest weight to the highest
   *
   * @param toSort the list to sort
   * @return the same instance of the list sorted
   */
  @NonNull
  static List<Rank> sort(@NonNull List<Rank> toSort) {
    toSort.sort(Comparator.comparingInt(Rank::getWeight));
    return toSort;
  }

  /**
   * Get the min weight from a list of ranks
   *
   * @param ranks the list of ranks to get the min weight from it
   * @return the min weight
   */
  static int getMinWeight(@NonNull List<? extends Rank> ranks) {
    if (ranks.isEmpty()) return 0;
    int min = ranks.get(0).getWeight();
    for (Rank rank : ranks) {
      if (rank.getWeight() < min) min = rank.getWeight();
    }
    return min;
  }

  /**
   * Get the children permissions in the given context. This permissions are the one appended to the
   * bukkit permissions
   *
   * @param context the context to get the permissions of this rank
   * @return the children permissions
   */
  @NonNull
  default Map<String, Boolean> getChildren(@Nullable String context) {
    Map<String, Boolean> children = new HashMap<>(this.getPermissions(context));
    children.putAll(this.getPermissions((String) null));
    for (Rank parent : Rank.sort(this.getParents())) {
      children.putAll(parent.getChildren(context));
    }
    return children;
  }

  /**
   * Get the list of bukkit permission to register it
   *
   * @return the list of bukkit permissions
   */
  @NonNull
  List<Permission> toBukkit();

  /**
   * Get the node of this rank in the given context
   *
   * @param context the context to get the node of the rank on
   * @return the node of the rank
   */
  @NonNull
  String getNode(@Nullable String context);

  /**
   * Get the node of the rank
   *
   * @return the node of the rank
   */
  @NonNull
  String getNode();

  /**
   * Get the weight of this rank by such it means the priority of it this is to decide which
   * prefixes should the player have
   *
   * @return the weight
   */
  int getWeight();

  /**
   * This is another way to identify the rank but in a friendlier way
   *
   * @return the name of the rank
   */
  @NonNull
  String getName();

  /**
   * A description of the rank
   *
   * @return the description
   */
  @Nullable
  String getDescription();

  /**
   * Get how the name should be displayed inside the game, if null, {@link #getName()}
   *
   * @return the display name
   */
  @Nullable
  String getDisplayName();

  /**
   * Get the parents of this rank
   *
   * @return the parents
   */
  @NonNull
  List<Rank> getParents();
}
