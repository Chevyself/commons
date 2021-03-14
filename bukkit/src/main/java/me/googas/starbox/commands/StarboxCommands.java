package me.googas.starbox.commands;

import me.googas.commands.annotations.Parent;
import me.googas.commands.bukkit.annotations.Command;
import me.googas.commands.bukkit.result.Result;
import me.googas.starbox.Starbox;

/** General Starbox parent command */
public class StarboxCommands {

  @Parent
  @Command(
      aliases = "starbox",
      description = "Parent command for Starbox",
      permission = "starbox.parent")
  public Result starbox() {
    return new Result(
        "Using &bStarbox&r version: &l&b" + Starbox.getPlugin().getDescription().getVersion());
  }
}
