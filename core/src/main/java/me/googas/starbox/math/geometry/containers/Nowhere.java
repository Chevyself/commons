package me.googas.starbox.math.geometry.containers;

import lombok.NonNull;
import me.googas.starbox.math.geometry.Point;
import me.googas.starbox.math.geometry.Shape;

/** A shape that does not have points */
public class Nowhere implements Shape {

  @Override
  public String getId() {
    return null;
  }

  @Override
  public @NonNull Points getPointsInside() {
    throw new UnsupportedOperationException("There's no points inside this shape");
  }

  @Override
  public @NonNull Point getMinimum() {
    throw new UnsupportedOperationException("There's no points inside this shape");
  }

  @Override
  public @NonNull Point getMaximum() {
    throw new UnsupportedOperationException("There's no points inside this shape");
  }

  @Override
  public double getVolume() {
    throw new UnsupportedOperationException("There's no points inside this shape");
  }

  @Override
  public boolean contains(@NonNull Point point) {
    return false;
  }

  @Override
  public boolean contains(@NonNull Shape another) {
    return false;
  }

  @Override
  public boolean intersects(@NonNull Shape another) {
    return false;
  }

  @Override
  public @NonNull Points intersectingPoints(@NonNull Shape another) {
    throw new UnsupportedOperationException("There's no points inside this shape");
  }

  @Override
  public @NonNull Points getFacePoints() {
    throw new UnsupportedOperationException("There's no points inside this shape");
  }

  @Override
  public @NonNull Point getRandomPoint() {
    throw new UnsupportedOperationException("There's no points inside this shape");
  }
}
