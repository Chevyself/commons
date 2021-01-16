package me.googas.samples;

import java.util.logging.Logger;
import me.googas.commons.builder.LogBuilder;
import me.googas.commons.builder.ToStringBuilder;
import me.googas.commons.log.LoggerFactory;

public class BuilderSample {

  public void test() {
    // Logger creating using our own LoggerFactory
    Logger logger = LoggerFactory.start("Test-Logger");
    LogBuilder builder = new LogBuilder("Starting log...");
    builder.append("\n").append(new Foo("Hello world!", 25));
    builder.send(logger);
  }

  public static class Foo {

    private final String string;
    private final int integer;

    public Foo(String string, int integer) {
      this.string = string;
      this.integer = integer;
    }

    @Override
    public String toString() {
      return new ToStringBuilder(this)
          .append("string", this.string)
          .append("integer", this.integer)
          .build();
    }
  }
}
