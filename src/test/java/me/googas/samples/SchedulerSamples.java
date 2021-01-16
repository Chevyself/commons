package me.googas.samples;

import java.util.Timer;
import me.googas.commons.scheduler.Scheduler;
import me.googas.commons.scheduler.TimerScheduler;
import me.googas.commons.time.Time;
import me.googas.commons.time.Unit;

public class SchedulerSamples {
  public static void main(String[] args) {
    Scheduler scheduler = new TimerScheduler(new Timer());
    Time aSecond = new Time(1, Unit.SECONDS);
    scheduler.repeat(aSecond, aSecond, () -> System.out.println("I will print every second!"));
    scheduler.later(aSecond, () -> System.out.println("I will run after a second has passed!"));
    scheduler.countdown(
        new Time(30, Unit.SECONDS),
        (left) -> System.out.printf("I will run for the next %s \n", left.toEffectiveString()),
        () -> System.out.println("I will run when the countdown is finished"));
  }
}
