package me.googas.commons.math.geometry.modifier;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import lombok.NonNull;
import me.googas.commons.math.geometry.Point;
import me.googas.commons.math.geometry.Shape;
import me.googas.commons.math.geometry.containers.Points;

/** A bunch of shapes */
public class Union implements Modifier {

  private final String id;
  /** The shapes inside the union */
  private final Set<Shape> shapes;

  /**
   * Create the union
   *
   * @param id the id of the union
   * @param shapes the shapes inside the union
   */
  public Union(String id, Set<Shape> shapes) {
    this.id = id;
    this.shapes = shapes;
  }

  /**
   * The id to identify a shape in runtime
   *
   * @return the id
   */
  @Override
  public String getId() {
    return this.id;
  }

  /**
   * Get all the points inside the shape
   *
   * @return the points inside
   */
  @Override
  public @NonNull Points getPointsInside() {
    Points points = new Points(new HashSet<>());
    for (Shape shape : this.shapes) {
      points.addAll(shape.getPointsInside());
    }
    return points;
  }

  /**
   * Get the minimum point of the shape
   *
   * @return the minimum point of the shape
   */
  @Override
  public @NonNull Point getMinimum() {
    return this.getPointsInside().getMinimum();
  }

  /**
   * Get the maximum point of the shape
   *
   * @return the maximum point of the shape
   */
  @Override
  public @NonNull Point getMaximum() {
    return this.getPointsInside().getMaximum();
  }

  /**
   * Get the volume of the shape
   *
   * @return the volume of the shape
   */
  @Override
  public double getVolume() {
    double volume = 0;
    for (Shape shape : this.shapes) {
      volume += shape.getVolume();
    }
    return volume;
  }

  @NonNull
  @Override
  public Collection<Shape> getShapes() {
    return this.shapes;
  }
}
