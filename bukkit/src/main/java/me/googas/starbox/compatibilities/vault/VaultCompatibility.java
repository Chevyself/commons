package me.googas.starbox.compatibilities.vault;

import com.starfishst.commands.bukkit.context.CommandContext;
import com.starfishst.core.providers.type.IContextualProvider;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.googas.starbox.StarboxCommand;
import me.googas.starbox.compatibilities.Compatibility;
import me.googas.starbox.modules.Module;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Most plugins use or require Vault nowadays this compatibility allows developers to easily connect
 * their data to vault therefore to other plugins
 *
 * @see me.googas.starbox.modules.data.DataModule
 */
public class VaultCompatibility implements Compatibility {

  @Getter @Setter private boolean enabled;

  @Override
  public @NonNull Collection<Module> getModules(@NonNull Plugin plugin) {
    return new ArrayList<>();
  }

  @Override
  public Collection<IContextualProvider<?, CommandContext>> getProviders() {
    return new ArrayList<>();
  }

  @Override
  public @NonNull Collection<StarboxCommand> getCommands() {
    return new ArrayList<>();
  }

  @Override
  public @NonNull String getName() {
    return "Vault";
  }
}
