package me.googas.starbox;

import com.starfishst.commands.bukkit.messages.MessagesProvider;
import com.starfishst.commands.bukkit.providers.registry.BukkitProvidersRegistry;
import lombok.NonNull;
import me.googas.starbox.commands.providers.LanguageModuleProvider;
import me.googas.starbox.commands.providers.LanguageProvider;
import me.googas.starbox.commands.providers.ProfileProvider;

public class StarboxProvidersRegistry extends BukkitProvidersRegistry {

  public StarboxProvidersRegistry(@NonNull MessagesProvider messages) {
    super(messages);
    this.addProvider(new LanguageModuleProvider());
    this.addProvider(new LanguageProvider());
    this.addProvider(new ProfileProvider());
  }
}
