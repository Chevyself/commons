package me.googas.commons.gameplay.module;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NonNull;
import me.googas.commons.gameplay.match.Match;
import me.googas.commons.modules.Module;
import me.googas.commons.modules.ModuleRegistry;
import org.bukkit.plugin.Plugin;

public class MatchModuleRegistry extends ModuleRegistry {

  @NonNull @Getter private final List<Module> required = new ArrayList<>();

  /**
   * Create the module registry
   *
   * @param plugin the plugin that registers the modules
   */
  public MatchModuleRegistry(@NonNull Plugin plugin) {
    super(plugin);
  }

  /**
   * Prepares the registry for the given match
   *
   * @param match the match using the registry
   * @return this same instance
   */
  @NonNull
  public MatchModuleRegistry prepare(@NonNull Match match) {
    for (Module module : new ArrayList<>(this.required)) {
      if (module instanceof MatchModule) ((MatchModule) module).setMatch(match);
    }
    return this;
  }

  /**
   * Engages the modules for the match to start
   *
   * @return this same instance
   */
  @NonNull
  public MatchModuleRegistry engage() {
    this.engage(new ArrayList<>(this.required));
    return this;
  }

  /**
   * Disengages the modules this might be because the match has been unloaded
   *
   * @return this same instance
   */
  @NonNull
  public MatchModuleRegistry disengage() {
    this.disengage(new ArrayList<>(this.getEngaged()));
    return this;
  }
}
