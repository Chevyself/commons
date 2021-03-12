package me.googas.starbox;

import com.starfishst.commands.bukkit.AnnotatedCommand;
import com.starfishst.commands.bukkit.CommandManager;
import com.starfishst.commands.bukkit.CommandManagerOptions;
import com.starfishst.commands.bukkit.ParentCommand;
import com.starfishst.commands.bukkit.annotations.Command;
import com.starfishst.commands.bukkit.messages.MessagesProvider;
import com.starfishst.commands.bukkit.providers.registry.BukkitProvidersRegistry;
import com.starfishst.commands.bukkit.utils.BukkitUtils;
import lombok.NonNull;
import me.googas.commons.Lots;
import me.googas.starbox.commands.StarboxCommands;
import me.googas.starbox.commands.debug.MatchMakingDebug;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class StarboxCommandManager extends CommandManager {

  @NonNull private final List<Object> toRegister = Lots.list(new MatchMakingDebug());

  public StarboxCommandManager(@NonNull Plugin plugin, @NonNull MessagesProvider messagesProvider) {
    super(
        plugin,
        new CommandManagerOptions(false),
        messagesProvider,
        new BukkitProvidersRegistry(messagesProvider));
  }

  public void register() {
    this.registerCommand(new StarboxCommands());
    ParentCommand command = this.getStarboxCommand();
    for (Object object : this.toRegister) {
      for (Method method : object.getClass().getDeclaredMethods()) {
        if (method.isAnnotationPresent(Command.class))
          command.addCommand(this.parseCommand(object, method, false));
      }
    }
  }

  @NonNull
  public ParentCommand getStarboxCommand() {
    for (AnnotatedCommand command : this.getCommands()) {
      if (command.getName().equalsIgnoreCase("me/googas/starbox")
          && command instanceof ParentCommand) return (ParentCommand) command;
    }
    throw new IllegalStateException("Starbox command has not been registered");
  }

  public void unregister() {
    try {
      CommandMap commandMap = BukkitUtils.getCommandMap();
      for (AnnotatedCommand command : new ArrayList<>(this.getCommands())) {
        command.unregister(commandMap);
      }
    } catch (NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }
}
