package me.googas.starbox.utility;

import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;

public class ListUtils {

  @NonNull
  @Deprecated
  public static List<String> divide(@NonNull String string, int length) {
    List<String> split = new ArrayList<>();
    while (string.length() > length) {
      String substring = string.substring(0, length);
      string = string.substring(length);
      split.add(substring);
    }
    if (!string.isEmpty()) split.add(string);
    return split;
  }
}
