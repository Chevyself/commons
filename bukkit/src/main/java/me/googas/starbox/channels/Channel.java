package me.googas.starbox.channels;

import lombok.NonNull;
import me.googas.commons.Strings;
import me.googas.starbox.Starbox;
import me.googas.starbox.modules.language.LanguageModule;
import me.googas.starbox.modules.placeholders.Line;

import java.util.Map;

/**
 * A channel is an object in which different types of media can be sent such as messages, sounds,
 * particles, etc.
 */
public interface Channel {

  /**
   * Send a message to this channel with a map of placeholders. Placeholders are read as:
   * %placeholder% so in the message string all the characters inside `%` will be replaced with the
   * value of the key in the map. If the map does not contain the key it will not be replaced
   *
   * @param string the string to replace the placeholders and convert into a message
   * @param placeholders the place holders of the message
   */
  default void sendMessage(@NonNull String string, @NonNull Map<String, String> placeholders) {
    this.sendMessage(Strings.build(string, placeholders));
  }

  /**
   * Sends a simple string message to this channel
   *
   * @param string the string to send
   */
  void sendMessage(@NonNull String string);

  /**
   * Sends a line to this channel. The line will be built to the channel or it will be simply be
   * sent the raw line if it cannot be built
   *
   * @param line the line to send
   */
  void sendLine(@NonNull Line line);

  /**
   * Send a localized message. This will require a {@link LanguageModule} to be registered in the
   * {@link me.googas.starbox.modules.ModuleRegistry} of Starbox otherwise a {@link
   * NullPointerException} will be thrown
   *
   * @param path the path to the message
   * @throws NullPointerException is thrown if {@link LanguageModule} is not engaged
   */
  default void sendLocalized(@NonNull String path) {
    this.sendMessage(Starbox.getLanguageModule().getLanguage(this.getLocale()).get(path));
  }

  /**
   * Send a localized message with placeholders
   *
   * @see #sendMessage(String, Map) #sendlocalized(String, Map)
   * @param path the path to the message
   * @param placeholders the placeholders of the message
   * @throws NullPointerException is thrown if {@link LanguageModule} is not engaged
   */
  default void sendLocalized(@NonNull String path, @NonNull Map<String, String> placeholders) {
    this.sendMessage(
        Starbox.getLanguageModule().getLanguage(this.getLocale()).get(path, placeholders));
  }

  /**
   * Get the locale (language) for the channel
   *
   * @return the locale for the channel. This language must be readable by {@link
   *     LanguageModule#getLanguage(String)}
   */
  @NonNull
  String getLocale();
}
