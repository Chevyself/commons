package me.googas.commons.math.geometry;

import java.util.HashSet;
import java.util.Set;
import lombok.NonNull;
import me.googas.commons.RandomUtils;
import me.googas.commons.math.MathUtils;
import me.googas.commons.math.geometry.containers.Points;

/** A sphere */
public class Sphere implements Shape {

  private final String id;
  /** The center of the sphere */
  @NonNull private Point center;
  /** The radius of the sphere */
  private double radius;

  /**
   * Create the sphere
   *
   * @param id the id of the sphere
   * @param center the center of the sphere
   * @param radius the radius of the sphere
   */
  public Sphere(String id, @NonNull Point center, double radius) {
    this.id = id;
    this.center = center;
    this.radius = radius;
  }

  /**
   * Set the center of the sphere
   *
   * @param center the new center of the sphere
   */
  public void setCenter(@NonNull Point center) {
    this.center = center;
  }

  /**
   * Set the radius of the sphere
   *
   * @param radius the new radius of the sphere
   */
  public void setRadius(double radius) {
    this.radius = radius;
  }

  /**
   * Get the center of the sphere
   *
   * @return the center of the sphere
   */
  @NonNull
  public Point getCenter() {
    return this.center;
  }

  /**
   * Get the radius of the sphere
   *
   * @return the radius of the sphere
   */
  public double getRadius() {
    return this.radius;
  }

  @Override
  public String getId() {
    return this.id;
  }

  @Override
  public boolean contains(@NonNull Point point) {
    return (MathUtils.square(point.getX() - this.center.getX())
            + MathUtils.square(point.getY() - this.center.getY())
            + MathUtils.square(point.getZ() - this.center.getZ())
        <= MathUtils.square(this.radius));
  }

  @Override
  public double getVolume() {
    return 4 / 3f * Math.PI * MathUtils.square(this.radius);
  }

  @Override
  public @NonNull Points getPointsInside() {
    Set<Point> set = new HashSet<>();
    for (Point point :
        new Box(this.getMinimum(), this.getMaximum(), null).getPointsInside().getPoints()) {
      if (this.contains(point)) {
        set.add(point);
      }
    }
    return new Points(set);
  }

  @Override
  public @NonNull Point getMinimum() {
    return new Point(
        this.center.getX() - this.radius,
        this.center.getY() - this.radius,
        this.center.getZ() - this.radius);
  }

  @Override
  public @NonNull Point getMaximum() {
    return new Point(
        this.center.getX() + this.radius,
        this.center.getY() + this.radius,
        this.center.getZ() + this.radius);
  }

  @Override
  public String toString() {
    return "Sphere{"
        + "id='"
        + this.id
        + '\''
        + ", center="
        + this.center
        + ", radius="
        + this.radius
        + '}';
  }

  @Override
  public @NonNull Point getRandomPoint() {
    double x =
        this.getCenter().getX()
            + RandomUtils.nextDouble(0, this.radius) * Math.sin(RandomUtils.nextDouble(0, 360));
    double y =
        this.getCenter().getY()
            + RandomUtils.nextDouble(0, this.radius) * Math.sin(RandomUtils.nextDouble(0, 360));
    double z =
        this.getCenter().getZ()
            + RandomUtils.nextDouble(0, this.radius) * Math.sin(RandomUtils.nextDouble(0, 360));
    return new Point(x, y, z);
  }
}
