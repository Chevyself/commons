package me.googas.starbox.cache;

import lombok.NonNull;
import lombok.experimental.Delegate;
import me.googas.starbox.builder.ToStringBuilder;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A simple implementation for cache. This must be registered inside a {@link java.util.Timer} or
 * {@link me.googas.starbox.scheduler.Scheduler} in order to work an check if objects can be removed
 * from cache
 */
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
