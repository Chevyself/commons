package me.googas.commons.events.player;

import lombok.Getter;
import lombok.NonNull;
import me.googas.commons.events.StarboxEvent;
import org.bukkit.entity.Player;

public class StarboxPlayerEvent extends StarboxEvent {

  @NonNull @Getter private final Player player;

  public StarboxPlayerEvent(@NonNull Player player) {
    this.player = player;
  }
}
