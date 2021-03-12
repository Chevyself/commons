package me.googas.starbox.modules.data.type;

import lombok.NonNull;

/**
 * A currency is a simple object which is used to get the name of the money that {@link Account} is
 * using
 */
public interface Currency {

  @NonNull
  default String format(double value) {
    return String.format("%.2f", value);
  }

  default int getFractionalDigits() {
    return 2;
  }

  /**
   * Get the name of the currency as a singular
   *
   * @return the name of the currency
   */
  @NonNull
  String getName();

  /**
   * Get the name of the currency in plural
   *
   * @return the name of the currency
   */
  @NonNull
  String getNamePlural();
}
