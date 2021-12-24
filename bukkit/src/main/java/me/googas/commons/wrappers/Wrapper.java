package me.googas.commons.wrappers;

import me.googas.annotations.Nullable;

public interface Wrapper {

  /**
   * Get the wrapped object
   *
   * @return the wrapped object
   */
  @Nullable
  Object get();

  /**
   * Set the wrapped object
   *
   * @param object the new wrapped object
   */
  void set(@Nullable Object object);
}
