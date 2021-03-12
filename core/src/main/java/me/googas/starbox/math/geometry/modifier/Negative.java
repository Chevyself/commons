package me.googas.starbox.math.geometry.modifier;

import lombok.NonNull;
import me.googas.starbox.math.geometry.Point;
import me.googas.starbox.math.geometry.Shape;
import me.googas.starbox.math.geometry.containers.Points;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/** Inverse the shapes. It contains everything that is not inside the shapes */
public class Negative implements Modifier {

  private final String id;
  /** The shapes that are inside the negative */
  @NonNull private final Set<Shape> shapes;

  /**
   * Create a negative modification
   *
   * @param id the id of the negative modification
   * @param shapes the shapes that contains this modification
   */
  public Negative(String id, @NonNull Set<Shape> shapes) {
    this.id = id;
    this.shapes = shapes;
  }

  @Override
  public String getId() {
    return this.id;
  }

  @Override
  public @NonNull Points getPointsInside() {
    return new Points(new HashSet<>());
  }

  @Override
  public @NonNull Point getMinimum() {
    throw new UnsupportedOperationException(
        "There's infinite points. This operation would never end");
  }

  @Override
  public @NonNull Point getMaximum() {
    throw new UnsupportedOperationException(
        "There's infinite points. This operation would never end");
  }

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
