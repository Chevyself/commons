package me.googas.commons.modules.data.type;

import java.util.Map;
import lombok.NonNull;

/**
 * When an object can be identified such as a Player with its uuid this can be used to create a map
 * to get it
 */
public interface Identifiable {

  /**
   * Get a simple map of the ways to identify the data
   *
   * @return the map
   */
  @NonNull
  Map<String, Object> getIdentification();
}
