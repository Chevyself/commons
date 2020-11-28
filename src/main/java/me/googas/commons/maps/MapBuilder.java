package me.googas.commons.maps;

import java.util.Map;
import lombok.NonNull;
import lombok.experimental.Delegate;
import me.googas.commons.builder.Builder;
import me.googas.commons.builder.ToStringBuilder;

/** Helps with single line hash map building */
public class MapBuilder<K, V> implements Builder<Map<K, V>> {

  /** The map that is being built */
  @NonNull private final Map<K, V> map;

  /**
   * Create a map builder
   *
   * @param map the map that is being built
   */
  @NonNull
  public MapBuilder(@NonNull Map<K, V> map) {
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
  @NonNull
  public <T extends K, O extends V> MapBuilder<K, V> append(@NonNull T key, O value) {
    this.map.put(key, value);
    return this;
  }

  /**
   * Appends another map to the builder
   *
   * @param map mappings to be stored in this map
   * @return the map builder
   */
  @NonNull
  public MapBuilder<K, V> appendAll(@NonNull Map<? extends K, ? extends V> map) {
    this.map.putAll(map);
    return this;
  }

  /**
   * Get the built map
   *
   * @return the built map
   */
  @NonNull
  @Override
  public Map<K, V> build() {
    return this.map;
  }

  @Override
  @Delegate
  public String toString() {
    return new ToStringBuilder(this).append("map", this.map).build();
  }
}
