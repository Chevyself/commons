package me.googas.starbox;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import lombok.Getter;
import lombok.NonNull;
import me.googas.commands.bukkit.utils.BukkitUtils;
import me.googas.starbox.fallback.Fallback;
import me.googas.starbox.modules.ui.Button;
import me.googas.starbox.modules.ui.types.PaginatedInventory;
import me.googas.starbox.utility.items.ItemBuilder;
import org.bukkit.Material;

public class StarboxFallback implements Fallback {

  @NonNull @Getter private final List<String> errors = new ArrayList<>();

  @NonNull @Getter
  private final PaginatedInventory inventory =
      new PaginatedInventory(BukkitUtils.build("&cErrors %page%/%max%")).addDefaultToolbar();

  @NonNull
  private Button toButton(@NonNull String message) {
    return new ItemBuilder(Material.BARRIER)
        .setName(BukkitUtils.build(message))
        .buildAll(
            event -> {
              this.errors.remove(message);
              this.inventory.remove(this.inventory.getButton(event.getRawSlot()), true);
            });
  }

  @Override
  public void process(Throwable throwable) {
    this.process(throwable, throwable.getMessage() == null ? "" : throwable.getMessage());
  }

  @Override
  public void process(Throwable throwable, String message) {
    Starbox.getPlugin().getLogger().log(Level.SEVERE, throwable, () -> message);
    this.errors.add(message);
    this.inventory.add(this.toButton(message));
  }
}
