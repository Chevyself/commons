package me.googas.starbox;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.googas.annotations.Nullable;
import me.googas.commons.Validate;
import me.googas.starbox.compatibilities.CompatibilityManager;
import me.googas.starbox.modules.ModuleRegistry;
import me.googas.starbox.modules.language.LanguageModule;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.function.Supplier;

public class Starbox {

  @NonNull @Getter @Setter private static String context = "bukkit";
  @Nullable @Getter private static StarboxPlugin instance;

  @NonNull @Setter
  private static Supplier<LanguageModule> languageModuleSupplier =
      () -> Starbox.getModuleRegistry().require(LanguageModule.class);

  @NonNull
  public static String buildContext(@NonNull Player player) {
    return Starbox.buildContext(player.getWorld());
  }

  @NonNull
  public static String buildContext(@NonNull String subContext) {
    return Starbox.context + "=" + subContext;
  }

  @NonNull
  public static String buildContext(@NonNull World world) {
    return Starbox.context + "=" + world.getName();
  }

  public static void setInstance(@Nullable StarboxPlugin instance) {
    if (Starbox.instance != null && instance != null)
      throw new IllegalStateException("Plugin is already initialized");
    Starbox.instance = instance;
  }

  @NonNull
  public static StarboxFallback getFallback() {
    return Starbox.getPlugin().getFallback();
  }

  @NonNull
  public static StarboxPlugin getPlugin() {
    return Validate.notNull(Starbox.instance, "Plugin has not been initialized");
  }

  @NonNull
  public static ModuleRegistry getModuleRegistry() {
    return Starbox.getPlugin().getModuleRegistry();
  }

  public static @NonNull CompatibilityManager getDependencyManager() {
    return Starbox.getPlugin().getCompatibilityManager();
  }

  public static boolean isPAPIEnabled() {
    return Starbox.getPlugin().getCompatibilityManager().isEnabled("PlaceholderAPI");
  }

  public static boolean isVaultEnabled() {
    return Starbox.getPlugin().getCompatibilityManager().isEnabled("Vault");
  }

  @NonNull
  public static CompatibilityManager getCompatibilityManager() {
    return Starbox.getPlugin().getCompatibilityManager();
  }

  @NonNull
  public static StarboxScheduler getScheduler() {
    return Starbox.getPlugin().getScheduler();
  }

  @NonNull
  public static LanguageModule getLanguageModule() {
    return Starbox.languageModuleSupplier.get();
  }
}
