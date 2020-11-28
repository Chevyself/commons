package me.googas.commons.fallback;

import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;
import lombok.experimental.Delegate;

public class SimpleFallback implements Fallback {

  @Delegate @NonNull private final List<String> errors = new ArrayList<>();

  @Override
  public @NonNull List<String> getErrors() {
    return this.errors;
  }
}
