package me.googas.starbox.compatibilities;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;

import java.util.Set;

/**
 * This object is required in order to check whether the compatibilities are loaded or can be
 * loaded.
 */
public class CompatibilityManager {

  @NonNull @Getter private final Set<Compatibility> compatibilities;

  /**
   * Create the dependencies manager
   *
   * @param compatibilities the compatibilities available to check
   */
  public CompatibilityManager(@NonNull Set<Compatibility> compatibilities) {
    this.compatibilities = compatibilities;
  }

  /**
   * This method will check whether the compatibilities are loaded getting the plugin manager. This
   * is where the name of the compatibility is required.
   *
   * @see Compatibility#getName()
   */
  public void check() {
    for (Compatibility compatibility : this.compatibilities) {
      if (Bukkit.getServer().getPluginManager().getPlugin(compatibility.getName()) != null) {
        compatibility.setEnabled(true);
        compatibility.onEnable();
      }
    }
  }

  /**
   * Check whether a compatibility is enabled
   *
   * @param name the name of the compatibility
   * @return true if the compatibility is loaded
   */
  public boolean isEnabled(@NonNull String name) {
    return this.getCompatibility(name).isEnabled();
  }

  /**
   * Get a compatibility by its name
   *
   * @param name the name of the compatibility
   * @return the compatibility
   * @throws IllegalArgumentException if the name does not match a compatibility
   */
  @NonNull
  public Compatibility getCompatibility(@NonNull String name) {
    for (Compatibility compatibility : this.compatibilities) {
      if (compatibility.getName().equalsIgnoreCase(name)) {
        return compatibility;
      }
    }
    throw new IllegalArgumentException(name + " is not a dependency");
  }
}
