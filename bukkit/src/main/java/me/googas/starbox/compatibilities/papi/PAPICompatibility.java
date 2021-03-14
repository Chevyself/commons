package me.googas.starbox.compatibilities.papi;

import java.util.ArrayList;
import java.util.Collection;
import lombok.NonNull;
import me.googas.commands.bukkit.context.CommandContext;
import me.googas.commands.providers.type.IContextualProvider;
import me.googas.starbox.StarboxCommand;
import me.googas.starbox.compatibilities.Compatibility;
import me.googas.starbox.modules.Module;
import org.bukkit.plugin.Plugin;

/**
 * This compatibility adds an easy way to register placeholders thru Starbox or use placeholders in
 * {@link me.googas.starbox.modules.placeholders.Line}
 */
public class PAPICompatibility implements Compatibility {

  private boolean enabled;

  @Override
  public @NonNull Collection<Module> getModules(@NonNull Plugin plugin) {
    return new ArrayList<>();
  }

  @Override
  public Collection<IContextualProvider<?, CommandContext>> getProviders() {
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
