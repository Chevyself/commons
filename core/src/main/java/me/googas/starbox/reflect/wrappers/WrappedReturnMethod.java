package me.googas.starbox.reflect.wrappers;

import java.lang.reflect.Method;
import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.starbox.Validate;
import me.googas.starbox.builder.ToStringBuilder;

/**
 * Same as {@link WrappedMethod} (as it is extended) but the return type is set as the parameter
 * type of the class
 *
 * @param <T> the type to return on the method
 */
public class WrappedReturnMethod<T> extends WrappedMethod {

  @Nullable private final Class<T> returnType;

  /**
   * Create the wrapped return method
   *
   * @param method the method to be wrapped
   * @param returnType the class of the object to return
   */
  public WrappedReturnMethod(@Nullable Method method, @Nullable Class<T> returnType) {
    super(method);
    this.returnType = returnType;
  }

  /** Create the wrapped return method without initializing the method or return type */
  public WrappedReturnMethod() {
    this(null, null);
  }

  /**
   * @see #invoke(Object, Object...) If the invoke is null the default object will be returned
   * @param object the object instance to invoke the method
   * @param def the default object to return in case invoke is null
   * @param params the parameters of the method to be invoked
   * @return the returned object from the execution of the method or the default value
   */
  @NonNull
  public T invokeOr(@NonNull Object object, @NonNull T def, @NonNull Object... params) {
    return Validate.notNullOr(this.invoke(object, params), def);
  }

  // Overridden from WrappedMethod
  @Nullable
  @Override
  public T invoke(@Nullable Object object, @NonNull Object... params) {
    if (this.returnType == null) return null;
    Object t = super.invoke(object, params);
    if (t != null && this.returnType.isAssignableFrom(t.getClass())) return this.returnType.cast(t);
    return null;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("returnType", this.returnType)
        .append("super", super.toString())
        .build();
  }
}
