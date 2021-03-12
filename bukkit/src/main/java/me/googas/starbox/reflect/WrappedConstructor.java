package me.googas.starbox.reflect;

import lombok.Getter;
import me.googas.annotations.Nullable;
import me.googas.commons.builder.ToStringBuilder;
import me.googas.starbox.Starbox;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;

public class WrappedConstructor {

  @Nullable @Getter private final Constructor<?> constructor;

  public WrappedConstructor(@Nullable Constructor<?> constructor) {
    this.constructor = constructor;
    if (this.constructor != null) this.constructor.setAccessible(true);
  }

  public WrappedConstructor() {
    this(null);
  }

  public Object invoke(@Nullable Object... args) {
    if (this.constructor != null) {
      try {
        return this.constructor.newInstance(args);
      } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
        Starbox.getPlugin().getLogger().log(Level.SEVERE, e, () -> "");
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("constructor", this.constructor).build();
  }
}
