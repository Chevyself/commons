package me.googas.commons.utility.items.meta;

import lombok.Getter;
import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.commons.utility.items.ItemBuilder;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class BlockStateMetaBuilder extends ItemMetaBuilder {

  @Nullable @Getter private BlockState state;

  public BlockStateMetaBuilder(@NonNull ItemBuilder itemBuilder) {
    super(itemBuilder);
  }

  @NonNull
  public BlockStateMetaBuilder setState(@Nullable BlockState state) {
    this.state = state;
    return this;
  }

  @Override
  public BlockStateMeta build(@NonNull ItemStack stack) {
    ItemMeta itemMeta = super.build(stack);
    if (!(itemMeta instanceof BlockStateMeta)) return null;
    BlockStateMeta meta = (BlockStateMeta) itemMeta;
    if (this.state != null) meta.setBlockState(this.state);
    return meta;
  }
}
