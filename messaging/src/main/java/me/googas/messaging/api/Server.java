package me.googas.messaging.api;

import lombok.NonNull;
import me.googas.messaging.Request;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;

/** This object represents the server {@link Messenger} connects to */
public interface Server {
  /**
   * Closes the server
   *
   * @throws IOException some objects when closed can cause this exception
   */
  void close() throws IOException;

  /**
   * Whether clients need authentication to use
   *
   * @return whether the server requires the client to be authenticated
   */
  boolean requiresAuthentication();

  /** Makes the server start listening */
  void start();

  /**
   * Send a request and accept the consumer for each client
   *
   * @param request the request to send
   * @param consumer the consumer to accept
   * @param <T> the type of object requested
   */
  <T> void sendRequest(@NonNull Request<T> request, BiConsumer<Messenger, Optional<T>> consumer);

  /**
   * Send a request and get the response for each client
   *
   * @param request the request to send
   * @param <T> the type of object request
   * @return the map of clients and its response
   */
  @NonNull
  <T> Map<Messenger, T> sendRequest(@NonNull Request<T> request);

  /**
   * Set whether clients must be authenticated
   *
   * @param bol the new value whether clients need authentication
   */
  void setRequiresAuthentication(boolean bol);

  /**
   * Get the clients that are connected to the server
   *
   * @return the set of clients connected to the server
   */
  @NonNull
  Set<? extends Messenger> getClients();
}
