package me.googas.commons.math.geometry.modifier;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import lombok.NonNull;
import me.googas.commons.math.geometry.Point;
import me.googas.commons.math.geometry.Shape;
import me.googas.commons.math.geometry.containers.Points;

/** Subtracts the intersecting points for the first shape */
public class Complement implements Modifier {

  private final String id;
  /** The shape to complement */
  @NonNull private final Shape shape;
  /** The shapes inside the union */
  @NonNull private final Set<Shape> shapes;

  public Complement(String id, @NonNull Shape shape, @NonNull Set<Shape> shapes) {
    this.id = id;
    this.shape = shape;
    this.shapes = shapes;
  }

  @Override
  public String getId() {
    return this.id;
  }

  @Override
  public @NonNull Points getPointsInside() {
    Points points = new Points(new HashSet<>());
    for (Shape shape : this.shapes) {
      if (!shape.getPointsInside().isInfinite()) {
        for (Point point : shape.getPointsInside().getPoints()) {
          if (!this.shape.contains(point)) {
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
    Set<Shape> shapes = new HashSet<>(this.shapes);
    shapes.add(this.shape);
    return shapes;
  }
}
