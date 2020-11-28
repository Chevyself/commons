package me.googas.commons.fallback;

import java.util.List;
import lombok.NonNull;
import me.googas.commons.Pagination;

/** A class to handle errors in a more user friendly way */
public interface Fallback {

  /**
   * Create a pagination from the list of errors
   *
   * @return the paginated list of errors
   */
  @NonNull
  default Pagination<String> paginate() {
    return new Pagination<>(this.getErrors());
  }

  /**
   * Get the complete list of errors contained inside this fallback
   *
   * @return the list of errors
   */
  @NonNull
  List<String> getErrors();
}
