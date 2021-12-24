package me.googas.commons.commands.debug;

import java.util.Collection;
import lombok.NonNull;
import me.googas.commands.bukkit.annotations.Command;
import me.googas.commands.bukkit.result.Result;
import me.googas.commands.bukkit.utils.BukkitUtils;
import me.googas.commons.Starbox;
import me.googas.commons.gameplay.MiniGame;
import me.googas.commons.gameplay.entity.MatchEntity;
import me.googas.commons.gameplay.entity.player.MatchPlayer;
import me.googas.commons.gameplay.entity.player.MatchTeam;
import me.googas.commons.gameplay.match.Match;
import me.googas.commons.modules.gameplay.MatchMakingModule;
import me.googas.commons.modules.ui.Button;
import me.googas.commons.modules.ui.types.PaginatedInventory;
import me.googas.commons.utility.Materials;
import me.googas.commons.utility.items.ItemBuilder;
import me.googas.commons.utility.items.meta.ItemMetaBuilder;
import me.googas.commons.utility.items.meta.SkullMetaBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * This commands are used to provide some information about match making objects. This commands are
 * registered in the {@link me.googas.commons.commands.StarboxCommands} parent command.
 *
 * <ul>
 *   <li>entities: Makes an inventory with all the entities loaded in the match making module
 *   <li>matches: Makes an inventory with all the matches loaded in the match making module
 *   <li>minigames: Makes an inventory with all the mini-games registered in the match making module
 * </ul>
 *
 * The permission needed to use this commands is <b>starbox.debug</b>
 */
public class MatchMakingDebug {

  @Command(
      aliases = "entities",
      description = "Makes an inventory with all the entities loaded in the match making module",
      permission = "starbox.debug")
  public Result entities(Player player) {
    MatchMakingModule module = Starbox.getModuleRegistry().get(MatchMakingModule.class);
    if (module != null && !module.getEntities().isEmpty()) {
      player.openInventory(this.getInventory(module.getEntities()).getInventory());
      return new Result();
    }
    return new Result("&cThere's no entities");
  }

  @Command(
      aliases = "matches",
      description = "Makes an inventory with all the matches loaded in the match making module",
      permission = "starbox.debug")
  public Result matches(Player player) {
    MatchMakingModule module = Starbox.getModuleRegistry().get(MatchMakingModule.class);
    if (module != null && !module.getMatches().isEmpty()) {
      player.openInventory(this.getInventoryMatches(module.getMatches()).getInventory());
      return new Result();
    }
    return new Result("&cThere's no matches");
  }

  @Command(
      aliases = "minigames",
      description =
          "Makes an inventory with all the mini-games registered in the match making module",
      permission = "starbox.debug")
  public Result minigames(Player player) {
    MatchMakingModule module = Starbox.getModuleRegistry().get(MatchMakingModule.class);
    if (module != null && !module.getMiniGames().isEmpty()) {
      player.openInventory(this.getInventoryMiniGames(module.getMiniGames()).getInventory());
      return new Result();
    }
    return new Result("&cThere's no minigames");
  }

  /**
   * Get the inventory for matches. Creates the inventory with all the matches given in the
   * collection
   *
   * @param matches the matches to add in the inventory
   * @return the inventory
   */
  @NonNull
  private PaginatedInventory getInventoryMatches(@NonNull Collection<Match> matches) {
    PaginatedInventory inventory =
        new PaginatedInventory(BukkitUtils.build("&eMatch making matches %page%/%max%"));
    for (Match match : matches) {
      inventory.add(this.toButton(match));
    }
    return inventory;
  }

  /**
   * Get the inventory for mini-games. Creates the inventory with all the mini-games given in the
   * collection
   *
   * @param miniGames the mini-games to add in the inventory
   * @return the inventory
   */
  @NonNull
  private PaginatedInventory getInventoryMiniGames(@NonNull Collection<MiniGame> miniGames) {
    PaginatedInventory inventory =
        new PaginatedInventory(BukkitUtils.build("&eMatch making mini games %page%/%max%"));
    for (MiniGame miniGame : miniGames) {
      inventory.add(this.toButton(miniGame));
    }
    return inventory;
  }

  /**
   * Get the miniGame as a button. The item is just glass with the lore as the {@link
   * Object#toString()} in this case the object is the miniGame
   *
   * @param miniGame the miniGame to get the button of
   * @return the button of the miniGame
   */
  @NonNull
  private Button toButton(@NonNull MiniGame miniGame) {
    return new ItemBuilder(Material.GLASS)
        .setLore(miniGame.toString())
        .buildAll(event -> event.setCancelled(true));
  }

  /**
   * Get the match as a button. The item is just a golder carrot with the lore as the {@link
   * Object#toString()} in this case the object is the match
   *
   * @param match the match to get the button of
   * @return the button of the match
   */
  @NonNull
  private Button toButton(@NonNull Match match) {
    return new ItemBuilder(Material.GOLDEN_CARROT)
        .setLore(match.toString())
        .buildAll(event -> event.setCancelled(true));
  }

  /**
   * Get the inventory for match entities. Creates the inventory with all the match entities given
   * in the collection
   *
   * @param entities the match entities to add in the inventory
   * @return the inventory
   */
  @NonNull
  private PaginatedInventory getInventory(@NonNull Collection<MatchEntity> entities) {
    PaginatedInventory inventory =
        new PaginatedInventory(BukkitUtils.build("&eMatch making entities %page%/%max%"));
    for (MatchEntity entity : entities) {
      if (entity instanceof MatchPlayer) {
        inventory.add(this.toButton((MatchPlayer) entity));
      } else {
        inventory.add(this.toButton(entity));
      }
    }
    return inventory.addDefaultToolbar();
  }

  /**
   * Get the player as a button. The item is just the head of the player with the lore as the {@link
   * Object#toString()} in this case the object is the player
   *
   * @param player the player to get the button of
   * @return the button of the player
   */
  @NonNull
  private Button toButton(@NonNull MatchPlayer player) {
    ItemBuilder builder = new ItemBuilder(Materials.getSkull());
    ItemMetaBuilder metaBuilder = builder.getMetaBuilder();
    if (metaBuilder instanceof SkullMetaBuilder) {
      metaBuilder.setName(BukkitUtils.build("&6" + player.getName())).setLore(player.toString());
      ((SkullMetaBuilder) metaBuilder).setOwner(player.getOfflinePlayer());
    }
    return builder.build(event -> event.setCancelled(true));
  }

  /**
   * Get the entity as a button. The item is just a golden apple with the lore as the {@link
   * Object#toString()} in this case the object is the entity
   *
   * @param entity the entity to get the button of
   * @return the button of the entity
   */
  @NonNull
  private Button toButton(@NonNull MatchEntity entity) {
    return new ItemBuilder(Material.GOLDEN_APPLE)
        .setName(entity.getName())
        .setLore(entity.toString())
        .buildAll(
            (event) -> {
              event.setCancelled(true);
              if (entity instanceof MatchTeam) {
                event
                    .getWhoClicked()
                    .openInventory(
                        this.getInventory(((MatchTeam) entity).getMembers()).getInventory());
              }
            });
  }
}
