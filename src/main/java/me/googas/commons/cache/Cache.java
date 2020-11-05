package me.googas.commons.cache;

import me.googas.commons.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * An object which represents a way to manage cache. This stores the catchables
 * objects and the time for their removal
 */
public interface Cache extends Runnable {

    /**
     * Creates a copy of the current cache
     *
     * @return the copy of the current cache
     */
    @NotNull
    default Collection<SoftReference<Catchable>> copy() {
        return new HashSet<>(this.getMap().keySet());
    }

    /**
     * Get an object from cache
     *
     * @param clazz the clazz of the catchable for casting
     * @param predicate the boolean to match
     * @param <T> the type of the catchable
     * @return the catchable if found else null
     */
    @Nullable
    default <T extends Catchable> T getCatchable(@NotNull Class<T> clazz, @NotNull Predicate<T> predicate) {
        for (SoftReference<Catchable> reference : this.copy()) {
            Catchable catchable = reference.get();
            if (catchable != null && clazz.isAssignableFrom(catchable.getClass())) {
                T cast = clazz.cast(catchable);
                if (predicate.test(cast)) {
                    this.getMap().put(reference, this.getTimeToRemove(catchable));
                    return cast;
                }
            }
        }
        return null;
    }

    /**
     * Get an object from cache or return another value
     *
     * @param clazz the clazz of the catchable for casting
     * @param predicate the boolean to match
     * @param def the default value to provide if not found in cache
     * @param <T> the type of the catchable
     * @return the catchable if found else the default value
     */
    @NotNull
    default <T extends Catchable> T getCatchableOr(@NotNull Class<T> clazz, @NotNull T def, @NotNull Predicate<T> predicate) {
        return Validate.notNullOr(this.getCatchable(clazz, predicate), def);
    }

    /**
     * Get an object from cache or return another value
     *
     * @param clazz the clazz of the catchable for casting
     * @param predicate the boolean to match
     * @param supplier the supplier to get the default value to provide if not found in cache
     * @param <T> the type of the catchable
     * @return the catchable if found else the default value
     */
    @Nullable
    default <T extends Catchable> T getCatchableOrGet(@NotNull Class<T> clazz, @NotNull Predicate<T> predicate, @NotNull Supplier<T> supplier) {
        return Validate.notNullOrGet(this.getCatchable(clazz, predicate), supplier);
    }

    /**
     * Checks whether an object is inside the cache
     *
     * @param catchable the object to check if it is inside the cache
     * @return true if the object is inside the cache
     */
    default boolean contains(@NotNull Catchable catchable) {
        for (SoftReference<Catchable> reference : this.copy()) {
            if (catchable.equals(reference.get())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds an object to the cache
     *
     * @param catchable the object to be added
     */
    default void add(@NotNull Catchable catchable) {
        if (this.contains(catchable)) {
            throw new IllegalStateException("There's already an instance of " + catchable + " inside of the cache");
        }
        this.getMap().put(new SoftReference<>(catchable), this.getTimeToRemove(catchable));
    }

    /**
     * Removes an object from cache
     *
     * @param catchable the object to be removed
     * @return whether the object was removed from cache
     */
    default boolean remove(@NotNull Catchable catchable) {
        if (this.contains(catchable)) {
            return this.getMap().keySet().removeIf(reference -> catchable.equals(reference.get()));
        } else {
            return false;
        }
    }

    /**
     * Refreshes a catchable object
     *
     * @param catchable the object to be cache
     */
    default void refresh(@NotNull Catchable catchable) {
        for (SoftReference<Catchable> reference : this.getMap().keySet()) {
            if (catchable.equals(reference.get())) {
                this.getMap().put(reference, this.getTimeToRemove(catchable));
            }
        }
    }

    /**
     * Get the time in which an object must be removed
     *
     * @param catchable the object to get the removal time
     * @return the removal time of the object
     */
    default long getTimeToRemove(@NotNull Catchable catchable) {
        return System.currentTimeMillis() + catchable.getToRemove().millis();
    }

    /**
     * This map contains the reference to the cache object and the time
     * in millis for the object to be removed
     *
     * @return the map with the reference and time of the objects
     */
    @NotNull
    Map<SoftReference<Catchable>, Long> getMap();

    @Override
    default void run() {
        Map<SoftReference<Catchable>, Long> copy = new HashMap<>(this.getMap());
        copy.forEach((reference, time) -> {
            Catchable catchable = reference.get();
            if (catchable != null) {
                if (System.currentTimeMillis() >= time) {
                    try {
                        catchable.onRemove();
                        reference.clear();
                    } catch (Throwable e) {
                        Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                    }
                }
            }
        });
        this.getMap().keySet().removeIf(reference -> reference.get() == null);
    }
}
