package me.googas.commons.gson.adapters.geometry;

import me.googas.commons.math.geometry.modifier.Modifier;

/** Adapts modifiers in json */
public interface ModifierAdapter<T extends Modifier> extends ShapeAdapter<T> {}
