package me.googas.commons.utility.items.meta;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NonNull;
import me.googas.commons.reflect.wrappers.WrappedClass;
import me.googas.commons.reflect.wrappers.WrappedMethod;
import me.googas.commons.utility.Versions;
import me.googas.commons.utility.items.ItemBuilder;
import me.googas.commons.wrappers.book.WrappedBookMetaGeneration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class BookMetaBuilder extends ItemMetaBuilder {

  @NonNull
  private static final WrappedClass BOOK_META =
      WrappedClass.parse("org.bukkit.inventory.meta.BookMeta");

  @NonNull
  private static final WrappedMethod SET_GENERATION =
      BookMetaBuilder.BOOK_META.getMethod(
          "setGeneration", WrappedBookMetaGeneration.GENERATION.getClazz());

  @NonNull @Getter private final List<String> pages = new ArrayList<>();
  @NonNull @Getter private String author = "Unknown";

  @NonNull @Getter
  private WrappedBookMetaGeneration wrappedGeneration = WrappedBookMetaGeneration.ORIGINAL;

  public BookMetaBuilder(@NonNull ItemBuilder itemBuilder) {
    super(itemBuilder);
  }

  @NonNull
  public BookMetaBuilder setAuthor(String author) {
    this.author = author;
    return this;
  }

  @NonNull
  public BookMetaBuilder setWrappedGeneration(WrappedBookMetaGeneration wrappedGeneration) {
    this.wrappedGeneration = wrappedGeneration;
    return this;
  }

  @Override
  public BookMeta build(@NonNull ItemStack stack) {
    ItemMeta itemMeta = super.build(stack);
    if (!(itemMeta instanceof BookMeta)) return null;
    BookMeta meta = (BookMeta) itemMeta;
    meta.setAuthor(this.author);
    meta.setPages(this.pages);
    if (Versions.BUKKIT >= 12) {
      BookMetaBuilder.SET_GENERATION.invoke(meta, this.wrappedGeneration.toGeneration());
    }
    return meta;
  }
}
