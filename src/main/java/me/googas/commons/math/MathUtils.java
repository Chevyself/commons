package me.googas.commons.math;

/** Static utilities for math */
public class MathUtils {

  /**
   * Get the number queried squared
   *
   * @param number the number
   * @return the number squared
   */
  public static double square(double number) {
    return number * number;
  }

  /**
   * Get the number queried cubed
   *
   * @param number the number
   * @return the number cubed
   */
  public static double cubed(double number) {
    return number * number * number;
  }

  public static double percentage(double total, double num) {
    return num * 100 / total;
  }
}
