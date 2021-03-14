package me.googas.starbox.math.geometry.modifier;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import lombok.NonNull;
import me.googas.starbox.math.geometry.Point;
import me.googas.starbox.math.geometry.Shape;
import me.googas.starbox.math.geometry.containers.Points;

/** A bunch of shapes */
public class Union implements Modifier {

  private final String id;
  /** The shapes inside the union */
  @NonNull private final Set<Shape> shapes;

  /**
   * Create the union
   *
   * @param id the id of the union
   * @param shapes the shapes inside the union
   */
  public Union(String id, @NonNull Set<Shape> shapes) {
    this.id = id;
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
      points.addAll(shape.getPointsInside());
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
