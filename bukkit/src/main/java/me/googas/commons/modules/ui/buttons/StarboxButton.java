package me.googas.commons.modules.ui.buttons;

import java.util.Objects;
import lombok.Getter;
import lombok.NonNull;
import me.googas.commons.builder.ToStringBuilder;
import me.googas.commons.modules.ui.Button;
import me.googas.commons.modules.ui.ButtonListener;
import me.googas.commons.modules.ui.types.PaginatedInventory;
import me.googas.commons.utility.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class StarboxButton implements Button {

  @Getter @NonNull private final ButtonListener listener;
  @Getter @NonNull private final ItemStack item;

  public StarboxButton(@NonNull ButtonListener listener, @NonNull ItemStack item) {
    this.listener = listener;
    this.item = item;
  }

  @NonNull
  public static Button back() {
    return new ItemBuilder(Material.ARROW, 1)
        .setName("Back")
        .buildAll(
            event -> {
              InventoryHolder holder = event.getInventory().getHolder();
              if (!(holder instanceof PaginatedInventory)) return;
              ((PaginatedInventory) holder).previous();
            });
  }

  @NonNull
  public static Button next() {
    return new ItemBuilder(Material.ARROW, 1)
        .setName("Next")
        .buildAll(
            event -> {
              InventoryHolder holder = event.getInventory().getHolder();
              if (!(holder instanceof PaginatedInventory)) return;
              ((PaginatedInventory) holder).next();
            });
  }

  @NonNull
  public static Button empty() {
    return new ItemBuilder(Material.AIR).build((event) -> event.setCancelled(true));
  }

  @NonNull
  public static Button empty(@NonNull ItemStack item) {
    return new StarboxButton(event -> event.setCancelled(true), item);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || this.getClass() != o.getClass()) return false;
    StarboxButton button = (StarboxButton) o;
    return this.listener.equals(button.listener) && this.item.isSimilar(button.item);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("listener", this.listener)
        .append("item", this.item)
        .build();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.listener, this.item);
  }
}
