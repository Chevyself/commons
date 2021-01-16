package me.googas.samples.cache;

import me.googas.commons.cache.MemoryCache;
import me.googas.commons.scheduler.TimerScheduler;
import me.googas.commons.time.Time;
import me.googas.commons.time.Unit;

import java.util.Timer;

/**
 * Sample class
 */
public class MemoryCacheSample {

    public static void main(String[] args) {
        MemoryCache cache = new MemoryCache();
        // Cache must be registered inside a task (It implements runnable) in which you can make it check
        // if the catchable can be removed in the time you want .
        // For this example, we will use the Scheduler framework.
        TimerScheduler scheduler = new TimerScheduler(new Timer());
        Time time = new Time(1, Unit.SECONDS);
        scheduler.repeat(time, time, cache);
    }
}
