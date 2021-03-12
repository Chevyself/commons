package me.googas.starbox.events.player;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.entity.Player;

/** This event is called when the name of a player is being changed */
public class PlayerUpdateDisplayNameEvent extends StarboxPlayerEvent {

  @NonNull @Getter @Setter private String name;
  @NonNull @Getter @Setter private String tabName;

  public PlayerUpdateDisplayNameEvent(
      @NonNull Player player, @NonNull String name, @NonNull String tabName) {
    super(player);
    this.name = name;
    this.tabName = tabName;
  }
}
