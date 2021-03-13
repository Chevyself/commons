package me.googas.messaging;

import lombok.NonNull;
import me.googas.messaging.api.Message;

import java.util.Map;

/** An extension for messages to receive a {@link Response} */
public interface IRequest extends Message {

  /**
   * Get the method of the receptor this request is trying to invoke
   *
   * @return the method as a string
   */
  @NonNull
  String getMethod();

  /**
   * Get the parameters that the receptor needs to give a response
   *
   * @return the parameters
   */
  @NonNull
  Map<String, ?> getParameters();
}
