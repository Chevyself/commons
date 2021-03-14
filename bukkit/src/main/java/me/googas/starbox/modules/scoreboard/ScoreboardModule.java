package me.googas.starbox.modules.scoreboard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.googas.annotations.Nullable;
import me.googas.starbox.modules.Module;
import me.googas.starbox.modules.placeholders.Line;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.ScoreboardManager;

/** Handles scoreboards */
public class ScoreboardModule implements Module {

  /** The set of scoreboards */
  @NonNull final Set<StarScoreboard> scoreboards = new HashSet<>();

  @NonNull @Getter @Setter private Line defaultTitle = new Line("", 0);

  @NonNull @Getter @Setter private List<Line> layout = new ArrayList<>();

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onPlayerJoin(PlayerJoinEvent event) {
    this.scoreboards.add(
        this.getScoreboard(event.getPlayer()).initialize(this.getTitle(event.getPlayer())));
  }

  @Nullable
  public StarScoreboard getScoreboard(@NonNull OfflinePlayer player) {
    for (StarScoreboard scoreboard : this.scoreboards) {
      if (scoreboard.getPlayer().equals(player.getUniqueId())) return scoreboard;
    }
    return null;
  }

  @NonNull
  public StarScoreboard getScoreboard(@NonNull Player player) {
    StarScoreboard scoreboard = this.getScoreboard((OfflinePlayer) player);
    if (scoreboard != null) return scoreboard;
    ScoreboardManager manager = Bukkit.getScoreboardManager();
    if (manager == null) throw new IllegalStateException("Scoreboard could not be generated");
    return new StarScoreboard(player, manager.getNewScoreboard(), this.layout);
  }

  /**
   * Get the title for a player
   *
   * @param player the player to get the title
   * @return the title
   */
  @NonNull
  public String getTitle(@NonNull OfflinePlayer player) {
    return this.defaultTitle.build(player);
  }

  /**
   * Get the layout of the player
   *
   * @param player the player to get the layer
   * @return the layer for the player
   */
  @NonNull
  public List<Line> getLayout(@NonNull @SuppressWarnings("unused") OfflinePlayer player) {
    return this.layout;
  }

  @Override
  public @NonNull String getName() {
    return "scoreboard";
  }
}
