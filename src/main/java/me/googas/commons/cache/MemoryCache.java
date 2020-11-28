package me.googas.commons.cache;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.NonNull;
import lombok.experimental.Delegate;
import me.googas.commons.builder.ToStringBuilder;

/** A simple implementation for cache */
public class MemoryCache implements Cache {

  /** The map required for the cache */
  @NonNull @Delegate
  private final Map<SoftReference<Catchable>, Long> map = new ConcurrentHashMap<>();

  @Override
  public @NonNull Map<SoftReference<Catchable>, Long> getMap() {
    return this.map;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("map", this.map).build();
  }
}
