package me.googas.commons.modules.placeholders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.commands.bukkit.utils.BukkitUtils;
import me.googas.commons.Starbox;
import org.bukkit.OfflinePlayer;

/** This represents a line in a layout */
public class Line {

  /** The raw string of the line */
  @NonNull @Getter private final String raw;

  @NonNull @Getter private final Map<String, String> placeholders;

  /** The position of the line */
  @Getter private final int position;

  /**
   * Create a line
   *
   * @param raw the raw string of the line
   * @param placeholders the custom placeholders for this line
   * @param position the position where the line goes
   */
  public Line(@NonNull String raw, @NonNull Map<String, String> placeholders, int position) {
    this.raw = raw;
    this.placeholders = placeholders;
    this.position = position;
  }

  /**
   * Create a line
   *
   * @param raw the raw string of the line
   * @param position the position where the line goes
   */
  public Line(@NonNull String raw, int position) {
    this(raw, new HashMap<>(), position);
  }

  /**
   * Create a line
   *
   * @param raw the raw string of the line
   * @param placeholders the custom placeholders for this line
   */
  public Line(@NonNull String raw, @NonNull Map<String, String> placeholders) {
    this(raw, placeholders, 0);
  }

  /**
   * Create a line
   *
   * @param raw the raw string of the line
   */
  public Line(@NonNull String raw) {
    this(raw, 0);
  }

  /**
   * Parse a layout from a list of strings
   *
   * @param list the list of strings
   * @param reverse whether the list of lines should be reversed to match the minecraft layout
   * @return the parsed layout
   */
  @NonNull
  public static List<Line> parseLayout(@NonNull List<String> list, boolean reverse) {
    List<Line> lines = new ArrayList<>();
    if (reverse) {
      Collections.reverse(list);
    }
    for (int i = 0; i < list.size(); i++) {
      lines.add(Line.parse(list.get(i), i));
    }
    return lines;
  }

  @NonNull
  public static Line parse(@NonNull String raw, int position) {
    if (raw.startsWith("localized:")) {
      return new LocalizedLine(raw.substring(10), position);
    }
    return new Line(raw, position);
  }

  /**
   * Builds this line for a player
   *
   * @param player the player to build this line to
   * @return the built line
   */
  @NonNull
  public String build(@NonNull OfflinePlayer player) {
    PlaceholderModule placeholders = this.placeholders();
    String raw = BukkitUtils.build(this.raw, this.placeholders);
    if (placeholders != null) return placeholders.build(player, raw);
    return raw;
  }

  @Nullable
  public PlaceholderModule placeholders() {
    return Starbox.getModuleRegistry().get(PlaceholderModule.class);
  }
}
