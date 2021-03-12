package me.googas.starbox.reflect;

import lombok.Getter;
import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.commons.Lots;
import me.googas.starbox.Starbox;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class WrappedClass {

  @Nullable @Getter private final Class<?> clazz;

  public WrappedClass(@Nullable Class<?> clazz) {
    this.clazz = clazz;
  }

  public WrappedClass() {
    this(null);
  }

  public static boolean compareParameters(
      @Nullable Class<?>[] paramTypes, @Nullable Class<?>[] params) {
    if (paramTypes == null || params == null) return false;
    if (paramTypes.length != params.length) return false;
    for (int i = 0; i < paramTypes.length; i++) {
      if (paramTypes[i] != params[i]) return false;
    }
    return true;
  }

  @NonNull
  public static WrappedClass parse(@NonNull String name) {
    try {
      return new WrappedClass(Class.forName(name));
    } catch (ClassNotFoundException ignored) {
    }
    return new WrappedClass();
  }

  @NonNull
  public WrappedConstructor getConstructor(@Nullable Class<?>... params) {
    if (this.clazz != null && this.hasConstructor(params)) {
      try {
        return new WrappedConstructor(this.clazz.getConstructor(params));
      } catch (NoSuchMethodException e) {
        Starbox.getPlugin().getLogger().log(Level.SEVERE, e, () -> "");
      }
    }
    return new WrappedConstructor();
  }

  @NonNull
  public WrappedField getField(@NonNull String name) {
    if (this.clazz != null && this.hasField(name)) {
      try {
        return new WrappedField(this.clazz.getField(name));
      } catch (NoSuchFieldException e) {
        Starbox.getPlugin().getLogger().log(Level.SEVERE, e, () -> "");
      }
    }
    return new WrappedField();
  }

  @NonNull
  public WrappedField getDeclaredField(@NonNull String name) {
    if (this.clazz != null && this.hasDeclaredField(name)) {
      try {
        return new WrappedField(this.clazz.getDeclaredField(name));
      } catch (NoSuchFieldException e) {
        Starbox.getPlugin().getLogger().log(Level.SEVERE, e, () -> "");
      }
    }
    return new WrappedField();
  }

  @NonNull
  public WrappedMethod getMethod(@NonNull String name, @Nullable Class<?>... params) {
    if (this.clazz != null && this.hasMethod(name, params)) {
      try {
        return new WrappedMethod(this.clazz.getMethod(name, params));
      } catch (NoSuchMethodException e) {
        Starbox.getPlugin().getLogger().log(Level.SEVERE, e, () -> "");
      }
    }
    return new WrappedMethod();
  }

  @NonNull
  public <T> WrappedReturnMethod<T> getMethod(
      @Nullable Class<T> returnType, @NonNull String name, @Nullable Class<?>... params) {
    WrappedMethod method = this.getMethod(name, params);
    if (method.getMethod() != null && returnType != null) {
      Class<?> methodReturnType = method.getReturnType();
      if (methodReturnType != null && returnType.isAssignableFrom(methodReturnType))
        return new WrappedReturnMethod<>(method.getMethod(), returnType);
    }
    return new WrappedReturnMethod<>(null, returnType);
  }

  public boolean hasMethod(@NonNull String name, @Nullable Class<?>... params) {
    if (this.clazz == null) return false;
    for (Method method : this.clazz.getMethods()) {
      if (method.getName().equals(name)) {
        Class<?>[] paramTypes = method.getParameterTypes();
        if (WrappedClass.compareParameters(paramTypes, params)) return true;
      }
    }
    return false;
  }

  public boolean hasField(@NonNull String name) {
    if (this.clazz != null) {
      for (Field field : this.clazz.getFields()) {
        if (field.getName().equals(name)) return true;
      }
    }
    return false;
  }

  public boolean hasDeclaredField(@NonNull String name) {
    if (this.clazz != null) {
      for (Field field : this.clazz.getDeclaredFields()) {
        if (field.getName().equals(name)) return true;
      }
    }
    return false;
  }

  public boolean hasConstructor(@Nullable Class<?>... params) {
    if (this.clazz == null) return false;
    for (Constructor<?> constructor : this.clazz.getConstructors()) {
      Class<?>[] paramTypes = constructor.getParameterTypes();
      return WrappedClass.compareParameters(paramTypes, params);
    }
    return false;
  }

  @NonNull
  public List<Method> getMethods() {
    if (this.clazz != null) {
      return Lots.list(this.clazz.getMethods());
    }
    return new ArrayList<>();
  }

  @NonNull
  public List<Field> getFields() {
    if (this.clazz != null) {
      return Lots.list(this.clazz.getFields());
    }
    return new ArrayList<>();
  }

  @NonNull
  public List<Field> getDeclaredFields() {
    if (this.clazz != null) {
      return Lots.list(this.clazz.getDeclaredFields());
    }
    return new ArrayList<>();
  }
}
