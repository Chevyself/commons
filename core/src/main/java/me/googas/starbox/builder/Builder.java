package me.googas.starbox.builder;

import lombok.NonNull;

/**
 * This object represents an object that will provide the given type by building it
 *
 * @param <T> the type of object to build
 */
public interface Builder<T> {

  /**
   * Build the requested type object
   *
   * @return the built object
   */
  @NonNull
  T build();
}
