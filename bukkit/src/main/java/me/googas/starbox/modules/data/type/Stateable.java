package me.googas.starbox.modules.data.type;

import lombok.NonNull;
import me.googas.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public interface Stateable {

  default double getStat(@Nullable String context, @NonNull String stat) {
    if (context == null) {
      context = "global";
    }
    return this.getStats().getOrDefault(context, new HashMap<>()).getOrDefault(stat, 0D);
  }

  default double getStat(@NonNull String stat) {
    return this.getStat("global", stat);
  }

  default boolean increaseStat(@Nullable String context, @NonNull String stat, double amount) {
    if (context == null) {
      context = "global";
    }
    Map<String, Double> map = this.getStats().computeIfAbsent(context, key -> new HashMap<>());
    map.put(stat, map.getOrDefault(stat, 0D) + amount);
    return true;
  }

  default boolean decreaseStat(@Nullable String context, @NonNull String stat, double amount) {
    if (context == null) {
      context = "global";
    }
    Map<String, Double> map = this.getStats().computeIfAbsent(context, key -> new HashMap<>());
    map.put(stat, map.getOrDefault(stat, 0D) - amount);
    return true;
  }

  @NonNull
  Map<String, Map<String, Double>> getStats();
}
