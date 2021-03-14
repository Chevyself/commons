package me.googas.starbox.commands.providers;

import lombok.NonNull;
import me.googas.commands.bukkit.context.CommandContext;
import me.googas.commands.bukkit.providers.type.BukkitExtraArgumentProvider;
import me.googas.starbox.Starbox;
import me.googas.starbox.modules.language.LanguageModule;

/**
 * Provides commands with the {@link LanguageModule} as an extra argument. Can be used to send
 * localized messages to other players
 */
public class LanguageModuleProvider implements BukkitExtraArgumentProvider<LanguageModule> {
  @Override
  public @NonNull Class<LanguageModule> getClazz() {
    return LanguageModule.class;
  }

  @Override
  public @NonNull LanguageModule getObject(@NonNull CommandContext context) {
    return Starbox.getLanguageModule();
  }
}
