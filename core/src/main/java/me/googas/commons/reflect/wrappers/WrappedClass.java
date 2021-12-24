package me.googas.commons.reflect.wrappers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.commons.Lots;

/**
 * This object represents a class but the methods here prevent exceptions such as {@link
 * NoSuchMethodException}
 */
public class WrappedClass {

  @Nullable @Getter private final Class<?> clazz;

  /**
   * Create the wrapped class
   *
   * @param clazz the class to be wrapped
   */
  public WrappedClass(@Nullable Class<?> clazz) {
    this.clazz = clazz;
  }

  /** Create the wrapped class without initializing it */
  public WrappedClass() {
    this(null);
  }

  /**
   * Compares to arrays of types. This checks that both are not null, the length are the same and
   * finally all the types in the same position are equal
   *
   * @param paramTypes the first array of types
   * @param params the second array of types
   * @return true if all conditions meet
   */
  public static boolean compareParameters(
      @Nullable Class<?>[] paramTypes, @Nullable Class<?>[] params) {
    if (paramTypes == null || params == null) return false;
    if (paramTypes.length != params.length) return false;
    for (int i = 0; i < paramTypes.length; i++) {
      if (paramTypes[i] != params[i]) return false;
    }
    return true;
  }

  /**
   * Parse a wrapped class by its name
   *
   * @param name the name of the wrapped class to get
   * @return the wrapped class if found else an empty wrapped class
   */
  @NonNull
  public static WrappedClass parse(@NonNull String name) {
    try {
      return new WrappedClass(Class.forName(name));
    } catch (ClassNotFoundException ignored) {
    }
    return new WrappedClass();
  }

  /**
   * Get the constructor with the given parameters
   *
   * @param params the classes of the parameters to match
   * @return the constructor of the class or empty if the constructor does not exist
   */
  @NonNull
  public WrappedConstructor getConstructor(@Nullable Class<?>... params) {
    if (this.clazz != null && this.hasConstructor(params)) {
      try {
        return new WrappedConstructor(this.clazz.getConstructor(params));
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      }
    }
    return new WrappedConstructor();
  }

  /**
   * Get a field of the class by its name
   *
   * @param name the name of the field
   * @return the wrapped field if found else an empty wrapped field
   */
  @NonNull
  public WrappedField getField(@NonNull String name) {
    if (this.clazz != null && this.hasField(name)) {
      try {
        return new WrappedField(this.clazz.getField(name));
      } catch (NoSuchFieldException e) {
        e.printStackTrace();
      }
    }
    return new WrappedField();
  }

  /**
   * Get a declared field from the class by its name
   *
   * @param name the name of the declared field
   * @return the field if found else an empty wrapped field
   */
  @NonNull
  public WrappedField getDeclaredField(@NonNull String name) {
    if (this.clazz != null && this.hasDeclaredField(name)) {
      try {
        return new WrappedField(this.clazz.getDeclaredField(name));
      } catch (NoSuchFieldException e) {
        e.printStackTrace();
      }
    }
    return new WrappedField();
  }

  /**
   * Get a method of the class by its name and its parameter types
   *
   * @param name the name of the method
   * @param params the types of the parameters of the method
   * @return the method if found else an empty wrapped method
   */
  @NonNull
  public WrappedMethod getMethod(@NonNull String name, @Nullable Class<?>... params) {
    if (this.clazz != null && this.hasMethod(name, params)) {
      try {
        return new WrappedMethod(this.clazz.getMethod(name, params));
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      }
    }
    return new WrappedMethod();
  }

  /**
   * Get a method of the class by its name, its parameter types and its return type
   *
   * @param returnType the type of the returned object
   * @param name the name of the method
   * @param params the types of the parameters of the method
   * @param <T> the type of object that the method returns
   * @return the method if found else an empty wrapped method
   */
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

  /**
   * Checks whether the class has a method with the given name and parameters
   *
   * @param name the name of the method to check if it exists
   * @param params the types of the parameters of the method
   * @return true if the class has the method
   */
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

  /**
   * Check whether the class has a method ith the given name and parameters
   *
   * @param name the name of the method to check if it exists
   * @return true if the class has the method
   */
  public boolean hasField(@NonNull String name) {
    if (this.clazz != null) {
      for (Field field : this.clazz.getFields()) {
        if (field.getName().equals(name)) return true;
      }
    }
    return false;
  }

  /**
   * Check whether the class has a declared field by its name
   *
   * @param name the name of the declared field
   * @return true if the class contains the declared field with the given name
   */
  public boolean hasDeclaredField(@NonNull String name) {
    if (this.clazz != null) {
      for (Field field : this.clazz.getDeclaredFields()) {
        if (field.getName().equals(name)) return true;
      }
    }
    return false;
  }

  /**
   * Check whether the class has the given constructor
   *
   * @param params the types of parameters of the constructor
   * @return true if the class has the constructor with the given parameters
   */
  public boolean hasConstructor(@Nullable Class<?>... params) {
    if (this.clazz == null) return false;
    for (Constructor<?> constructor : this.clazz.getConstructors()) {
      Class<?>[] paramTypes = constructor.getParameterTypes();
      return WrappedClass.compareParameters(paramTypes, params);
    }
    return false;
  }

  @NonNull
  public <T extends Annotation> List<WrappedMethod> getMethods(@NonNull Class<T> annotationClazz) {
    List<WrappedMethod> methods = new ArrayList<>();
    if (this.clazz != null) {
      for (Method method : this.clazz.getMethods()) {
        WrappedMethod wrappedMethod = new WrappedMethod(method);
        if (wrappedMethod.hasAnnotation(annotationClazz)) methods.add(wrappedMethod);
      }
    }
    return methods;
  }

  /**
   * Get the list of methods from this class
   *
   * @return the list of methods of the class
   */
  @NonNull
  public List<Method> getMethods() {
    if (this.clazz != null) {
      return Lots.list(this.clazz.getMethods());
    }
    return new ArrayList<>();
  }

  /**
   * Get the list of fields from this class
   *
   * @return the list of fields of the class
   */
  @NonNull
  public List<Field> getFields() {
    if (this.clazz != null) {
      return Lots.list(this.clazz.getFields());
    }
    return new ArrayList<>();
  }

  /**
   * Get the list of declared fields from this class
   *
   * @return the list of declared fields of the class
   */
  @NonNull
  public List<Field> getDeclaredFields() {
    if (this.clazz != null) {
      return Lots.list(this.clazz.getDeclaredFields());
    }
    return new ArrayList<>();
  }
}
