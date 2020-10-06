package me.googas.commons.math.geometry.modifier;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import me.googas.commons.math.geometry.Point;
import me.googas.commons.math.geometry.Shape;
import me.googas.commons.math.geometry.containers.Points;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Inverse the shapes. It contains everything that is not inside the shapes */
public class Negative implements Modifier {

  /** The id of the shape */
  @Nullable private final String id;
  /** The shapes that are inside the negative */
  private final Set<Shape> shapes;

  /**
   * Create a negative modification
   *
   * @param id the id of the negative modification
   * @param shapes the shapes that contains this modification
   */
  public Negative(@Nullable String id, Set<Shape> shapes) {
    this.id = id;
    this.shapes = shapes;
  }

  /**
   * The id to identify a shape in runtime
   *
   * @return the id
   */
  @Override
  public @Nullable String getId() {
    return this.id;
  }

  /**
   * Get all the points inside the shape
   *
   * @return the points inside
   */
  @Override
  public @NotNull Points getPointsInside() {
    return new Points(new HashSet<>());
  }

  /**
   * Get the minimum point of the shape
   *
   * @return the minimum point of the shape
   */
  @Override
  public @NotNull Point getMinimum() {
    throw new UnsupportedOperationException(
        "There's infinite points. This operation would never end");
  }

  /**
   * Get the maximum point of the shape
   *
   * @return the maximum point of the shape
   */
  @Override
  public @NotNull Point getMaximum() {
    throw new UnsupportedOperationException(
        "There's infinite points. This operation would never end");
  }

  /**
   * Get the volume of the shape
   *
   * @return the volume of the shape
   */
  @Override
  public double getVolume() {
    return Double.MAX_VALUE;
  }

  @Override
  public Collection<Shape> getShapes() {
    return this.shapes;
  }

  @Override
  public String toString() {
    return "Negative{" + "id='" + this.id + '\'' + ", shapes=" + this.shapes + '}';
  }
}
