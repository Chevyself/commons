package me.googas.samples;

import java.util.Timer;
import me.googas.commons.scheduler.TimerScheduler;
import me.googas.commons.time.Time;
import me.googas.commons.time.Unit;

public class TimeTest {

  public static void main(String[] args) {
    Time time = Time.fromMillis(31557600000L);
    System.out.println("time.toEffectiveString() = " + time.toEffectiveString());
    System.out.println("time.toHhMmSs() = " + time.toHhMmSs());
    TimerScheduler scheduler = new TimerScheduler(new Timer());
    scheduler.countdown(
        new Time(30, Unit.MINUTES),
        second -> {
          System.out.println("second.toHhMmSs() = " + second.toHhMmSs());
        },
        () -> {});
  }
}
