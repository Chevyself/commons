package me.googas.starbox.modules.data.type;

import lombok.NonNull;
import me.googas.annotations.Nullable;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;

/** A permissible is an interface that object that may have permissions should implement */
public interface Permissible {

  /**
   * Check whether this object contains the given permission at the context . If the context is null
   * it will be check using {@link #getGlobalPermissions()}
   *
   * @param context the context to check if the permissible has the permission on
   * @param node the node of the permission to check
   * @return true if the set {@link #getPermissions(String)} contains the node and the value of the
   *     node is true
   */
  default boolean hasPermission(@Nullable String context, @NonNull String node) {
    if (context == null) {
      Boolean value = this.getGlobalPermissions().get(node);
      return value != null && value;
    }
    Map<String, Boolean> map = this.getPermissions().get(context);
    if (map == null) return false;
    Boolean value = map.get(node);
    return value != null && value;
  }

  /**
   * Add a permission to this object in the given context
   *
   * @param context the context to add the permission
   * @param permission the permission to add
   * @return whether the permission was added
   */
  default boolean addPermission(@Nullable String context, @NonNull String permission) {
    if (context == null) {
      this.getGlobalPermissions().put(permission, true);
      return true;
    }
    Map<String, Boolean> map =
        this.getPermissions().computeIfAbsent(context, key -> new HashMap<>());
    map.put(permission, true);
    return true;
  }

  /**
   * Get the permissions in the given world. The name of the world will be used to get it as in
   *
   * @see #getPermissions(String)
   * @param world the world to get the permissions of the data
   * @return the map of permissions
   */
  @NonNull
  default Map<String, Boolean> getPermissions(@NonNull World world) {
    return this.getPermissions(world.getName());
  }

  default boolean hasRank(@Nullable String context, @NonNull Rank rank) {
    return this.hasPermission(context, rank.getNode());
  }

  default boolean addRank(@Nullable String context, @NonNull Rank rank) {
    return this.addPermission(context, rank.getNode());
  }

  default boolean removePermission(@Nullable String context, @NonNull String permission) {
    if (context == null) {
      this.getGlobalPermissions().remove(permission);
      return true;
    }
    Map<String, Boolean> map = this.getPermissions().get(context);
    if (map != null) {
      map.remove(permission);
    }
    return true;
  }

  default boolean removeRank(@Nullable String context, @NonNull Rank rank) {
    return this.removePermission(context, rank.getNode());
  }

  @NonNull
  default Map<String, Boolean> getPermissions(@Nullable String context) {
    if (context != null) this.getPermissions().get(context);
    return new HashMap<>();
  }

  @NonNull
  Map<String, Map<String, Boolean>> getPermissions();

  @NonNull
  default Map<String, Boolean> getGlobalPermissions() {
    Map<String, Boolean> global = this.getPermissions().get("global");
    if (global != null) return global;
    return new HashMap<>();
  }
}
