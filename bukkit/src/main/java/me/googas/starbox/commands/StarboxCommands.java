package me.googas.starbox.commands;

import com.starfishst.commands.bukkit.annotations.Command;
import com.starfishst.commands.bukkit.result.Result;
import com.starfishst.core.annotations.Parent;
import me.googas.starbox.Starbox;

/** General Starbox parent command */
public class StarboxCommands {

  @Parent
  @Command(
      aliases = "me/googas/starbox",
      description = "Parent command for Starbox",
      permission = "starbox.parent")
  public Result starbox() {
    return new Result(
        "Using &bStarbox&r version: &l&b" + Starbox.getPlugin().getDescription().getVersion());
  }
}
