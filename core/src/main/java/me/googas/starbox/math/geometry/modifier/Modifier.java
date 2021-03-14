package me.googas.starbox.math.geometry.modifier;

import java.util.Collection;
import lombok.NonNull;
import me.googas.starbox.math.geometry.Shape;

/** Shapes can have different modifications which introduce a bunch of new usages */
public interface Modifier extends Shape {

  /**
   * Get the shapes that are being modified
   *
   * @return the collection of shapes
   */
  @NonNull
  Collection<Shape> getShapes();
}
