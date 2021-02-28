package me.googas.commons.cache;

import lombok.NonNull;
import me.googas.commons.time.Time;

/** An object which can be stored inside {@link Cache} */
public interface Catchable {

  /**
   * Called when the cache is ready to remove this object. This will be called in {@link Cache#run()}
   *
   * @throws Throwable in case something goes wrong while unloading this object
   */
  default void onRemove() throws Throwable {}

  /**
   * Get the time for the object to be removed from cache
   *
   * @return the time to be removed
   */
  @NonNull
  Time getToRemove();
}
