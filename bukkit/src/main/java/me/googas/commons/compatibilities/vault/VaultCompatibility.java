package me.googas.commons.compatibilities.vault;

import java.util.ArrayList;
import java.util.Collection;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.googas.commands.bukkit.context.CommandContext;
import me.googas.commands.providers.type.StarboxContextualProvider;
import me.googas.commons.StarboxCommand;
import me.googas.commons.compatibilities.Compatibility;
import me.googas.commons.modules.Module;
import org.bukkit.plugin.Plugin;

/**
 * Most plugins use or require Vault nowadays this compatibility allows developers to easily connect
 * their data to vault therefore to other plugins
 *
 * @see me.googas.commons.modules.data.DataModule
 */
public class VaultCompatibility implements Compatibility {

  @Getter @Setter private boolean enabled;

  @Override
  public @NonNull Collection<Module> getModules(@NonNull Plugin plugin) {
    return new ArrayList<>();
  }

  @Override
  public Collection<StarboxContextualProvider<?, CommandContext>> getProviders() {
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
