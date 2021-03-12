package me.googas.starbox;

import com.starfishst.commands.bukkit.messages.DefaultMessagesProvider;
import lombok.Getter;
import lombok.NonNull;
import me.googas.commons.Lots;
import me.googas.starbox.compatibilities.CompatibilityManager;
import me.googas.starbox.compatibilities.papi.PAPICompatibility;
import me.googas.starbox.compatibilities.vault.VaultCompatibility;
import me.googas.starbox.modules.ModuleRegistry;
import me.googas.starbox.utility.Versions;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class StarboxPlugin extends JavaPlugin {

  @NonNull @Getter
  private final CompatibilityManager compatibilityManager =
      new CompatibilityManager(Lots.set(new PAPICompatibility(), new VaultCompatibility()));

  @NonNull @Getter private final ModuleRegistry moduleRegistry = new ModuleRegistry(this);
  @NonNull @Getter private final StarboxScheduler scheduler = new StarboxScheduler(this);

  @NonNull @Getter
  private final DefaultMessagesProvider messagesProvider = new DefaultMessagesProvider();

  @NonNull @Getter
  private final StarboxCommandManager manager =
      new StarboxCommandManager(this, this.messagesProvider);

  @NonNull @Getter private final StarboxFallback fallback = new StarboxFallback();

  @Override
  public void onEnable() {
    Starbox.setInstance(this);
    this.manager.register();
    this.compatibilityManager.check();
    this.getLogger().info("Loaded Bukkit using version 1." + Versions.BUKKIT);
    super.onEnable();
  }

  @Override
  public void onDisable() {
    this.moduleRegistry.disengage(new ArrayList<>(this.moduleRegistry.getEngaged()));
    this.manager.unregister();
    Starbox.setInstance(null);
    HandlerList.unregisterAll(this);
    super.onDisable();
  }
}
