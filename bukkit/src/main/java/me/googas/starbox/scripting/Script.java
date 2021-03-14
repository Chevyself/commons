package me.googas.starbox.scripting;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import jdk.nashorn.api.scripting.NashornScriptEngine;
import lombok.Getter;
import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.starbox.CoreFiles;
import me.googas.starbox.Starbox;
import me.googas.starbox.builder.ToStringBuilder;
import org.bukkit.Bukkit;

/** Uses JavaScript ES5 https://www.w3schools.com/Js/js_es5.asp */
public class Script {

  @NonNull private static final Gson gson = new GsonBuilder().create();

  @NonNull
  private static final NashornScriptEngine engine =
      (NashornScriptEngine) new ScriptEngineManager().getEngineByName("javascript");

  static {
    Script.engine.put("Bukkit", Bukkit.getServer());
  }

  @NonNull @Getter private final String name;
  @NonNull @Getter private final String description;
  @NonNull @Getter private final String version;
  @NonNull @Getter private final String author;
  @NonNull @Getter private String main;

  public Script(
      @NonNull String name,
      @NonNull String description,
      @NonNull String version,
      @NonNull String author,
      @NonNull String main) {
    this.name = name;
    this.description = description;
    this.version = version;
    this.author = author;
    this.main = main;
  }

  /** @deprecated this may be only used if a script cannot be loaded */
  public Script() {
    this("", "", "", "", "");
  }

  /**
   * Load a script using a json file
   *
   * @param json the json file to loadJson the script
   * @return the loaded script
   */
  @NonNull
  public static Script loadJson(@NonNull File json) {
    try {
      FileReader reader = new FileReader(json);
      Script script = Script.gson.fromJson(reader, Script.class);
      if (script.getMain().startsWith("path:")) {
        script.main = Script.loadMain(script.getMain());
      }
      return script;
    } catch (FileNotFoundException e) {
      String message = "Could not loadJson json script from " + json;
      Starbox.getFallback().process(e, message);
      throw new IllegalArgumentException(message);
    }
  }

  @NonNull
  public static String loadMain(@NonNull String mainPath) {
    mainPath = mainPath.startsWith("path:") ? mainPath.substring(5) : mainPath;
    mainPath = mainPath.endsWith(".js") ? mainPath : mainPath + ".js";
    File file = CoreFiles.getFile(CoreFiles.currentDirectory() + mainPath);
    if (file != null) {
      FileReader reader = null;
      BufferedReader bufferedReader = null;
      StringBuilder builder = new StringBuilder();
      try {
        reader = new FileReader(file);
        bufferedReader = new BufferedReader(reader);
        String line;
        String lineSeparator = System.getProperty("line.separator");
        while ((line = bufferedReader.readLine()) != null) {
          builder.append(line).append(lineSeparator);
        }
      } catch (IOException e) {
        Starbox.getFallback().process(e);
      }
      try {
        if (reader != null) reader.close();
      } catch (IOException e) {
        Starbox.getFallback().process(e);
      }
      try {
        if (bufferedReader != null) bufferedReader.close();
      } catch (IOException e) {
        Starbox.getFallback().process(e);
      }
      String string = builder.toString();
      try {
        Script.engine.compile(string);
        return string;
      } catch (ScriptException e) {
        throw new IllegalArgumentException("Could not load from " + mainPath, e);
      }
    }
    throw new IllegalArgumentException("Could not find file in " + mainPath);
  }

  @Nullable
  public Object run() {
    return this.run(null);
  }

  @Nullable
  public Object run(@Nullable Map<String, Object> params) {
    try {
      if (params != null) params.forEach(Script.engine::put);
      return Script.engine.eval(this.main);
    } catch (ScriptException e) {
      Starbox.getFallback()
          .process(e, "There's been an error while executing the script " + this.name);
      return null;
    }
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("name", this.name)
        .append("description", this.description)
        .append("version", this.version)
        .append("author", this.author)
        .append("main", this.main)
        .build();
  }
}
