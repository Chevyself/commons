package me.googas.starbox;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;
import me.googas.commands.bukkit.AnnotatedCommand;
import me.googas.commands.bukkit.CommandManager;
import me.googas.commands.bukkit.CommandManagerOptions;
import me.googas.commands.bukkit.ParentCommand;
import me.googas.commands.bukkit.annotations.Command;
import me.googas.commands.bukkit.messages.MessagesProvider;
import me.googas.commands.bukkit.providers.registry.BukkitProvidersRegistry;
import me.googas.commands.bukkit.utils.BukkitUtils;
import me.googas.starbox.commands.StarboxCommands;
import me.googas.starbox.commands.debug.MatchMakingDebug;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.Plugin;

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
