package me.googas.commons.wrappers.block;

import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.commons.reflect.APIVersion;
import org.bukkit.Material;

@APIVersion(14)
public class WrappedBlockDataMeta {

  @Nullable private WrappedBlockData data;

  public WrappedBlockDataMeta(@Nullable WrappedBlockData data) {
    this.data = data;
  }

  public WrappedBlockDataMeta() {
    this(null);
  }

  public boolean hasBlockData() {
    return this.data != null;
  }

  @NonNull
  public WrappedBlockData getBlockData(@NonNull Material material) {
    return this.data;
  }

  public void setBlockData(@NonNull WrappedBlockData data) {
    this.data = data;
  }
}
