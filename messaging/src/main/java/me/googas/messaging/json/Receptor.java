package me.googas.messaging.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import lombok.NonNull;

/**
 * This annotation is used in methods which are capable of receiving {@link
 * me.googas.messaging.Request}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Receptor {
  /**
   * The method of the request which this receptor accepts
   *
   * @deprecated since 1.0.8-SNAPSHOT since
   * @return the method
   */
  @NonNull
  String method() default "";
  /**
   * The method of the request which this receptor accepts
   *
   * @return the method
   */
  @NonNull
  String value();
}
