package me.googas.starbox.modules.language;

import lombok.NonNull;
import me.googas.commands.arguments.Argument;
import me.googas.commands.bukkit.AnnotatedCommand;
import me.googas.commands.bukkit.ParentCommand;
import me.googas.commands.bukkit.context.CommandContext;
import me.googas.commands.bukkit.messages.MessagesProvider;
import me.googas.starbox.Lots;
import me.googas.starbox.maps.MapBuilder;
import me.googas.starbox.maps.Maps;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class LanguageModuleMessagesProvider implements MessagesProvider {

  @NonNull private final LanguageModule module;

  public LanguageModuleMessagesProvider(@NonNull LanguageModule module) {
    this.module = module;
  }

  @NonNull
  public Language getLanguage(@NonNull CommandContext context) {
    CommandSender sender = context.getSender();
    if (sender instanceof Player) {
      return this.module.getLanguage((Player) sender);
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
  public MapBuilder<String, String> getCommandPlaceholders(@NonNull AnnotatedCommand cmd) {
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
    return Maps.builder("name", argument.getName())
        .append("description", argument.getDescription())
        .append("position", String.valueOf(argument.getPosition()));
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
  public @NonNull String helpTopicCommand(@NonNull AnnotatedCommand cmd) {
    return this.module
        .getDefault()
        .get("help-topic.plugin.command", this.getCommandPlaceholders(cmd));
  }

  @Override
  public @NonNull String commandShortText(@NonNull AnnotatedCommand cmd) {
    return this.module
        .getDefault()
        .get("help-topic.command.short", this.getCommandPlaceholders(cmd));
  }

  @Override
  public @NonNull String commandName(AnnotatedCommand cmd) {
    return this.module
        .getDefault()
        .get("help-topic.command.name", this.getCommandPlaceholders(cmd));
  }

  @Override
  public @NonNull String parentCommandFull(
      @NonNull ParentCommand parentCommand,
      @NonNull String s,
      @NonNull String s1,
      @NonNull String s2) {
    return this.module
        .getDefault()
        .get(
            "help-topic.parent.full",
            this.getCommandPlaceholders(parentCommand)
                .append("short", s)
                .append("children", s1)
                .append("arguments", s2));
  }

  @Override
  public @NonNull String parentCommandShort(
      @NonNull ParentCommand parentCommand, @NonNull String s) {
    return this.module
        .getDefault()
        .get("help-topic.parent.short", this.getCommandPlaceholders(parentCommand));
  }

  @Override
  public @NonNull String commandFull(
      @NonNull AnnotatedCommand annotatedCommand, @NonNull String s, @NonNull String s1) {
    return this.module
        .getDefault()
        .get(
            "help-topic.command.full",
            this.getCommandPlaceholders(annotatedCommand).append("short", s).append("usage", s1));
  }

  @Override
  public @NonNull String childCommandName(
      @NonNull AnnotatedCommand cmd, @NonNull ParentCommand parentCommand) {
    return this.module
        .getDefault()
        .get("help-topic.parent.child.name", this.getCommandPlaceholders(cmd));
  }

  @Override
  public @NonNull String childCommandShort(
      @NonNull AnnotatedCommand annotatedCommand, @NonNull ParentCommand parentCommand) {
    return this.module
        .getDefault()
        .get("help-topic.parent.child.short", this.getCommandPlaceholders(annotatedCommand));
  }

  @Override
  public @NonNull String childCommandFull(
      @NonNull AnnotatedCommand annotatedCommand,
      @NonNull ParentCommand parentCommand,
      @NonNull String s,
      @NonNull String s1) {
    return this.module
        .getDefault()
        .get("help-topic.parent.child.full", this.getCommandPlaceholders(annotatedCommand));
  }

  @Override
  public @NonNull String requiredArgumentHelp(@NonNull Argument<?> argument) {
    return this.module
        .getDefault()
        .get("help-topic.arguments.required", this.getArgumentPlaceholders(argument));
  }

  @Override
  public @NonNull String optionalArgumentHelp(@NonNull Argument<?> argument) {
    return this.module
        .getDefault()
        .get("help-topic.arguments.optional", this.getArgumentPlaceholders(argument));
  }

  @Override
  public @NonNull String childCommand(
      @NonNull AnnotatedCommand annotatedCommand, @NonNull ParentCommand parentCommand) {
    return this.module
        .getDefault()
        .get(
            "help-topic.plugin.child",
            this.getCommandPlaceholders(annotatedCommand)
                .append("parent-name", parentCommand.getName()));
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
