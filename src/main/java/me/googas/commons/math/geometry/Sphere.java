package me.googas.commons.math.geometry;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.googas.commons.RandomUtils;
import me.googas.commons.math.MathUtils;
import me.googas.commons.math.Shapes;
import me.googas.commons.math.geometry.containers.Points;

/** A sphere */
public class Sphere implements Shape {

  private final String id;
  /** The center of the sphere */
  @NonNull @Getter @Setter private Point center;
  /** The radius of the sphere */
  @Getter @Setter private double radius;

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
    return Shapes.getPoints(this, this.getMinimum(), this.getMaximum());
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
