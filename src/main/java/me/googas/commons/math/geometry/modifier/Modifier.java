package me.googas.commons.math.geometry.modifier;

import java.util.Collection;
import me.googas.commons.math.geometry.Shape;
import org.jetbrains.annotations.NotNull;

/** Shapes can have different modifications which introduce a bunch of new usages */
public interface Modifier extends Shape {

  /**
   * Get the shapes that are being modified
   *
   * @return the collection of shapes
   */
  @NotNull
  Collection<Shape> getShapes();
}
