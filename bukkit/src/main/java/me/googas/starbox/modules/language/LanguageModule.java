package me.googas.starbox.modules.language;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Delegate;
import me.googas.commands.bukkit.context.CommandContext;
import me.googas.starbox.Lots;
import me.googas.starbox.modules.Module;
import me.googas.starbox.utility.Players;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LanguageModule implements Module {

  @NonNull @Getter @Delegate private final List<Language> languages = new ArrayList<>();

  @NonNull
  public LanguageBundle getLanguage(@NonNull OfflinePlayer offlinePlayer) {
    Player player = offlinePlayer.getPlayer();
    if (player != null) return this.getLanguage(this.getLocale(player));
    return new LanguageBundle("default", Lots.list(this.getDefault()));
  }

  @NonNull
  public LanguageBundle getLanguage(@NonNull String locale) {
    List<Language> languages = new ArrayList<>();
    for (Language language : this.languages) {
      if (language.getLocale().equalsIgnoreCase(locale)) languages.add(language);
    }
    if (languages.isEmpty()) languages.add(this.getDefault());
    return new LanguageBundle(locale, languages);
  }

  @NonNull
  public LanguageBundle getLanguage(@NonNull CommandContext context) {
    return this.getLanguage(context.getSender());
  }

  @NonNull
  private LanguageBundle getLanguage(@NonNull CommandSender sender) {
    if (sender instanceof Player) {
      return this.getLanguage((OfflinePlayer) sender);
    }
    return new LanguageBundle("default", Lots.list(this.getDefault()));
  }

  @NonNull
  public String getLocale(@NonNull Player player) {
    return Players.getLocale(player).split("_")[0];
  }

  public Language getDefault() {
    if (this.languages.isEmpty()) {
      return FallbackLanguage.INSTANCE;
    } else {
      return this.languages.get(0);
    }
  }

  @Override
  public @NonNull String getName() {
    return "language";
  }
}
