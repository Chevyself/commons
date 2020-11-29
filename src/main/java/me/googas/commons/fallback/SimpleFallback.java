package me.googas.commons.fallback;

import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;
import lombok.experimental.Delegate;

public class SimpleFallback implements Fallback {

  @Delegate @NonNull private final List<String> errors = new ArrayList<>();

  @Override
  public void process(Throwable exception) {
    if (exception != null) this.process(exception, exception.getMessage());
  }

  @Override
  public void process(Throwable exception, String message) {
    exception.printStackTrace();
    if (message != null) this.add(message);
  }

  @Override
  public @NonNull List<String> getErrors() {
    return this.errors;
  }
}
