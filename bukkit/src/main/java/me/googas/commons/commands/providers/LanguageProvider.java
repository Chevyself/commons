package me.googas.commons.commands.providers;

import lombok.NonNull;
import me.googas.commands.bukkit.context.CommandContext;
import me.googas.commands.bukkit.providers.type.BukkitExtraArgumentProvider;
import me.googas.commons.Starbox;
import me.googas.commons.modules.language.FallbackLanguage;
import me.googas.commons.modules.language.Language;
import me.googas.commons.modules.language.LanguageModule;

/** Provides the language of the command sender, may be used to send a localized message to it */
public class LanguageProvider implements BukkitExtraArgumentProvider<Language> {
  @Override
  public @NonNull Class<Language> getClazz() {
    return Language.class;
  }

  @Override
  public @NonNull Language getObject(@NonNull CommandContext context) {
    LanguageModule module = Starbox.getModuleRegistry().get(LanguageModule.class);
    if (module == null) return FallbackLanguage.INSTANCE;
    return module.getLanguage(context);
  }
}
