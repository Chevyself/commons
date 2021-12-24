package me.googas.commons.modules.language;

import lombok.NonNull;
import me.googas.commands.arguments.Argument;
import me.googas.commands.arguments.SingleArgument;
import me.googas.commands.bukkit.AnnotatedCommand;
import me.googas.commands.bukkit.StarboxBukkitCommand;
import me.googas.commands.bukkit.context.CommandContext;
import me.googas.commands.bukkit.messages.MessagesProvider;
import me.googas.commons.Lots;
import me.googas.commons.maps.MapBuilder;
import me.googas.commons.maps.Maps;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import sun.text.resources.th.BreakIteratorInfo_th;

import java.util.HashMap;

public class LanguageModuleMessagesProvider implements MessagesProvider {

  @NonNull private final LanguageModule module;

  public LanguageModuleMessagesProvider(@NonNull LanguageModule module) {
    this.module = module;
  }

  @NonNull
  public Language getLanguage(CommandContext context) {
    if (context != null) {
      CommandSender sender = context.getSender();
      if (sender instanceof Player) {
        return this.module.getLanguage((Player) sender);
      }
    }
    return this.module.getDefault();
  }

  /**
   * Get the placeholders for a command
   *
   * @param cmd the command to get the placeholders from
   * @return the placeholders as a builder
   */
  @NonNull
  public MapBuilder<String, String> getCommandPlaceholders(@NonNull StarboxBukkitCommand cmd) {
    return Maps.builder("name", cmd.getName())
        .append("aliases", Lots.pretty(cmd.getAliases()))
        .append("description", cmd.getDescription())
        .append("permission", cmd.getDescription());
  }

  /**
   * Get the placeholders for an argument
   *
   * @param argument the argument to get the placeholders to
   * @return the placeholders
   */
  @NonNull
  private MapBuilder<String, String> getArgumentPlaceholders(@NonNull Argument<?> argument) {
    MapBuilder<String, String> builder = new MapBuilder<>(new HashMap<>());
    if (argument instanceof SingleArgument) {
      SingleArgument<?> singleArgument = (SingleArgument<?>) argument;
      builder.append("name", singleArgument.getName())
              .append("description", singleArgument.getDescription())
              .append("position", String.valueOf(singleArgument.getPosition()));
    }
    return builder;
  }

  @NonNull
  public Language getLanguage() {
    return this.getLanguage(null);
  }

  @Override
  public @NonNull String invalidLong(@NonNull String s, @NonNull CommandContext context) {
    return this.getLanguage(context).get("invalid.long", Maps.singleton("string", s));
  }

  @Override
  public @NonNull String invalidInteger(@NonNull String s, @NonNull CommandContext context) {
    return this.getLanguage(context).get("invalid.integer", Maps.singleton("string", s));
  }

  @Override
  public @NonNull String invalidDouble(@NonNull String s, @NonNull CommandContext context) {
    return this.getLanguage(context).get("invalid.double", Maps.singleton("string", s));
  }

  @Override
  public @NonNull String invalidBoolean(@NonNull String s, @NonNull CommandContext context) {
    return this.getLanguage(context).get("invalid.boolean", Maps.singleton("string", s));
  }

  @Override
  public @NonNull String invalidTime(@NonNull String s, @NonNull CommandContext context) {
    return this.getLanguage(context).get("invalid.time", Maps.singleton("string", s));
  }

  @Override
  public @NonNull String missingArgument(
      @NonNull String s, @NonNull String s1, int i, CommandContext context) {
    Language language = this.getLanguage(context);
    return language.get(
        "missing.argument",
        Maps.builder("name", language.get(s))
            .append("description", language.get(s1))
            .append("position", String.valueOf(i)));
  }

  @Override
  public @NonNull String missingStrings(
      @NonNull String s,
      @NonNull String s1,
      int i,
      int i1,
      int i2,
      @NonNull CommandContext context) {
    Language language = this.getLanguage(context);
    return language.get(
        "missing.argument",
        Maps.builder("name", language.get(s))
            .append("description", language.get(s1))
            .append("position", String.valueOf(i))
            .append("min", String.valueOf(i1))
            .append("missing", String.valueOf(i2)));
  }

  @Override
  public @NonNull String invalidPlayer(@NonNull String s, @NonNull CommandContext context) {
    return this.getLanguage(context).get("invalid.player", Maps.singleton("string", s));
  }

  @Override
  public @NonNull String playersOnly(@NonNull CommandContext context) {
    return this.getLanguage(context).get("invalid.only-players");
  }

  @Override
  public @NonNull String notAllowed(@NonNull CommandContext commandContext) {
    return this.getLanguage(commandContext).get("not-allowed");
  }

  @Override
  public @NonNull String helpTopicShort(@NonNull Plugin plugin) {
    return this.module
        .getDefault()
        .get(
            "help-topic.plugin.short",
            Maps.builder("name", plugin.getName())
                .append("description", plugin.getDescription().getDescription())
                .append("version", plugin.getDescription().getVersion()));
  }

  @Override
  public @NonNull String helpTopicFull(
      @NonNull String s, @NonNull String s1, @NonNull Plugin plugin) {
    return this.module
        .getDefault()
        .get(
            "help-topic.plugin.full",
            Maps.builder("name", plugin.getName())
                .append("description", plugin.getDescription().getDescription())
                .append("version", plugin.getDescription().getVersion())
                .append("short", s)
                .append("commands", s1));
  }

  @Override
  public @NonNull String helpTopicCommand(@NonNull StarboxBukkitCommand command) {
    return this.getLanguage().get("help.command.topic", this.getCommandPlaceholders(command));
  }

  @Override
  public @NonNull String commandShortText(@NonNull StarboxBukkitCommand starboxBukkitCommand) {
    return this.getLanguage().get("help.command.short", this.getCommandPlaceholders(starboxBukkitCommand));
  }

  @Override
  public @NonNull String commandName(StarboxBukkitCommand starboxBukkitCommand, String name) {
    return name;
  }

  @Override
  public @NonNull String commandFullText(@NonNull StarboxBukkitCommand command, @NonNull String name) {
    return this.getLanguage().get("help.command.full", this.getCommandPlaceholders(command));
  }

  @Override
  public @NonNull String childCommand(@NonNull StarboxBukkitCommand parent, @NonNull StarboxBukkitCommand child) {
    // TODO add parent
    MapBuilder<String, String> builder = this.getCommandPlaceholders(parent);
    builder.append("child-name", child.getName()).append("child-description", child.getDescription());
    return this.getLanguage().get("help.command.child", builder);
  }

  @Override
  public @NonNull String invalidMaterialEmpty(@NonNull CommandContext commandContext) {
    return this.getLanguage(commandContext).get("invalid.material-empty");
  }

  @Override
  public @NonNull String invalidMaterial(
      @NonNull String s, @NonNull CommandContext commandContext) {
    return this.getLanguage(commandContext).get("invalid.material", Maps.singleton("string", s));
  }
}
