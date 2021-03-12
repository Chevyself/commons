package me.googas.starbox.gson.adapters.time;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import me.googas.starbox.time.Time;

import java.lang.reflect.Type;

public class TimeAdapter implements JsonSerializer<Time>, JsonDeserializer<Time> {

  @Override
  public JsonElement serialize(Time src, Type typeOfSrc, JsonSerializationContext context) {
    return new JsonPrimitive(src.toDatabaseString());
  }

  @Override
  public Time deserialize(JsonElement src, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    return Time.fromString(src.getAsString());
  }
}
