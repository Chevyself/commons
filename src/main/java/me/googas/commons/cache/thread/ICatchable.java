package me.googas.commons.cache.thread;

import me.googas.commons.time.Time;
import me.googas.commons.time.Unit;
import org.jetbrains.annotations.NotNull;

/** This represents an object that can be stored inside {@link Cache} */
public interface ICatchable {

  /** Adds this object to the cache */
  default void addToCache() {
    Cache.add(this.refresh());
  }

  /**
   * Refreshes the time staying inside the cache
   *
   * @return for convenience return this same object
   */
  @NotNull
  ICatchable refresh();

  /**
   * Reduces the seconds left by certain amount
   *
   * @param amount the amount of seconds reduce the seconds left
   */
  void reduceTime(long amount);

  /**
   * Unloads this from the cache
   *
   * @param onRemove whether to call {@link #onRemove()}
   */
  default void unload(boolean onRemove) {
    if (onRemove) {
      this.onRemove();
    }
    Cache.remove(this);
  }

  /** Unloads this from the cache. Calls {@link #unload(boolean)} as true */
  default void unload() {
    this.unload(true);
  }

  /** When seconds have passed inside the cache this method will be called */
  void onSecondPassed();

  /** When the seconds left is less than 0 and the object will be removed this will be called */
  void onRemove();

  /**
   * Get the seconds left of the object inside the cache
   *
   * @return the seconds left of the object inside the cache
   */
  long getSecondsLeft();

  /**
   * Get the seconds left but as a time object for different usages
   *
   * @return the seconds left as time
   */
  @NotNull
  default Time getTimeLeft() {
    return new Time(this.getSecondsLeft(), Unit.SECONDS);
  }
}
