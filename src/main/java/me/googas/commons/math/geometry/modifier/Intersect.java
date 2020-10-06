package me.googas.commons.math.geometry.modifier;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import me.googas.commons.math.geometry.Point;
import me.googas.commons.math.geometry.Shape;
import me.googas.commons.math.geometry.containers.Points;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Returns the area where the regions intersect */
public class Intersect implements Modifier {

  /** The id of the shape */
  @Nullable private final String id;
  /** The shapes to calculate where they intersect */
  private final Set<Shape> shapes;

  /**
   * Create the intersect modifier
   *
   * @param id the id of the shape
   * @param shapes the shapes to get the area where they intersect
   */
  public Intersect(@Nullable String id, Set<Shape> shapes) {
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
    Points points = new Points(new HashSet<>());
    for (Shape shape : this.shapes) {
      for (Shape compare : this.shapes) {
        for (Point point : compare.getPointsInside().getPoints()) {
          if (shape.getPointsInside().contains(point)) {
            points.add(point);
          }
        }
      }
    }
    return points;
  }

  /**
   * Get the minimum point of the shape
   *
   * @return the minimum point of the shape
   */
  @Override
  public @NotNull Point getMinimum() {
    return this.getPointsInside().getMinimum();
  }

  /**
   * Get the maximum point of the shape
   *
   * @return the maximum point of the shape
   */
  @Override
  public @NotNull Point getMaximum() {
    return this.getPointsInside().getMaximum();
  }

  /**
   * Get the volume of the shape
   *
   * @return the volume of the shape
   */
  @Override
  public double getVolume() {
    return 0;
  }

  @Override
  public Collection<Shape> getShapes() {
    return this.shapes;
  }
}
