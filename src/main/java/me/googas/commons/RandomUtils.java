package me.googas.commons;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import lombok.NonNull;

/** Static utilities for randomization */
public class RandomUtils {

  /** The java Random instance */
  @NonNull private static final Random random = new Random();
  /** Upper case letters. Used for random strings */
  @NonNull private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  /** Lower case letters. Used for random strings */
  @NonNull private static final String LOWER_LETTERS = "abcdefghijklmnopqrstuvwxyz";
  /** Numbers inside a string. Used for random strings */
  @NonNull private static final String NUMBERS = "1234567890";

  /**
   * Create a random string with the provided characters. It will loop getting random characters
   * from the charsString until it reaches the provided length
   *
   * @param charsString the provided characters
   * @param length the length of the new string
   * @return the randomly generated string
   */
  public static String nextString(@NonNull String charsString, int length) {
    char[] text = new char[length];
    for (int i = 0; i < length; i++) {
      text[i] = charsString.charAt(RandomUtils.random.nextInt(charsString.length()));
    }
    return new String(text);
  }

  /**
   * Create a random string with letters and numbers using {@link #nextString(String, int)} and
   * providing {@link #LETTERS}, {@link #LOWER_LETTERS} and {@link #NUMBERS}
   *
   * @param length the length of the new string
   * @return the randomly generated string
   */
  public static String nextString(int length) {
    return RandomUtils.nextString(
        RandomUtils.LETTERS + RandomUtils.LOWER_LETTERS + RandomUtils.NUMBERS, length);
  }

  /**
   * Get an integer inside a bound. The bound is created as follows:
   *
   * <p>i = ((max - min) + 1) + min
   *
   * @param min the minimum number of the bound
   * @param max the maximum number of the bound
   * @return the random generated integer inside the bounds
   */
  public static int nextInt(int min, int max) {
    return RandomUtils.random.nextInt(((max - min) + 1) + min);
  }

  /**
   * Get a double inside a bound. The bound is created as follows:
   *
   * <p>i = min + ((1 + max - min) * {@link Math#random()});
   *
   * @param min the minimum double of the bound
   * @param max the maximum double of the bound
   * @return the random generated double inside the bounds
   */
  public static double nextDouble(double min, double max) {
    if (max < min) {
      return RandomUtils.nextDouble(max, min);
    } else {
      return min + ((1 + max - min) * Math.random());
    }
  }

  /**
   * This method uses {@link #nextDouble(double, double)} but {@link Math#floor(double)} the result
   *
   * @param min the minimum double of the bound
   * @param max the maximum double of the bound
   * @return the random generated and {@link Math#floor(double)} double inside the bound.
   */
  public static double nextDoubleFloor(double min, double max) {
    return Math.floor(RandomUtils.nextDouble(min, max));
  }

  /**
   * Create a random string only using letters using {@link #nextString(String, int)} and providing
   * {@link #LETTERS} and {@link #LOWER_LETTERS}
   *
   * @param length the length of the new string
   * @return the randomly generated string
   */
  public static String nextStringLetters(int length) {
    return RandomUtils.nextString(RandomUtils.LETTERS + RandomUtils.LOWER_LETTERS, length);
  }

  /**
   * Create a random string only using {@link #nextString(String, int)} and providing {@link
   * #LETTERS}
   *
   * @param length the length of the new string
   * @return the randomly generated string
   */
  public static String nextStringUpper(int length) {
    return RandomUtils.nextString(RandomUtils.LETTERS, length);
  }

  /**
   * Create a random string only using {@link #nextString(String, int)} and providing {@link
   * #LOWER_LETTERS}
   *
   * @param length the length of the new string
   * @return the randomly generated string
   */
  public static String nextStringLower(int length) {
    return RandomUtils.nextString(RandomUtils.LOWER_LETTERS, length);
  }

  /**
   * Get a random entry from a collection
   *
   * @param collection the collection to get the random entry
   * @param <O> the type of the entries inside the collection
   * @return the entry
   */
  @NonNull
  public static <O> O getRandom(@NonNull Collection<O> collection) {
    int random = (int) (Math.random() * collection.size());
    for (O entry : collection) {
      if (--random < 0) return entry;
    }
    throw new IllegalArgumentException("Random collection cannot be empty!");
  }

  /**
   * Get an amount of randomly selected items from a collection
   *
   * @param collection the collection to get the entries
   * @param amount the amount of entries to get
   * @param <O> the type of the objects in the collection
   * @return the list containing the random entries
   */
  @NonNull
  public static <O> List<O> getRandom(@NonNull Collection<O> collection, int amount) {
    if (amount > collection.size())
      throw new IllegalStateException(
          "The amount cannot be higher than the size of the collection");
    List<O> list = new ArrayList<>();
    while (list.size() < amount) {
      O random = RandomUtils.getRandom(collection);
      if (!list.contains(random)) list.add(random);
    }
    return list;
  }

  /**
   * Get the {@link Random} object used for the generation of randomness for this utility
   *
   * @return the object
   */
  @NonNull
  public static Random getRandom() {
    return RandomUtils.random;
  }
}
