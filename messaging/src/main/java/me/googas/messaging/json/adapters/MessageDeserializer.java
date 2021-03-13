package me.googas.messaging.json.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import me.googas.messaging.ReceivedRequest;
import me.googas.messaging.Response;
import me.googas.messaging.api.Message;

import java.lang.reflect.Type;

/** Deserializes messages */
public class MessageDeserializer implements JsonSerializer<Message>, JsonDeserializer<Message> {

  @Override
  public JsonElement serialize(Message src, Type typeOfSrc, JsonSerializationContext context) {
    return context.serialize(src);
  }

  @Override
  public Message deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    if (json.isJsonObject()) {
      JsonObject jsonObject = json.getAsJsonObject();
      if (jsonObject.get("method") != null) {
        return context.deserialize(jsonObject, ReceivedRequest.class);
      } else {
        return context.deserialize(jsonObject, Response.class);
      }
    }
    throw new JsonParseException("Messages must be json objects");
  }
}
