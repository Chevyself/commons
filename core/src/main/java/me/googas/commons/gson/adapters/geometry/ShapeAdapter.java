package me.googas.commons.gson.adapters.geometry;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import me.googas.commons.math.geometry.Shape;

/** Adapts shapes into json */
public interface ShapeAdapter<T extends Shape> extends JsonSerializer<T>, JsonDeserializer<T> {}
