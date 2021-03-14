package me.googas.starbox.gson.adapters.time;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import me.googas.starbox.time.ClassicTime;
import me.googas.starbox.time.Time;

public class ClassicTimeAdapter
    implements JsonSerializer<ClassicTime>, JsonDeserializer<ClassicTime> {

  @Override
  public JsonElement serialize(
      ClassicTime classicTime, Type type, JsonSerializationContext jsonSerializationContext) {
    return new JsonPrimitive(classicTime.toTime().toDatabaseString());
  }

  @Override
  public ClassicTime deserialize(
      JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {
    return Time.fromString(jsonElement.getAsString()).toClassicTime();
  }
}
