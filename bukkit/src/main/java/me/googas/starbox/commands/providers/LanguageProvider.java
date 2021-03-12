package me.googas.starbox.commands.providers;

import com.starfishst.commands.bukkit.context.CommandContext;
import com.starfishst.commands.bukkit.providers.type.BukkitExtraArgumentProvider;
import lombok.NonNull;
import me.googas.starbox.Starbox;
import me.googas.starbox.modules.language.FallbackLanguage;
import me.googas.starbox.modules.language.Language;
import me.googas.starbox.modules.language.LanguageModule;

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
