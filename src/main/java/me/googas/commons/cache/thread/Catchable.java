package me.googas.commons.cache.thread;

import me.googas.commons.time.Time;
import me.googas.commons.time.Unit;
import org.jetbrains.annotations.NotNull;

/** This is an object that can be put inside the cache */
public abstract class Catchable implements ICatchable {

  /** The time to remove the object from the cache */
  @NotNull private transient Time toRemove;
  /** The seconds left of the object inside the cache */
  private transient long secondsLeft;

  /**
   * Create an instance
   *
   * @param toRemove the time to remove the object from the cache
   * @param addToCache whether this object should be added to the cache
   */
  public Catchable(@NotNull Time toRemove, boolean addToCache) {
    this.toRemove = toRemove;
    if (addToCache) {
      this.addToCache();
    }
  }

  /**
   * Create an instance. This constructor will add the object to the cache
   *
   * @param toRemove the time to remove the object from the cache
   */
  public Catchable(@NotNull Time toRemove) {
    this(toRemove, true);
  }

  /**
   * Create an instance. This constructor will not add the object to cache and {@link #toRemove}
   * will be 0 seconds
   */
  public Catchable() {
    this(new Time(0, Unit.SECONDS), false);
  }

  @Override
  @NotNull
  public Catchable refresh() {
    this.secondsLeft = this.toRemove.getAs(Unit.SECONDS).getValue();
    return this;
  }

  @Override
  public long getSecondsLeft() {
    return this.secondsLeft;
  }

  @Override
  public void reduceTime(long amount) {
    this.secondsLeft -= amount;
  }
}
