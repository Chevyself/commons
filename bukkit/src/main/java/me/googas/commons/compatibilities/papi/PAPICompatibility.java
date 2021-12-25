package me.googas.commons.compatibilities.papi;

import java.util.ArrayList;
import java.util.Collection;
import lombok.NonNull;
import me.googas.commands.bukkit.context.CommandContext;
import me.googas.commands.providers.type.StarboxContextualProvider;
import me.googas.commons.StarboxCommand;
import me.googas.commons.compatibilities.Compatibility;
import me.googas.commons.modules.Module;
import org.bukkit.plugin.Plugin;

/**
 * This compatibility adds an easy way to register placeholders thru Starbox or use placeholders in
 * {@link me.googas.commons.modules.placeholders.Line}
 */
public class PAPICompatibility implements Compatibility {

  private boolean enabled;

  @Override
  public @NonNull Collection<Module> getModules(@NonNull Plugin plugin) {
    return new ArrayList<>();
  }

  @Override
  public Collection<StarboxContextualProvider<?, CommandContext>> getProviders() {
    return new ArrayList<>();
  }

  @Override
  public void setEnabled(boolean bol) {
    this.enabled = bol;
  }

  @Override
  public @NonNull Collection<StarboxCommand> getCommands() {
    return new ArrayList<>();
  }

  @Override
  public boolean isEnabled() {
    return this.enabled;
  }

  @Override
  public @NonNull String getName() {
    return "PlaceholderAPI";
  }
}
