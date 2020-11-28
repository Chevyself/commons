package me.googas.commons.fallback;

import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;
import me.googas.commons.Pagination;

/**
 * A class to handle errors in a more user friendly way
 *
 * @deprecated since 1.1.0
 */
public class GoogasFallback {

  /** A list to store the errors messages */
  @NonNull private static final Pagination<String> errors = new Pagination<>(new ArrayList<>());

  /**
   * Add an error to the list
   *
   * @param message the message sent by the error
   */
  public static void addError(@NonNull String message) {
    if (!GoogasFallback.errors.getList().contains(message)) {
      GoogasFallback.errors.getList().add(message);
    }
  }

  /**
   * Get a page of errors
   *
   * @param page the page number to see
   * @return the page of errors
   */
  @NonNull
  public static List<String> getErrors(int page) {
    return GoogasFallback.errors.getPage(page);
  }

  /**
   * Get the max page of errors
   *
   * @param limit the limit of elements per page
   * @return the max page number
   */
  public static int maxPage(int limit) {
    return GoogasFallback.errors.maxPage();
  }

  /**
   * Get the pagination of errors
   *
   * @return the pagination of errors
   */
  @NonNull
  public static Pagination<String> getErrors() {
    return GoogasFallback.errors;
  }
}
