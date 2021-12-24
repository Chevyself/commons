package me.googas.commons.modules.placeholders;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.NonNull;
import lombok.experimental.Delegate;
import me.googas.annotations.Nullable;
import me.googas.commands.bukkit.utils.BukkitUtils;
import me.googas.commons.Starbox;
import me.googas.commons.compatibilities.papi.PAPIPlaceholderHandler;
import me.googas.commons.modules.Module;
import org.bukkit.OfflinePlayer;

public class PlaceholderModule implements Module {

  public static final Pattern PATTERN = Pattern.compile("%.*?%");

  @NonNull @Delegate private final Set<Placeholder> placeholders = new HashSet<>();

  @Nullable
  public Placeholder getPlaceholder(@NonNull String name) {
    for (Placeholder placeholder : this.placeholders) {
      if (placeholder.getName().equalsIgnoreCase(name)) return placeholder;
    }
    return null;
  }

  public String build(@NonNull OfflinePlayer player, @NonNull String raw) {
    if (Starbox.isPAPIEnabled()) {
      PAPIPlaceholderHandler handler =
          Starbox.getModuleRegistry().get(PAPIPlaceholderHandler.class);
      if (handler != null) return handler.build(player, raw);
    }
    Matcher matcher = PlaceholderModule.PATTERN.matcher(raw);
    while (matcher.find()) {
      String name = matcher.group().replace("%", "");
      Placeholder placeholder = this.getPlaceholder(name);
      if (placeholder != null) raw = raw.replace("%" + name + "%", placeholder.build(player));
    }
    return BukkitUtils.build(raw);
  }

  @Override
  public @NonNull String getName() {
    return "placeholders";
  }
}
