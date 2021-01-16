package me.googas.samples;

import java.util.Timer;
import lombok.NonNull;
import me.googas.commons.cache.Catchable;
import me.googas.commons.cache.MemoryCache;
import me.googas.commons.scheduler.TimerScheduler;
import me.googas.commons.time.Time;
import me.googas.commons.time.Unit;

/** Sample class */
public class MemoryCacheSample {

  public void cache() {
    MemoryCache cache = new MemoryCache();
    // Cache must be registered inside a task (It implements runnable) in which you can make it
    // check
    // if the catchable can be removed in the time you want .
    // For this example, we will use the Scheduler framework.
    TimerScheduler scheduler = new TimerScheduler(new Timer());
    Time time = new Time(1, Unit.SECONDS);
    scheduler.repeat(time, time, cache);
  }

  public static class ACatchable implements Catchable {

    @Override
    public void onRemove() {
      System.out.println("This has been unloaded");
    }

    @Override
    public @NonNull Time getToRemove() {
      // This object will be removed from the cache it is on in 3 minutes of querying it
      return new Time(3, Unit.MINUTES);
    }
  }
}
