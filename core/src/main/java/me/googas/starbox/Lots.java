package me.googas.starbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import lombok.NonNull;
import me.googas.annotations.Nullable;

/**
 * Static utilities for groups of objects such as {@link List}, {@link java.lang.reflect.Array} or
 * {@link Set}
 */
public class Lots {

  /**
   * Build an array of objects
   *
   * @param objects the objects to put in an array
   * @param <O> object
   * @return the array of objects
   */
  @SafeVarargs
  public static <O> @NonNull O[] array(@NonNull O... objects) {
    return objects;
  }

  /**
   * Build a list of objects
   *
   * @param objects the objects to put in the list
   * @param <O> object
   * @return the list of objects
   */
  @SafeVarargs
  @NonNull
  public static <O> List<O> list(@NonNull O... objects) {
    return new ArrayList<>(Arrays.asList(objects));
  }

  /**
   * Removes a element from an array and converts it into a {@link List}
   *
   * @param array the old array
   * @param position the position to remove
   * @param <O> {@link Object}
   * @return the new list with the removed element
   * @throws IllegalArgumentException if position is less than 0
   */
  public static <O> List<O> removeAndList(O[] array, int position) {
    if (position < 0) {
      throw new IllegalArgumentException("Position cannot be less than 0");
    } else {
      List<O> list = new ArrayList<>();
      for (int i = 0; i < array.length; i++) {
        if (i != position) {
          list.add(array[i]);
        }
      }
      return list;
    }
  }

  /**
   * Removes a element from an array
   *
   * @param array the old array
   * @param position the position to remove
   * @return the new array
   * @throws IllegalArgumentException if position is less than 0
   */
  public static String[] remove(String[] array, int position) {
    if (position < 0) {
      throw new IllegalArgumentException("Position cannot be less than 0");
    } else {
      String[] newArr = new String[array.length - 1];
      for (int i = 0; i < array.length; i++) {
        if (i < position) {
          newArr[i] = array[i];
        } else if (i > position) {
          newArr[i - 1] = array[i];
        }
      }
      return newArr;
    }
  }

  /**
   * Get an array from certain position from another one
   *
   * @param position the position to get the array from
   * @param array the array to get the new from
   * @return the new array with the objects from the array at a certain position
   */
  @NonNull
  public static String[] arrayFrom(int position, String[] array) {
    if (position < 0) {
      throw new IllegalArgumentException("Position cannot be less than 0");
    } else if (position > array.length) {
      throw new IllegalArgumentException("Position cannot be higher than array length");
    } else {
      String[] newArr = new String[array.length - position];
      int newArrPosition = 0;
      for (int i = position; i < array.length; i++) {
        newArr[newArrPosition] = array[i];
        newArrPosition++;
      }
      return newArr;
    }
  }

  /**
   * Gives a pretty string of a collection
   *
   * @param collection the collection to give as string
   * @param <O> the type of the collection
   * @return a string given by the collection
   */
  @NonNull
  public static <O> String pretty(@NonNull Collection<O> collection) {
    return collection.toString().replace("[", "").replace("]", "");
  }

  @NonNull
  public static <O> String pretty(@NonNull Collection<O> collection, @Nullable String empty) {
    if (collection.isEmpty()) return empty == null ? "Empty" : empty;
    StringBuilder builder = Strings.getBuilder();
    boolean first = true;
    for (O obj : collection) {
      if (first) {
        builder.append("- ").append(obj);
        first = false;
      } else {
        builder.append("\n- ").append(obj);
      }
    }
    return builder.toString();
  }

  /**
   * Adds all the elements from another list that assert the predicate
   *
   * @param list the list to add the matching elements
   * @param toAdd the elements to test
   * @param bol the predicate to assert
   * @param <O> the type of the objects
   * @return the list that got the elements added
   */
  public static <O> List<O> addIf(
      @NonNull List<O> list, @NonNull List<O> toAdd, @NonNull Predicate<O> bol) {
    for (O object : toAdd) {
      if (bol.test(object)) {
        list.add(object);
      }
    }
    return list;
  }

  /**
   * Create an immutable list
   *
   * @param objects the objects to put inside the list
   * @param <O> the type of objects inside the list
   * @return the immutable list
   */
  @SafeVarargs
  public static <O> List<O> inmutable(@NonNull O... objects) {
    return Collections.unmodifiableList(Lots.list(objects));
  }

  /**
   * Create a set of objects
   *
   * @param objects the objects to get as set
   * @param <O> the type of the objects
   * @return the created set of objects
   */
  @SafeVarargs
  @NonNull
  public static <O> Set<O> set(@NonNull O... objects) {
    return new HashSet<>(Lots.list(objects));
  }

  /**
   * Get the clazz of the objects inside a list. If the list is empty it will return null
   *
   * @param list the list to get the class from
   * @return the clazz of the list or empty if there's no object in the list
   */
  public static Class<?> getClazz(@NonNull List<?> list) {
    if (list.isEmpty()) {
      return null;
    }
    return list.get(0).getClass();
  }
}
