package me.googas.commons.compatibilities.papi;

import lombok.NonNull;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.googas.annotations.Nullable;
import me.googas.commons.Starbox;
import me.googas.commons.modules.Module;
import me.googas.commons.modules.placeholders.Placeholder;
import me.googas.commons.modules.placeholders.PlaceholderModule;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * This handler makes possible that the placeholders registered in Starbox can also be used in PAPI
 */
public class PAPIPlaceholderHandler implements Module {
  @NonNull
  public String build(@NonNull OfflinePlayer player, @NonNull String raw) {
    return PlaceholderAPI.setPlaceholders(player, raw);
  }

  @Override
  public @NonNull String getName() {
    return "PAPIPlaceholders";
  }

  @Override
  public void onEnable() {
    new StarboxPlaceholderExpansion().register();
  }

  public static class StarboxPlaceholderExpansion extends PlaceholderExpansion {

    @Nullable
    public Placeholder getPlaceholder(@NonNull String name) {
      return Starbox.getModuleRegistry().require(PlaceholderModule.class).getPlaceholder(name);
    }

    @Nullable
    public String get(@NonNull OfflinePlayer player, @NonNull String name) {
      Placeholder placeholder =
          this.getPlaceholder(name.startsWith("starbox.") ? name.substring(8) : name);
      return placeholder == null ? null : placeholder.build(player);
    }

    @Override
    public @NonNull String getIdentifier() {
      return "me/googas/starbox";
    }

    @Override
    public @NonNull String getAuthor() {
      return "Starbox";
    }

    @Override
    public @NonNull String getVersion() {
      return "1.0";
    }

    @Override
    public boolean persist() {
      return true;
    }

    @Override
    public boolean canRegister() {
      return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NonNull String params) {
      return this.get(player, params);
    }

    @Override
    public String onPlaceholderRequest(Player player, @NonNull String params) {
      return this.get(player, params);
    }
  }
}
