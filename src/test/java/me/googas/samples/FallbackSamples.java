package me.googas.samples;

import me.googas.commons.fallback.SimpleFallback;

public class FallbackSamples {
  public static void main(String[] args) {
    SimpleFallback fallback = new SimpleFallback();
    try {
      Integer.parseInt("Obvious error");
    } catch (NumberFormatException e) {
      fallback.process(e);
    }
    System.out.println(fallback.getErrors());
  }
}
