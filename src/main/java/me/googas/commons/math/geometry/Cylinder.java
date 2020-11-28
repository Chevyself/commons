package me.googas.commons.math.geometry;

import java.util.HashSet;
import java.util.Set;
import lombok.NonNull;
import me.googas.commons.RandomUtils;
import me.googas.commons.math.MathUtils;
import me.googas.commons.math.geometry.containers.Points;

/** A cylinder */
public class Cylinder implements Shape {

  private final String id;

  /** The base of the cylinder */
  @NonNull private Point base;
  /** The radius of the cylinder */
  private double radius;
  /** The height of the cylinder */
  private double height;

  /**
   * Create a cylinder
   *
   * @param id the id of the cylinder
   * @param base the base of the cylinder
   * @param radius the radius of the cylinder
   * @param height the height of the cylinder
   */
  public Cylinder(String id, @NonNull Point base, double radius, double height) {
    this.id = id;
    this.base = base;
    this.radius = radius;
    this.height = height;
  }

  /**
   * Set the base of the cylinder
   *
   * @param base the new base
   */
  public void setBase(@NonNull Point base) {
    this.base = base;
  }

  /**
   * Set the radius of the cylinder
   *
   * @param radius the new radius
   */
  public void setRadius(double radius) {
    this.radius = radius;
  }

  /**
   * Set the height of the cylinder
   *
   * @param height the new height
   */
  public void setHeight(double height) {
    this.height = height;
  }

  /**
   * Get the base of the cylinder
   *
   * @return the base of the cylinder
   */
  @NonNull
  public Point getBase() {
    return this.base;
  }

  /**
   * Get the radius of the cylinder
   *
   * @return the radius
   */
  public double getRadius() {
    return this.radius;
  }

  /**
   * Get the height of the cylinder
   *
   * @return the height of the cylinder
   */
  public double getHeight() {
    return this.height;
  }

  /**
   * Get the height + the y coordinate of the base
   *
   * @return the total height
   */
  public double getTotalHeight() {
    return this.base.getY() + this.height;
  }

  /**
   * Get the minimum point of the cylinder
   *
   * @return the minimum point
   */
  @NonNull
  @Override
  public Point getMinimum() {
    return new Point(
        this.base.getX() - this.radius, this.base.getY(), this.base.getZ() - this.radius);
  }

  /**
   * Get the maximum point of the cylinder
   *
   * @return the maximum point
   */
  @NonNull
  @Override
  public Point getMaximum() {
    return new Point(
        this.base.getX() + this.radius,
        this.base.getY() + this.height,
        this.base.getZ() + this.radius);
  }

  @Override
  public String getId() {
    return this.id;
  }

  @Override
  public boolean contains(@NonNull Point point) {
    return point.getY() >= this.base.getY()
        && point.getY() <= this.getTotalHeight()
        && (MathUtils.square(point.getX() - this.base.getX())
                + MathUtils.square(point.getZ() - this.base.getZ())
            <= MathUtils.square(this.radius));
  }

  @Override
  public double getVolume() {
    return this.height * Math.PI * MathUtils.square(this.radius);
  }

  @NonNull
  @Override
  public Points getPointsInside() {
    Set<Point> set = new HashSet<>();
    for (Point point :
        new Box(this.getMinimum(), this.getMaximum(), null).getPointsInside().getPoints()) {
      if (this.contains(point)) {
        set.add(point);
      }
    }
    return new Points(set);
  }

  /**
   * Get a random point inside of the shape
   *
   * @return the random point
   */
  @Override
  public @NonNull Point getRandomPoint() {
    double x =
        this.getBase().getX()
            + RandomUtils.nextDouble(0, this.radius) * Math.sin(RandomUtils.nextDouble(0, 360));
    double y =
        RandomUtils.nextDouble(this.base.getY() - this.height, this.base.getY() + this.height);
    double z =
        this.getBase().getZ()
            + RandomUtils.nextDouble(0, this.radius) * Math.sin(RandomUtils.nextDouble(0, 360));
    return new Point(x, y, z);
  }

  @Override
  public String toString() {
    return "Cylinder{"
        + "id='"
        + this.id
        + '\''
        + ", base="
        + this.base
        + ", radius="
        + this.radius
        + ", height="
        + this.height
        + '}';
  }
}
