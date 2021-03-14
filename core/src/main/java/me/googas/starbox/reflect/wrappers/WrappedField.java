package me.googas.starbox.reflect.wrappers;

import java.lang.reflect.Field;
import lombok.Getter;
import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.starbox.builder.ToStringBuilder;

/**
 * This object represents field wrapped to avoid exceptions such as {@link IllegalAccessException}
 * while giving or getting its values
 */
public class WrappedField {

  @Nullable @Getter private final Field field;

  /**
   * Create the wrapped field
   *
   * @param field the field to be wrapped
   */
  public WrappedField(@Nullable Field field) {
    this.field = field;
    if (field != null) field.setAccessible(true);
  }

  /** Create a wrapped field without initializing its wrap */
  public WrappedField() {
    this(null);
  }

  /**
   * Get the object that is set in the field
   *
   * @param obj the object instance to get its field value
   * @return the value of its field
   */
  @Nullable
  public Object get(@NonNull Object obj) {
    if (this.field != null) {
      try {
        return this.field.get(obj);
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  /**
   * Set the object that is in the field
   *
   * @param object the object instance to set its field value
   * @param value the value of its field
   * @return true if the value has been set
   */
  public boolean set(@NonNull Object object, @Nullable Object value) {
    if (this.field != null) {
      try {
        this.field.set(object, value);
        return true;
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("field", this.field).build();
  }
}
