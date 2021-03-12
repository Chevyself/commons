package me.googas.starbox.modules.placeholders;

import com.starfishst.commands.bukkit.utils.BukkitUtils;
import lombok.NonNull;
import me.googas.starbox.Starbox;
import me.googas.starbox.modules.language.LanguageModule;
import org.bukkit.OfflinePlayer;

import java.util.Map;

/** A line that is localized */
public class LocalizedLine extends Line {

  /**
   * Create a line
   *
   * @param raw the raw string of the line
   * @param placeholders the custom placeholders for the line
   * @param position the position where the line goes
   */
  public LocalizedLine(
      @NonNull String raw, @NonNull Map<String, String> placeholders, int position) {
    super(raw, placeholders, position);
  }

  /**
   * Create a line
   *
   * @param raw the raw string of the line
   * @param position the position where the line goes
   */
  public LocalizedLine(@NonNull String raw, int position) {
    super(raw, position);
  }

  /**
   * Create a line
   *
   * @param raw the raw string of the line
   * @param placeholders the custom placeholders for the line
   */
  public LocalizedLine(@NonNull String raw, @NonNull Map<String, String> placeholders) {
    super(raw, placeholders);
  }

  /**
   * Create a line
   *
   * @param raw the raw string of the line
   */
  public LocalizedLine(@NonNull String raw) {
    super(raw);
  }

  @Override
  public @NonNull String build(@NonNull OfflinePlayer player) {
    PlaceholderModule placeholders = this.placeholders();
    LanguageModule language = Starbox.getModuleRegistry().get(LanguageModule.class);
    String raw;
    if (language != null) {
      raw =
          BukkitUtils.build(
              language.getLanguage(player).get(this.getRaw()), this.getPlaceholders());
    } else {
      raw = this.getRaw();
    }
    if (placeholders != null) {
      return placeholders.build(player, raw);
    } else {
      return this.getRaw();
    }
  }
}
