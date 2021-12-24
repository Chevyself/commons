package me.googas.commons.modules.data.type;

import lombok.NonNull;
import me.googas.annotations.Nullable;
import org.bukkit.World;

/** This is an object which may have decorations such as a prefix and suffix */
public interface Decorative {

  @Nullable
  String getPrefix(@Nullable String context);

  @Nullable
  String getSuffix(@Nullable String context);

  void setPrefix(@Nullable String context, @Nullable String prefix);

  void setSuffix(@Nullable String context, @Nullable String suffix);

  @Nullable
  default String getSuffix(@NonNull World world) {
    return this.getSuffix(world.getName());
  }

  default String getPrefix(@NonNull World world) {
    return this.getPrefix(world.getName());
  }
}
