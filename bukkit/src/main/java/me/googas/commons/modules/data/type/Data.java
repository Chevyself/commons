package me.googas.commons.modules.data.type;

import java.util.HashMap;
import java.util.Map;
import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.commons.Validate;

/** Data is an interface which objects can implement when they support having information */
public interface Data {

  /**
   * Get the map of information in a given context
   *
   * @param context the context to get the map of
   * @return the map of the given context
   */
  @NonNull
  default Map<String, Object> getInformation(@Nullable String context) {
    if (context == null) {
      context = "global";
    }
    Map<String, Object> map = this.getInformation().get(context);
    if (map == null) return new HashMap<>();
    return map;
  }

  /**
   * Get an object with the given node aka key in the context
   *
   * @param context the context to get the map
   * @param node the node to get the value
   * @param typeOfT the type of the value to get
   * @param <T> the type of the value
   * @return the value if found and the type can cast the value
   */
  @Nullable
  default <T> T get(@Nullable String context, @NonNull String node, @NonNull Class<T> typeOfT) {
    Map<String, Object> info = this.getInformation(context);
    Object t = info.get(node);
    if (typeOfT.isAssignableFrom(t.getClass())) return typeOfT.cast(t);
    return null;
  }

  /**
   * @see #get(String, String, Class) this provides a check to if the object is null
   * @param context the context to get the map
   * @param node the node the get the value
   * @param def the default value in case the map does not contain it
   * @param typeOfT the type of the value
   * @param <T> the type of the value
   * @return the value if found in the map and if it can be casted else the default value
   */
  @NonNull
  default <T> T getOr(
      @Nullable String context, @NonNull String node, @NonNull T def, @NonNull Class<T> typeOfT) {
    return Validate.notNullOr(this.get(context, node, typeOfT), def);
  }

  /**
   * Set a value in the map of the context
   *
   * @param context the context to get the map
   * @param node the node to set the value
   * @param value the value that will be assigned to the key
   */
  default void set(@Nullable String context, @NonNull String node, @NonNull Object value) {
    if (context == null) {
      context = "global";
    }
    this.getInformation(context).put(node, value);
  }

  @NonNull
  default Number getNumber(@Nullable String context, @NonNull String node, @NonNull Number def) {
    return this.getOr(context, node, def, Number.class);
  }

  default int getInt(@Nullable String context, @NonNull String node, int def) {
    return this.getNumber(context, node, def).intValue();
  }

  default double getDouble(@Nullable String context, @NonNull String node, double def) {
    return this.getNumber(context, node, def).doubleValue();
  }

  default boolean getBoolean(@Nullable String context, @NonNull String node, boolean def) {
    return this.getOr(context, node, def, Boolean.class);
  }

  @NonNull
  default String getString(@Nullable String context, @NonNull String node, @NonNull String def) {
    return this.getOr(context, node, def, String.class);
  }

  default void setInt(@Nullable String context, @NonNull String node, int value) {
    this.set(context, node, value);
  }

  default void setDouble(@Nullable String context, @NonNull String node, double value) {
    this.set(context, node, value);
  }

  default void setBoolean(@Nullable String context, @NonNull String node, boolean value) {
    this.set(context, node, value);
  }

  default void setString(@Nullable String context, @NonNull String node, @NonNull String value) {
    this.set(context, node, value);
  }

  /**
   * Get the information map which has the context and its map
   *
   * @return the information map
   */
  @NonNull
  Map<String, Map<String, Object>> getInformation();
}
