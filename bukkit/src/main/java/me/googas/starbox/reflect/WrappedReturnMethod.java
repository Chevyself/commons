package me.googas.starbox.reflect;

import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.commons.Validate;
import me.googas.commons.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

public class WrappedReturnMethod<T> extends WrappedMethod {

  @Nullable private final Class<T> returnType;

  public WrappedReturnMethod(@Nullable Method method, @Nullable Class<T> returnType) {
    super(method);
    this.returnType = returnType;
  }

  public WrappedReturnMethod() {
    this(null, null);
  }

  @NonNull
  public T invokeOr(@NonNull Object object, @NotNull T def, @NonNull Object... params) {
    return Validate.notNullOr(this.invoke(object, params), def);
  }

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
