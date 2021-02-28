package me.googas.commons.cache;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;
import lombok.NonNull;
import me.googas.commons.Validate;
import me.googas.commons.time.Time;
import me.googas.commons.time.Unit;

/**
 * An object which represents a way to manage cache. This stores the catchables objects and the time
 * for their removal
 */
public interface Cache extends Runnable {

  /**
   * Creates a copy of the current cache
   *
   * @return the copy of the current cache
   */
  @NonNull
  default Collection<SoftReference<Catchable>> copy() {
    return new HashSet<>(this.getMap().keySet());
  }

  /**
   * Get an object from cache. This will refresh the object inside of cache use {@link #get(Class,
   * Predicate, boolean)} to not refresh it
   *
   * @param clazz the clazz of the catchable for casting
   * @param predicate the predicate to match the catchable
   * @param <T> the type of the catchable
   * @return the catchable if found else null
   */
  default <T extends Catchable> T get(@NonNull Class<T> clazz, @NonNull Predicate<T> predicate) {
    return this.get(clazz, predicate, true);
  }

  /**
   * Get an object from cache and select whether to refresh it
   *
   * @param clazz the clazz of the catchable for casting
   * @param predicate the predicate to match the catchable
   * @param refresh whether to refresh the object. By refreshing means that the time of the object inside the cache will be
   *                extended to its initial value
   * @param <T> the type of the catchable
   * @return the catchable if found else null
   */
  default <T extends Catchable> T get(
      @NonNull Class<T> clazz, @NonNull Predicate<T> predicate, boolean refresh) {
    for (SoftReference<Catchable> reference : this.copy()) {
      Catchable catchable = reference.get();
      if (catchable != null && clazz.isAssignableFrom(catchable.getClass())) {
        T cast = clazz.cast(catchable);
        if (predicate.test(cast)) {
          if (refresh) this.refresh(catchable);
          return cast;
        }
      }
    }
    return null;
  }

  /**
   * Get an object from cache and refresh it or return a default value in case the object is not found inside the cache
   *
   * @param clazz the clazz of the catchable for casting
   * @param predicate the predicate to match the catchable
   * @param def the default value to provide if the catchable is not found in cache
   * @param <T> the type of the catchable
   * @return the catchable if found else the default value
   */
  @NonNull
  default <T extends Catchable> T getOr(
      @NonNull Class<T> clazz, @NonNull T def, @NonNull Predicate<T> predicate) {
    return Validate.notNullOr(this.get(clazz, predicate), def);
  }

  /**
   * Get an object from cache and refresh it or return a default value supplied by a {@link Supplier} in case the object is not found inside the cache
   *
   * @param clazz the clazz of the catchable for casting
   * @param predicate the predicate to match the catchable
   * @param supplier the supplier to get the default value to provide if not found in cache
   * @param <T> the type of the catchable
   * @return the catchable if found else the default value provided by the supplier
   */
  default <T extends Catchable> T getOrSupply(
      @NonNull Class<T> clazz, @NonNull Predicate<T> predicate, @NonNull Supplier<T> supplier) {
    return Validate.notNullOrGet(this.get(clazz, predicate), supplier);
  }

  /**
   * Get a list of catchables matching a predicate. This will not refresh the objects use {@link
   * #refresh(Catchable)} to refresh them
   *
   * @param clazz the clazz of catchables for casting
   * @param predicate the predicate to match the catchables
   * @param <T> the type of the catchables
   * @return the list of catchables this will not be null but it could be empty
   */
  @NonNull
  default <T extends Catchable> Collection<T> getMany(
      @NonNull Class<T> clazz, @NonNull Predicate<T> predicate) {
    List<T> list = new ArrayList<>();
    for (SoftReference<Catchable> reference : this.copy()) {
      Catchable catchable = reference.get();
      if (catchable != null && clazz.isAssignableFrom(catchable.getClass())) {
        T cast = clazz.cast(catchable);
        if (predicate.test(cast)) {
          list.add(cast);
        }
      }
    }
    return list;
  }

  /**
   * Checks whether an object is inside the cache
   *
   * @param catchable the object to check if it is inside the cache
   * @return true if the object is inside the cache
   */
  default boolean contains(@NonNull Catchable catchable) {
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
   * @throws IllegalStateException if there's an instance of the object in cache already
   */
  default void add(@NonNull Catchable catchable) {
    if (this.contains(catchable)) {
      throw new IllegalStateException(
          "There's already an instance of " + catchable + " inside of the cache");
    }
    this.getMap().put(new SoftReference<>(catchable), this.getTimeToRemove(catchable));
  }

  /**
   * Get the time left of an object inside of cache
   *
   * @param catchable the object to check the time
   * @return the time of the object inside of cache. If the object is null it will return 0 seconds else the value in time with a unit of {@link Unit#MILLISECONDS}
   */
  @NonNull
  default Time getTimeLeft(@NonNull Catchable catchable) {
    for (SoftReference<Catchable> reference : this.copy()) {
      Catchable referenceCatchable = reference.get();
      if (catchable.equals(referenceCatchable)) {
        long toRemove = this.getMap().getOrDefault(reference, -1L);
        long millis = toRemove - System.currentTimeMillis();
        if (millis > 0) {
          return Time.fromMillis(toRemove - System.currentTimeMillis());
        }
      }
    }
    return new Time(0, Unit.SECONDS);
  }

  /**
   * Removes an object from cache
   *
   * @param catchable the object to be removed
   * @return whether the object was removed from cache
   */
  default boolean remove(@NonNull Catchable catchable) {
    if (this.contains(catchable)) {
      return this.getMap().keySet().removeIf(reference -> catchable.equals(reference.get()));
    } else {
      return false;
    }
  }

  /**
   * Refreshes a catchable object
   *
   * @param catchable the object to be cached
   */
  default void refresh(@NonNull Catchable catchable) {
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
  default long getTimeToRemove(@NonNull Catchable catchable) {
    return System.currentTimeMillis() + catchable.getToRemove().millis();
  }

  /**
   * Get a copy of the cache map. To see what is the cache map
   * @see #getMap()
   *
   * @return a copy of the map
   */
  @NonNull
  default Map<SoftReference<Catchable>, Long> copyMap() {
    return new HashMap<>(this.getMap());
  }

  /**
   * This map contains the reference to the cache object and the time in millis for the object to
   * be removed
   *
   * @return the map with the reference and time of the objects
   */
  @NonNull
  Map<SoftReference<Catchable>, Long> getMap();

  @Override
  default void run() {
    // Get a copy of the map to avoid concurrent modification exception
    Map<SoftReference<Catchable>, Long> copy = new HashMap<>(this.getMap());
    for (Map.Entry<SoftReference<Catchable>, Long> entry : copy.entrySet()) {
      SoftReference<Catchable> reference = entry.getKey();
      Long time = entry.getValue();
      if (reference == null) continue;
      Catchable catchable = reference.get();
      if (catchable != null) {
        // Checks if time has expired
        if (time == null || System.currentTimeMillis() >= time) {
          try {
            catchable.onRemove();
            reference.clear();
          } catch (Throwable e) {
            e.printStackTrace();
          }
        }
      }
    }
    this.getMap().keySet().removeIf(reference -> reference.get() == null);
  }
}