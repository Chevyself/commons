package me.googas.commons.cache;

import me.googas.commons.time.Time;
import org.jetbrains.annotations.NotNull;

/**
 * An object which can be stored inside cache
 */
public interface Catchable {

    /**
     * Called when the cache is ready to remove this object
     *
     * @throws Throwable in case something goes wrong while unloading
     * this object
     */
    void onRemove() throws Throwable;

    /**
     * Get the time for the object to be removed from cache
     *
     * @return the time to be removed
     */
    @NotNull
    Time getToRemove();
}
