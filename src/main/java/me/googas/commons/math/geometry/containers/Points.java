package me.googas.commons.math.geometry.containers;

import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import me.googas.commons.math.geometry.Point;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** A set of points inside a region */
public class Points {

  /** The contained points */
  @NotNull private final Set<Point> points;

  /**
   * Create the collection of points
   *
   * @param points the set of points
   */
  public Points(@NotNull Set<Point> points) {
    this.points = points;
  }

  /**
   * Adds all the points inside another set of points
   *
   * @param points the points to add
   * @return true if there where changed made
   */
  public boolean addAll(@NotNull Points points) {
    if (!points.isInfinite()) {
      this.addAll(points.getPoints());
      return true;
    }
    return false;
  }

  /**
   * Get the size of the points
   *
   * @return the size of the points
   */
  public int size() {
    return this.points.size();
  }

  /**
   * Get whether a point is inside or not
   *
   * @param point the point to check if it is inside
   * @return true if this contains the point
   */
  public boolean contains(@Nullable Point point) {
    return this.points.contains(point);
  }

  /**
   * Adds a point to the set of points
   *
   * @param point the point to add
   * @return whether or not the point was added
   */
  public boolean add(@NotNull Point point) {
    return this.points.add(point);
  }

  /**
   * Removes a point from the set
   *
   * @param point the point to remove from the set
   * @return whether or not the point was removed
   */
  public boolean remove(@Nullable Point point) {
    return this.points.remove(point);
  }

  /**
   * Checks if a collection of points is contained inside the set
   *
   * @param collection the collection of points
   * @return true if the collection of points is contained
   */
  public boolean containsAll(@NotNull Collection<? extends Point> collection) {
    return this.points.containsAll(collection);
  }

  /**
   * Adds a collection of points to the set
   *
   * @param collection the collection to add
   * @return true if the collection was added
   */
  public boolean addAll(@NotNull Collection<? extends Point> collection) {
    return this.points.addAll(collection);
  }

  /**
   * Removes all the points that are not contained in another collection of points
   *
   * @param collection the collection of points
   * @return true if the set changed
   */
  public boolean retainAll(@NotNull Collection<? extends Point> collection) {
    return this.points.retainAll(collection);
  }

  /**
   * Removes all the points contained inside another collection
   *
   * @param collection the points to remove from the set
   * @return true if there was changes
   */
  public boolean removeAll(@NotNull Collection<? extends Point> collection) {
    return this.points.removeAll(collection);
  }

  /** Clears the set of points */
  public void clear() {
    this.points.clear();
  }

  /**
   * Removes from the set the points which assert the predicate
   *
   * @param filter the filter which the set should assert
   * @return true if there was changes made
   */
  public boolean removeIf(@NotNull Predicate<? super Point> filter) {
    return this.points.removeIf(filter);
  }

  /**
   * Creates an stream of points
   *
   * @return the stream of points
   */
  @Deprecated
  public Stream<Point> stream() {
    return this.points.stream();
  }

  /**
   * Makes each points do a different action
   *
   * @param action the action for each point
   */
  @Deprecated
  public void forEach(Consumer<? super Point> action) {
    this.points.forEach(action);
  }

  /**
   * Get the contained points
   *
   * @return the points
   */
  @NotNull
  public Set<Point> getPoints() {
    return this.points;
  }

  /**
   * Get whether the contained points are infinite
   *
   * @return true if there's an infinite amount of points
   */
  public boolean isInfinite() {
    return false;
  }

  /**
   * Get whether there's contained points
   *
   * @return true if there's no points inside
   */
  public boolean isEmpty() {
    return this.points.isEmpty();
  }

  /**
   * Get the smallest point
   *
   * <p>The default value is a point x=0, y=0, z=0
   *
   * @return the smallest point
   */
  @NotNull
  public Point getMinimum() {
    Point min = new Point(0, 0, 0);
    for (Point point : this.points) {
      if (point.lowerThan(min)) {
        min = point;
      }
    }
    return min;
  }

  /**
   * Get the biggest point
   *
   * <p>The default value is a point x=0, y=0, z=0
   *
   * @return the biggest point
   */
  @NotNull
  public Point getMaximum() {
    Point max = new Point(0, 0, 0);
    for (Point point : this.points) {
      if (point.greaterThan(max)) {
        max = point;
      }
    }
    return max;
  }
}
