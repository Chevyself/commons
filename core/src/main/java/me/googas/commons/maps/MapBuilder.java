package me.googas.commons.maps;

import java.util.Map;
import lombok.NonNull;
import lombok.experimental.Delegate;
import me.googas.commons.builder.Builder;
import me.googas.commons.builder.ToStringBuilder;

/** Helps with single line {@link Map} building */
public class MapBuilder<K, V> implements Builder<Map<K, V>> {

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
   * Appends a property to the map
   *
   * @param key the key of hte property
   * @param value the value associated with the key
   * @return this same instance
   */
  @NonNull
  public MapBuilder<K, V> append(@NonNull K key, V value) {
    this.map.put(key, value);
    return this;
  }

  /**
   * Appends another map to the builder
   *
   * @param map mappings to be stored in this map
   * @return this same instance
   */
  @NonNull
  public MapBuilder<K, V> appendAll(@NonNull Map<? extends K, ? extends V> map) {
    this.map.putAll(map);
    return this;
  }

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
