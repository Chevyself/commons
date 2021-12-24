package me.googas.commons.fallback;

import java.util.List;
import lombok.NonNull;
import me.googas.commons.Pagination;

/**
 * A class to handle errors in a more user friendly way. This means in a way to get them in the
 * future and check what went wrong
 */
public interface Fallback {

  /**
   * Create a pagination from {@link #getErrors()}
   *
   * @return the paginated list of errors
   */
  @NonNull
  default Pagination<String> paginate() {
    return new Pagination<>(this.getErrors());
  }

  /**
   * Make the fallback process an exception
   *
   * @param exception the throwable/exception to handle
   */
  void process(Throwable exception);

  /**
   * Make the fallback process an exception and a message
   *
   * @param exception the throwable/exception to handle
   * @param message the message to save in the fallback
   */
  void process(Throwable exception, String message);

  /**
   * Get the complete list of errors contained inside this fallback
   *
   * @return the list of errors
   */
  @NonNull
  List<String> getErrors();

  // TODO could use a clear method
}
