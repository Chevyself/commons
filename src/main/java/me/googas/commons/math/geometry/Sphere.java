package me.googas.commons.math.geometry;

import java.util.HashSet;
import java.util.Set;
import me.googas.commons.RandomUtils;
import me.googas.commons.math.MathUtils;
import me.googas.commons.math.geometry.containers.Points;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** A sphere */
public class Sphere implements Shape {

  /** The id of the sphere */
  @Nullable private final String id;
  /** The center of the sphere */
  @NotNull private Point center;
  /** The radius of the sphere */
  private double radius;

  /**
   * Create the sphere
   *
   * @param id the id of the sphere
   * @param center the center of the sphere
   * @param radius the radius of the sphere
   */
  public Sphere(@Nullable String id, @NotNull Point center, double radius) {
    this.id = id;
    this.center = center;
    this.radius = radius;
  }

  /**
   * Set the center of the sphere
   *
   * @param center the new center of the sphere
   */
  public void setCenter(@NotNull Point center) {
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
  @NotNull
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
  public @Nullable String getId() {
    return this.id;
  }

  @Override
  public boolean contains(@NotNull Point point) {
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
  public @NotNull Points getPointsInside() {
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
  public @NotNull Point getMinimum() {
    return new Point(
        this.center.getX() - this.radius,
        this.center.getY() - this.radius,
        this.center.getZ() - this.radius);
  }

  @Override
  public @NotNull Point getMaximum() {
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

  /**
   * Get a random point inside of the shape
   *
   * @return the random point
   */
  @Override
  public @NotNull Point getRandomPoint() {
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
