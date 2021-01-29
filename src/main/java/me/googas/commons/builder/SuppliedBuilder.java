package me.googas.commons.builder;

import lombok.NonNull;
import me.googas.annotations.Nullable;

/**
 * Just like {@link Builder} but the type T represents the parameter to get the object.
 *
 * @param <T> the type of the parameter
 * @param <O> the type of the built object
 */
public interface SuppliedBuilder<T, O> {

  @Nullable
  O build(@NonNull T t);
}
