package me.googas.commons.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Predicate;
import java.util.function.Supplier;
import me.googas.commons.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** This class acts like a 'Cache' allowing programs to hold objects in memory */
public class Cache {

  /** The timer to run the cache */
  @NotNull private static final Timer timer = new Timer();
  /** The cache */
  @NotNull private static final List<ICatchable> cache = new ArrayList<>();
  /** The timer task that runs the cache */
  @NotNull private static TimerTask task = new CacheTask();

  static {
    // Runs every 1000 millis which is a second
    Cache.timer.schedule(Cache.task, 0, 1000);
  }

  /**
   * Adds an object inside the cache
   *
   * @param catchable the object to put inside the cache
   * @return true if the catchable was added to the list
   */
  public static boolean add(@NotNull ICatchable catchable) {
    return Cache.cache.add(catchable);
  }

  /**
   * Removes an object from the cache
   *
   * @param catchable the object to remove
   */
  public static void remove(@NotNull ICatchable catchable) {
    Cache.cache.remove(catchable);
  }

  /**
   * Check if the cache contains an object
   *
   * @param catchable to check
   * @return true if the cache contains it
   */
  public static boolean contains(@NotNull ICatchable catchable) {
    return Cache.cache.contains(catchable);
  }

  /**
   * Get the list of items so called 'cache'
   *
   * @return the list of items
   */
  @NotNull
  public static List<ICatchable> getCache() {
    return Cache.cache;
  }

  /**
   * Creates a new list with the same objects inside of the cache. This object can be used to
   * manipulate the objects without causing {@link java.util.ConcurrentModificationException}
   *
   * @return the copied list
   */
  @NotNull
  public static List<ICatchable> copy() {
    return new ArrayList<>(Cache.cache);
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
  public static <T extends ICatchable> T getCatchable(
      @NotNull Class<T> clazz, @NotNull Predicate<T> predicate) {
    for (ICatchable catchable : Cache.cache) {
      if (clazz.isAssignableFrom(catchable.getClass())) {
        T cast = clazz.cast(catchable);
        if (predicate.test(cast)) {
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
  public static <T extends ICatchable> T getCatchableOr(
      @NotNull Class<T> clazz, @NotNull Predicate<T> predicate, @NotNull T def) {
    return Validate.notNullOr(Cache.getCatchable(clazz, predicate), def);
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
  @NotNull
  public static <T extends ICatchable> T getCatchableOrGet(
      @NotNull Class<T> clazz, @NotNull Predicate<T> predicate, @NotNull Supplier<T> supplier) {
    return Validate.notNullOr(Cache.getCatchable(clazz, predicate), supplier);
  }

  /** Cancels the task that runs the cache. This means that objects wont be unloaded */
  public static void cancelTask() {
    Cache.task.cancel();
  }

  /**
   * Get the task that the cache needs to run
   *
   * @return the task that the cache uses to run
   */
  @NotNull
  public static TimerTask getTask() {
    return Cache.task;
  }

  /** The task that run every second until the cache unloads */
  static class CacheTask extends TimerTask {

    @Override
    public void run() {
      List<ICatchable> copy = Cache.copy();
      for (ICatchable catchable : copy) {
        catchable.reduceTime(1);
        if (catchable.getSecondsLeft() > 0) {
          catchable.onSecondPassed();
        } else {
          catchable.onRemove();
          catchable.unload(true);
        }
      }
    }
  }
}
