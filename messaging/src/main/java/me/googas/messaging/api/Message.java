package me.googas.messaging.api;

import lombok.NonNull;

import java.util.UUID;

/** A message send between two messengers */
public interface Message {

  /**
   * Get the unique id of the message
   *
   * @return the unique id of the message
   */
  @NonNull
  UUID getId();
}
