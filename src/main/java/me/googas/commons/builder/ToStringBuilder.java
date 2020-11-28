package me.googas.commons.builder;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Delegate;
import me.googas.commons.Lots;

/**
 * This object heads to help the construction of {@link Object#toString()} This string is not meant
 * to be read by an user
 */
public class ToStringBuilder implements Builder<String> {

  /** The string buffer to build the string */
  @NonNull @Getter @Delegate private final StringBuffer buffer;

  /** The object that is being built */
  private final Object object;

  /**
   * Create the builder
   *
   * @param buffer the buffer to build the strings of the object
   * @param object the object which strings is being build to
   */
  public ToStringBuilder(@NonNull StringBuffer buffer, Object object) {
    this.buffer = buffer;
    this.object = object;
  }

  /**
   * Create the builder
   *
   * @param object the object that is requesting {@link #toString()}
   */
  public ToStringBuilder(Object object) {
    this(new StringBuffer(), object);
  }

  /**
   * Appends a key and a value on the string
   *
   * @param key the key of the property to add in the string
   * @param value the value of the property to add in the string
   * @return the same instance of builder
   */
  @NonNull
  public ToStringBuilder append(@NonNull String key, Object value) {
    if (this.buffer.length() > 0) this.buffer.append(", ");
    this.buffer.append(key).append("=").append(value);
    return this;
  }

  /**
   * Append a key with a value as an array objects
   *
   * @param key the key of the property to add in the string
   * @param objects the objects of the property to add in the string
   * @return the same instance of builder
   */
  public ToStringBuilder append(@NonNull String key, Object... objects) {
    return this.append(key, Lots.inmutable(objects));
  }

  @NonNull
  @Override
  public String build() {
    return this.toString();
  }

  @Override
  public String toString() {
    return this.object == null
        ? "null"
        : this.object.getClass().getSimpleName()
            + "#"
            + this.object.hashCode()
            + "{"
            + this.buffer.toString()
            + "}";
  }
}
