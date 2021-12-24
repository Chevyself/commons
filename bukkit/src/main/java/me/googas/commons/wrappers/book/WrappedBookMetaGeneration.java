package me.googas.commons.wrappers.book;

import lombok.NonNull;
import me.googas.commons.reflect.APIVersion;
import me.googas.commons.reflect.wrappers.WrappedClass;
import me.googas.commons.reflect.wrappers.WrappedMethod;
import me.googas.commons.utility.Versions;

@APIVersion(12)
public enum WrappedBookMetaGeneration {
  ORIGINAL,
  COPY_OF_ORIGINAL,
  COPY_OF_COPY,
  TATTERED;

  @NonNull
  public static final WrappedClass GENERATION =
      WrappedClass.parse("org.bukkit.inventory.meta.BookMeta.Generation");

  @NonNull
  private static final WrappedMethod VALUE_OF =
      WrappedBookMetaGeneration.GENERATION.getMethod("valueOf");

  @NonNull
  public Object toGeneration() {
    if (Versions.BUKKIT >= 12) {
      Object invoke = WrappedBookMetaGeneration.VALUE_OF.invoke(null, this.name());
      if (invoke != null) return invoke;
    }
    throw new IllegalStateException(
        "Attempted to get attribute in an illegal version of Minecraft");
  }
}
