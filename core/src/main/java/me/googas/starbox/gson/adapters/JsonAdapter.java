package me.googas.starbox.gson.adapters;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

/**
 * This class represents a serializer and deserializer for an object. Felt like this class was
 * missing from Gson
 */
public interface JsonAdapter<T> extends JsonSerializer<T>, JsonDeserializer<T> {}
