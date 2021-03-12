package me.googas.starbox.reflect;

import lombok.Getter;
import lombok.NonNull;
import me.googas.annotations.Nullable;
import me.googas.commons.builder.ToStringBuilder;
import me.googas.starbox.Starbox;

import java.lang.reflect.Field;
import java.util.logging.Level;

public class WrappedField {

  @Nullable @Getter private final Field field;

  public WrappedField(@Nullable Field field) {
    this.field = field;
    if (field != null) field.setAccessible(true);
  }

  public WrappedField() {
    this(null);
  }

  @Nullable
  public Object get(@NonNull Object obj) {
    if (this.field != null) {
      try {
        return this.field.get(obj);
      } catch (IllegalAccessException e) {
        Starbox.getPlugin().getLogger().log(Level.SEVERE, e, () -> "");
      }
    }
    return null;
  }

  public boolean set(@NonNull Object object, @Nullable Object value) {
    if (this.field != null) {
      try {
        this.field.set(object, value);
        return true;
      } catch (IllegalAccessException e) {
        Starbox.getPlugin().getLogger().log(Level.SEVERE, e, () -> "");
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("field", this.field).build();
  }
}
