package me.googas.commons.scheduler;

import me.googas.commons.time.Time;
import me.googas.commons.time.Unit;
import org.junit.Test;

public class TimerSchedulerTest {

  @Test
  public void timerCountdown() {
    TimerScheduler scheduler = new TimerScheduler();
    Countdown countdown =
        scheduler.countdown(
            new Time(1, Unit.SECONDS),
            new SimpleCountdown(
                new Time(10, Unit.SECONDS),
                10,
                time -> System.out.println(time.toEffectiveString() + " left to 0"),
                () -> {
                  System.out.println("Countdown ended!");
                  System.exit(0);
                }));
  }
}
