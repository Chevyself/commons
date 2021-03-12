package me.googas.starbox.wrappers;

import me.googas.annotations.Nullable;
import me.googas.commons.builder.ToStringBuilder;

public class SimpleWrapper implements Wrapper {

  @Nullable private Object reference;

  public SimpleWrapper(@Nullable Object reference) {
    this.reference = reference;
  }

  @Nullable
  @Override
  public Object get() {
    return this.reference;
  }

  @Override
  public void set(@Nullable Object object) {
    this.reference = object;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("reference", this.reference).build();
  }
}
