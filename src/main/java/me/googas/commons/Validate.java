package me.googas.commons;

import java.util.function.Supplier;
import lombok.Getter;
import lombok.NonNull;
import me.googas.commons.fallback.Fallback;
import me.googas.commons.fallback.SimpleFallback;

/** Static method to validate certain stuff like booleans, non nulls, etc */
public class Validate {

  /** This contains a simple fallback used to store the errors given */
  @NonNull @Getter private static final Fallback fallback = new SimpleFallback();

  /**
   * Validate that a boolean is true else throw an exception
   *
   * @param toAssert the boolean to assert to true
   * @param message the message to send if the boolean is not true
   */
  public static void assertTrue(boolean toAssert, String message) {
    if (!toAssert) {
      throw new IllegalArgumentException(message == null ? "This must assert true!" : message);
    }
  }

  /**
   * Validate that a boolean is false else throw an exception
   *
   * @param toAssert the boolean to assert to false
   * @param message the message to send if the boolean is not false
   */
  public static void assertFalse(boolean toAssert, String message) {
    if (toAssert) {
      throw new IllegalArgumentException(message == null ? "This must assert false!" : message);
    }
  }

  /**
   * Validates that an object is not null and thrown a {@link NullPointerException} with a message
   * if it is null
   *
   * @param object the object to check that is not null
   * @param message the message to send if it is null
   * @param <O> the type of the object
   * @return the object if it is not null
   * @throws NullPointerException if the object is null with the provided message if given one
   */
  @NonNull
  public static <O> O notNull(O object, String message) {
    return Validate.notNull(object, new NullPointerException(message));
  }

  /**
   * Validates that an object is not null else throw a provided exception
   *
   * @param object the object to validate that is not null
   * @param toThrow the exception to throw if it is null
   * @param <O> the type of the object
   * @param <T> the type of the exception
   * @return the object if it is not null
   * @throws T the exception in the parameter if the object is null
   */
  @NonNull
  public static <O, T extends Throwable> O notNull(O object, @NonNull T toThrow) throws T {
    if (object != null) {
      return object;
    } else {
      throw toThrow;
    }
  }

  /**
   * Validate that the object is not null or give another object but giving an error to the {@link
   * #fallback}
   *
   * @param object the object to check that is not null
   * @param def the default object in case the object is null
   * @param message the message to put in the fallback in case the object checking is null
   * @param <O> the type of the object
   * @return the object if not null else the default object
   */
  @NonNull
  public static <O> O notNullOr(O object, O def, String message) {
    if (object != null) {
      return object;
    } else {
      if (message != null) {
        Validate.fallback.getErrors().add(message);
      }
      return def;
    }
  }

  /**
   * Validate that the object is not null or give another object
   *
   * @param object the object to check that is not null
   * @param def the default object in case that the object to check is null
   * @param <O> the type of the object
   * @return the object if not null else the default object
   */
  @NonNull
  public static <O> O notNullOr(O object, @NonNull O def) {
    return object == null ? def : object;
  }

  /**
   * Validate that the object is not null or give another object
   *
   * @param object the object to check that is not null
   * @param supplier the supplier to get the default object in case that the object to check is null
   * @param <O> the type of the object
   * @return the object if not null else the default object
   */
  public static <O> O notNullOrGet(O object, @NonNull Supplier<O> supplier) {
    if (object != null) {
      return object;
    } else {
      return supplier.get();
    }
  }
}
