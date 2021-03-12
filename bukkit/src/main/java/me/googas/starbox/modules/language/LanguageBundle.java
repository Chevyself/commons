package me.googas.starbox.modules.language;

import lombok.Getter;
import lombok.NonNull;

import java.util.List;

public class LanguageBundle implements Language {

  @NonNull @Getter private final String locale;
  @NonNull @Getter private final List<Language> languages;

  public LanguageBundle(@NonNull String locale, @NonNull List<Language> languages) {
    this.locale = locale;
    this.languages = languages;
  }

  @Override
  public @NonNull String get(@NonNull String path) {
    for (Language language : this.languages) {
      return language.get(path);
    }
    return path;
  }
}
