package me.googas.commons.cache;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple implementation for cache
 */
public class MemoryCache implements Cache {

    /**
     * The map required for the cache
     */
    @NotNull
    private final Map<SoftReference<Catchable>, Long> map = new HashMap<>();

    /**
     * This map contains the reference to the cache object and the time
     * in millis for the object to be removed
     *
     * @return the map with the reference and time of the objects
     */
    @Override
    public @NotNull Map<SoftReference<Catchable>, Long> getMap() {
        return this.map;
    }
}
