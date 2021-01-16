package me.googas.samples.events;

import me.googas.commons.events.Event;

public class SampleEvent implements Event {

  private final String string;

  public SampleEvent(String string) {
    this.string = string;
  }

  public String getString() {
    return this.string;
  }
}
