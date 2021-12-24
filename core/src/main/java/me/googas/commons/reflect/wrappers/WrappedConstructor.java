package me.googas.commons.reflect.wrappers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import lombok.Getter;
import me.googas.annotations.Nullable;
import me.googas.commons.builder.ToStringBuilder;

/**
 * This class represents a constructor wrapped to avoid exceptions such as {@link
 * IllegalAccessException}
 */
public class WrappedConstructor {

  @Nullable @Getter private final Constructor<?> constructor;

  /**
   * Create the wrapped constructor
   *
   * @param constructor the constructor to wrap
   */
  public WrappedConstructor(@Nullable Constructor<?> constructor) {
    this.constructor = constructor;
    if (this.constructor != null) this.constructor.setAccessible(true);
  }

  /** Create the wrapped constructor without a wrap */
  public WrappedConstructor() {
    this(null);
  }

  /**
   * Invoke the constructor
   *
   * @param args the arguments required to run the constructor
   * @return the object that creates the constructor if it could be invoked else null
   */
  public Object invoke(@Nullable Object... args) {
    if (this.constructor != null) {
      try {
        return this.constructor.newInstance(args);
      } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("constructor", this.constructor).build();
  }
}
