package me.googas.commons;

import lombok.NonNull;
import me.googas.commands.bukkit.messages.MessagesProvider;
import me.googas.commands.bukkit.providers.registry.BukkitProvidersRegistry;
import me.googas.commons.commands.providers.LanguageModuleProvider;
import me.googas.commons.commands.providers.LanguageProvider;
import me.googas.commons.commands.providers.ProfileProvider;

public class StarboxProvidersRegistry extends BukkitProvidersRegistry {

  public StarboxProvidersRegistry(@NonNull MessagesProvider messages) {
    super(messages);
    this.addProvider(new LanguageModuleProvider());
    this.addProvider(new LanguageProvider());
    this.addProvider(new ProfileProvider());
  }
}
