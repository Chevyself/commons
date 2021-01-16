package me.googas.commons.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import lombok.NonNull;

/** Provides an static instance of Gson and static utilities for adapters and factories. */
@Deprecated
public class GsonProvider {

  /** The map of adapters working in the gson instance */
  @NonNull private static final HashMap<Type, Object> adapters = new HashMap<>();
  /** The type adapter factories to register in gson */
  @NonNull private static final Set<TypeAdapterFactory> factories = new HashSet<>();
  /** The gson instance */
  @NonNull public static Gson GSON = GsonProvider.build();

  /** Builds and sets the gson instance */
  public static void refresh() {
    GsonProvider.GSON = GsonProvider.build();
  }

  /**
   * Builds a gson instance using the map of adapters, factories and pretty printing.
   *
   * @return a gson instance
   */
  @NonNull
  public static Gson build() {
    GsonBuilder builder = new GsonBuilder();
    builder.setPrettyPrinting();
    GsonProvider.adapters.forEach((builder::registerTypeAdapter));
    GsonProvider.factories.forEach(builder::registerTypeAdapterFactory);
    return builder.create();
  }

  /**
   * Adds and adapter to the map. This action does not update the gson instance after adding the
   * adapter use {@link #refresh()}
   *
   * @param type the type that the adapter serializes and deserializes
   * @param adapter the adapter
   */
  public static void addAdapter(@NonNull Type type, @NonNull Object adapter) {
    GsonProvider.adapters.put(type, adapter);
  }

  /**
   * Add a factory to the set. This action does not update the gson instance after adding the
   * adapter use {@link #refresh()}
   *
   * @param factory the factory to add
   * @return true if changes where made
   */
  public static boolean addFactory(@NonNull TypeAdapterFactory factory) {
    return GsonProvider.factories.add(factory);
  }

  /**
   * Saves an object to a file
   *
   * @param file the file to write
   * @param toWrite the object to write
   * @throws IOException in case file handling goes wrong: it could be that the file does not exist
   *     or th file writer just fails. It will be closed at the end
   */
  public static void save(@NonNull File file, @NonNull Object toWrite) throws IOException {
    FileWriter fileWriter = new FileWriter(file);
    fileWriter.write(GsonProvider.GSON.toJson(toWrite));
    fileWriter.close();
  }

  /**
   * Get the adapters inside the provider
   *
   * @return the adapters in a map
   */
  @NonNull
  public static HashMap<Type, Object> getAdapters() {
    return GsonProvider.adapters;
  }
}
