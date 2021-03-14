package me.googas.starbox.math.geometry.modifier;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import lombok.NonNull;
import me.googas.starbox.math.geometry.Point;
import me.googas.starbox.math.geometry.Shape;
import me.googas.starbox.math.geometry.containers.Points;

/** Returns the area where the regions intersect */
public class Intersect implements Modifier {

  private final String id;
  /** The shapes to calculate where they intersect */
  @NonNull private final Set<Shape> shapes;

  /**
   * Create the intersect modifier
   *
   * @param id the id of the shape
   * @param shapes the shapes to get the area where they intersect
   */
  public Intersect(String id, @NonNull Set<Shape> shapes) {
    this.id = id;
    this.shapes = shapes;
  }

  @Override
  public String getId() {
    return this.id;
  }

  @Override
  public Points getPointsInside() {
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

  @Override
  public @NonNull Point getMinimum() {
    return this.getPointsInside().getMinimum();
  }

  @Override
  public @NonNull Point getMaximum() {
    return this.getPointsInside().getMaximum();
  }

  @Override
  public double getVolume() {
    return 0;
  }

  @Override
  public Collection<Shape> getShapes() {
    return this.shapes;
  }
}
