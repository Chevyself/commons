package me.googas.starbox.reflect;

import lombok.Getter;
import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.commons.builder.ToStringBuilder;
import me.googas.starbox.Starbox;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;

public class WrappedMethod {

  @Nullable @Getter private final Method method;

  public WrappedMethod(@Nullable Method method) {
    this.method = method;
    if (method != null) method.setAccessible(true);
  }

  public WrappedMethod() {
    this(null);
  }

  @NonNull
  public static WrappedMethod parse(
      @NonNull Class<?> clazz, @NonNull String name, @NonNull Class<?>... params) {
    return new WrappedClass(clazz).getMethod(name, params);
  }

  @NonNull
  public static WrappedMethod parse(
      @NonNull String className, @NonNull String name, @NonNull Class<?> params) {
    return WrappedClass.parse(className).getMethod(params, name);
  }

  @Nullable
  public Object invoke(@Nullable Object object, @Nullable Object... params) {
    if (this.method == null) return null;
    try {
      return this.method.invoke(object, params);
    } catch (IllegalAccessException | InvocationTargetException e) {
      Starbox.getPlugin().getLogger().log(Level.SEVERE, e, () -> "");
    }
    return null;
  }

  @Nullable
  public Class<?> getReturnType() {
    if (this.method == null) return null;
    return this.method.getReturnType();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("method", this.method).build();
  }
}
