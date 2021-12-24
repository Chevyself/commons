package me.googas.commons;

import java.util.ArrayList;
import lombok.Getter;
import lombok.NonNull;
import me.googas.commands.bukkit.CommandManager;
import me.googas.commands.bukkit.messages.BukkitMessagesProvider;
import me.googas.commands.bukkit.messages.MessagesProvider;
import me.googas.commands.bukkit.providers.registry.BukkitProvidersRegistry;
import me.googas.commons.compatibilities.CompatibilityManager;
import me.googas.commons.compatibilities.papi.PAPICompatibility;
import me.googas.commons.compatibilities.vault.VaultCompatibility;
import me.googas.commons.modules.ModuleRegistry;
import me.googas.commons.utility.Versions;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class StarboxPlugin extends JavaPlugin {

  @NonNull @Getter
  private final CompatibilityManager compatibilityManager =
      new CompatibilityManager(Lots.set(new PAPICompatibility(), new VaultCompatibility()));

  @NonNull @Getter private final ModuleRegistry moduleRegistry = new ModuleRegistry(this);
  @NonNull @Getter private final StarboxScheduler scheduler = new StarboxScheduler(this);

  @NonNull @Getter
  private final MessagesProvider messagesProvider = new BukkitMessagesProvider();

  @NonNull @Getter
  private final CommandManager manager =
      new CommandManager(this, new BukkitProvidersRegistry(this.messagesProvider), this.messagesProvider);

  @NonNull @Getter private final StarboxFallback fallback = new StarboxFallback();

  @Override
  public void onEnable() {
    Starbox.setInstance(this);
    this.compatibilityManager.check();
    this.getLogger().info("Loaded Bukkit using version 1." + Versions.BUKKIT);
    super.onEnable();
  }

  @Override
  public void onDisable() {
    this.moduleRegistry.disengage(new ArrayList<>(this.moduleRegistry.getEngaged()));
    this.manager.close();
    Starbox.setInstance(null);
    HandlerList.unregisterAll(this);
    super.onDisable();
  }
}
