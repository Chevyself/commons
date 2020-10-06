package me.googas.commons.gson.adapters.geometry;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.lang.reflect.Type;
import me.googas.commons.math.geometry.Box;
import me.googas.commons.math.geometry.Cylinder;
import me.googas.commons.math.geometry.Shape;
import me.googas.commons.math.geometry.Sphere;
import me.googas.commons.math.geometry.modifier.Modifier;

/** Adapts shapes from json */
public class ShapesAdapter implements ShapeAdapter<Shape> {

  @Override
  public JsonElement serialize(Shape shape, Type type, JsonSerializationContext context) {
    return context.serialize(shape);
  }

  @Override
  public Shape deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
      throws JsonParseException {
    JsonObject object = jsonElement.getAsJsonObject();
    if (object.get("minimum") != null && object.get("maximum") != null) {
      return context.deserialize(object, Box.class);
    } else if (object.get("base") != null
        && object.get("radius") != null
        && object.get("height") != null) {
      return context.deserialize(object, Cylinder.class);
    } else if (object.get("center") != null && object.get("radius") != null) {
      return context.deserialize(object, Sphere.class);
    } else if (object.get("shapes") != null) {
      return context.deserialize(object, Modifier.class);
    } else {
      throw new JsonParseException("Your input does not match a known shape in: " + object);
    }
  }
}
