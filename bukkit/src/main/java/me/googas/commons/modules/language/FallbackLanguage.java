package me.googas.commons.modules.language;

import lombok.NonNull;

public class FallbackLanguage implements Language {

  public static FallbackLanguage INSTANCE = new FallbackLanguage();

  @Override
  public @NonNull String getLocale() {
    return "fallback";
  }

  @Override
  public @NonNull String get(@NonNull String path) {
    return path;
  }
}
