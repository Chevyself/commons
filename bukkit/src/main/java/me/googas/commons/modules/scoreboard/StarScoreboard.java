package me.googas.commons.modules.scoreboard;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.googas.annotations.Nullable;
import me.googas.commands.bukkit.utils.BukkitUtils;
import me.googas.commons.Strings;
import me.googas.commons.Validate;
import me.googas.commons.maps.Maps;
import me.googas.commons.modules.placeholders.Line;
import me.googas.commons.reflect.APIVersion;
import me.googas.commons.reflect.wrappers.WrappedClass;
import me.googas.commons.reflect.wrappers.WrappedReturnMethod;
import me.googas.commons.utility.Versions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

/** The custom scoreboard for a player */
public class StarScoreboard {

  @NonNull private static final WrappedClass SCOREBOARD = new WrappedClass(Scoreboard.class);

  @NonNull
  private static final WrappedReturnMethod<Objective> REGISTER_NEW_OBJECTIVE =
      StarScoreboard.SCOREBOARD.getMethod(
          Objective.class, "registerNewObjective", String.class, String.class, String.class);

  @NonNull
  @APIVersion(value = 8, max = 13)
  private static final WrappedReturnMethod<Objective> REGISTER_NEW_OBJECTIVE_NO_DISPLAY =
      StarScoreboard.SCOREBOARD.getMethod(
          Objective.class, "registerNewObjective", String.class, String.class);

  @NonNull
  private static final Map<Integer, String> characters =
      Maps.builder(10, "a")
          .append(11, "b")
          .append(12, "c")
          .append(13, "d")
          .append(14, "e")
          .append(15, "f")
          .build();
  /** The owner of the custom scoreboard */
  @NonNull @Getter private final UUID player;

  /** The scoreboard */
  @NonNull @Getter private final Scoreboard scoreboard;

  /** The objective of the scoreboard */
  @NonNull @Getter private final Objective objective;

  /** The layout of the scoreboard */
  @NonNull @Setter private List<Line> layout;

  public StarScoreboard(
      @NonNull UUID player,
      @NonNull Scoreboard scoreboard,
      @NonNull Objective objective,
      @NonNull List<Line> layout) {
    this.player = player;
    this.scoreboard = scoreboard;
    this.objective = objective;
    this.layout = layout;
  }

  public StarScoreboard(
      @NonNull Player player, @NonNull Scoreboard scoreboard, @NonNull List<Line> layout) {
    this(
        player.getUniqueId(),
        scoreboard,
        StarScoreboard.createObjective(scoreboard, player.getName(), "dummy", null),
        layout);
  }

  /**
   * This is just for creating the scoreboard nothing special. Empty string
   *
   * @param position the amount of spaces that the string should have
   * @return an empty string with spaces
   */
  @NonNull
  public static String getEntryName(int position) {
    if (position < 9) {
      return BukkitUtils.build("&" + position + "&r");
    } else {
      return BukkitUtils.build("&" + StarScoreboard.characters.get(position) + "&r");
    }
  }

  @NonNull
  private static Objective createObjective(
      @NonNull Scoreboard scoreboard,
      @NonNull String name,
      @NonNull String criteria,
      @Nullable String display) {
    Objective objective;

    if (Versions.BUKKIT <= 13) {
      objective =
          StarScoreboard.REGISTER_NEW_OBJECTIVE_NO_DISPLAY.invoke(scoreboard, name, criteria);
    } else {
      objective =
          StarScoreboard.REGISTER_NEW_OBJECTIVE.invoke(
              scoreboard, name, criteria, display == null ? name : display);
    }
    return Validate.notNull(objective, "Objective could not be created");
  }

  @NonNull
  public StarScoreboard initialize(@NonNull String title) {
    this.objective.setDisplayName(title);
    this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    Player player = this.bukkitPlayer();
    if (player != null) {
      player.setScoreboard(this.scoreboard);
    }
    this.setNewLayout(this.layout);
    return this;
  }

  /**
   * Adds a new line to the scoreboard
   *
   * @param line the line to be added
   * @return the created minecraft team
   */
  @NonNull
  private Team newLine(@NonNull Line line) {
    Team team = this.getLineTeam(line.getPosition());
    String entryName = StarScoreboard.getEntryName(line.getPosition());
    if (team == null) {
      team = this.scoreboard.registerNewTeam("line_" + line.getPosition());
      team.addEntry(entryName);
    }
    String current = team.getPrefix() + team.getDisplayName() + team.getSuffix();
    String build = line.build(this.bukkitOfflinePlayer());
    if (current.equalsIgnoreCase(build)) return team;
    List<String> divide = Strings.divide(build, 16);
    String lastColor = "";
    for (int i = 0; i < divide.size(); i++) {
      String string = divide.get(i);
      switch (i) {
        case 0:
          team.setPrefix(lastColor + string);
          break;
        case 1:
          team.setSuffix(lastColor + string);
          break;
      }
      lastColor = ChatColor.getLastColors(string);
    }
    this.objective.getScore(entryName).setScore(line.getPosition());
    return team;
  }

  /**
   * Gets the line in a position
   *
   * @param position the position to get the line
   * @return a minecraft team representing a line if it exist in the position
   */
  @Nullable
  private Team getLineTeam(int position) {
    return this.scoreboard.getTeam("line_" + position);
  }

  @Nullable
  private Line getLine(int position) {
    for (Line line : this.layout) {
      if (line.getPosition() == position) return line;
    }
    return null;
  }

  /** Updates the scoreboard */
  public void update() {
    this.layout.forEach(this::newLine);
  }

  public void update(int position) {
    Line line = this.getLine(position);
    if (line != null) this.newLine(line);
  }

  @Nullable
  private Player bukkitPlayer() {
    return Bukkit.getPlayer(this.player);
  }

  @NonNull
  private OfflinePlayer bukkitOfflinePlayer() {
    return Bukkit.getOfflinePlayer(this.player);
  }

  /** Destroys this scoreboard */
  public void destroy() {
    this.scoreboard.getTeams().forEach(Team::unregister);
  }

  /**
   * Sets the new layout of the scoreboard
   *
   * @param layout the new layout
   */
  private void setNewLayout(@NonNull List<Line> layout) {
    this.layout = layout;
    Set<Team> editedTeams = new HashSet<>();
    layout.forEach(line -> editedTeams.add(this.newLine(line)));
    this.scoreboard
        .getTeams()
        .forEach(
            team -> {
              if (!editedTeams.contains(team)) {
                team.unregister();
              }
            });
    this.update();
  }

  private void setTitle(@NonNull String title) {
    this.objective.setDisplayName(title);
  }
}
