package me.googas.commons.maps;

import java.util.Map;
import org.jetbrains.annotations.NotNull;

/** Helps with single line hash map building */
public class MapBuilder<K, V> {

  /** The map that is being built */
  @NotNull private final Map<K, V> map;

  /**
   * Create a map builder
   *
   * @param map the map that is being built
   */
  @NotNull
  public MapBuilder(@NotNull Map<K, V> map) {
    this.map = map;
  }

  /**
   * Appends values to the map
   *
   * @param key with which the value is associated
   * @param value the value associated with the key
   * @return the map builder
   * @param <O> the type that extends key
   * @param <T> the type that extends value
   */
  public <T extends K, O extends V> MapBuilder<K, V> append(@NotNull T key, @NotNull O value) {
    this.map.put(key, value);
    return this;
  }

  /**
   * Appends another map to the builder
   *
   * @param map mappings to be stored in this map
   * @return the map builder
   */
  public MapBuilder<K, V> appendAll(@NotNull Map<? extends K, ? extends V> map) {
    this.map.putAll(map);
    return this;
  }

  /**
   * Get the built map
   *
   * @return the built map
   */
  @NotNull
  public Map<K, V> build() {
    return this.map;
  }
}
