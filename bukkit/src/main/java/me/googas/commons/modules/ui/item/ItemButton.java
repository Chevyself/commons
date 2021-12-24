package me.googas.commons.modules.ui.item;

import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

public interface ItemButton {

  @NonNull
  ItemButtonListener getListener();

  @NonNull
  ItemStack getItem();
}
