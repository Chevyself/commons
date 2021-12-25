package me.googas.commons.modules.language;

import java.util.Map;
import lombok.NonNull;
import me.googas.commands.bukkit.utils.BukkitUtils;
import me.googas.commons.maps.MapBuilder;

public interface Language {

  /**
   * Get the string or the path to loadJson it
   *
   * @param path the path to the string
   * @return the string if the path leads to one else the path
   */
  @NonNull
  String get(@NonNull String path);

  /**
   * Get the string and build it with placeholders. It will replace the placeholders that are inside
   * a "%" character
   *
   * @param path the path that leads to the string
   * @param placeholders the string to build the string
   * @return the built string
   */
  default @NonNull String get(@NonNull String path, @NonNull Map<String, String> placeholders) {
    return BukkitUtils.format(this.get(path), placeholders);
  }

  /**
   * Get the string and build it with placeholders using a builder. It will replace the placeholders
   * that are inside a "%" character
   *
   * @param path the path that leads to the string
   * @param placeholders the string to build the string
   * @return the built string
   */
  default @NonNull String get(
      @NonNull String path, @NonNull MapBuilder<String, String> placeholders) {
    return BukkitUtils.format(this.get(path), placeholders.build());
  }

  @NonNull
  String getLocale();
}
