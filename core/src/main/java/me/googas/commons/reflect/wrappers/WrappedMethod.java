package me.googas.commons.reflect.wrappers;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import lombok.Getter;
import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.commons.builder.ToStringBuilder;

/**
 * This object represents method wrapped to avoid exceptions such as {@link IllegalAccessException}
 * while giving or getting its values
 */
public class WrappedMethod {

  @Nullable @Getter private final Method method;

  /**
   * Create the wrapped method
   *
   * @param method the method to be rapped
   */
  public WrappedMethod(@Nullable Method method) {
    this.method = method;
    if (method != null) method.setAccessible(true);
  }

  /** Create the wrapped method without initializing its wrap */
  public WrappedMethod() {
    this(null);
  }

  /**
   * Invoke the method
   *
   * @param object the object instance to invoke the method
   * @param params the parameters of the method to be invoked
   * @return the object returned by the method
   */
  @Nullable
  public Object invoke(@Nullable Object object, @Nullable Object... params) {
    if (this.method == null) return null;
    try {
      return this.method.invoke(object, params);
    } catch (IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Check if the method contains the annotation with the given annotation class
   *
   * @param annotationClazz the annotation class to check if the method posses
   * @param <T> the type of the annotation class
   * @return true if
   */
  public <T extends Annotation> boolean hasAnnotation(@NonNull Class<T> annotationClazz) {
    return this.method != null && this.method.isAnnotationPresent(annotationClazz);
  }

  /**
   * Get the return type of the method
   *
   * @return the return type of the method
   */
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
