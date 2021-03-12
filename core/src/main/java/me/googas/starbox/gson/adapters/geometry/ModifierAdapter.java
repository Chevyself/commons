package me.googas.starbox.gson.adapters.geometry;

import me.googas.starbox.math.geometry.modifier.Modifier;

/** Adapts modifiers in json */
public interface ModifierAdapter<T extends Modifier> extends ShapeAdapter<T> {}
