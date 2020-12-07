package me.googas.commons.scheduler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Delegate;
import me.googas.commons.time.Time;

/** An scheduler using {@link java.util.Timer} */
public class TimerScheduler implements Scheduler {

  /** The timer to use in the scheduler */
  @NonNull @Getter @Delegate private final Timer timer;

  /** The list of tasks running by this scheduler */
  @NonNull private final List<Task> tasks = new ArrayList<>();

  /**
   * Create the timer scheduler
   *
   * @param timer the timer to use
   */
  public TimerScheduler(@NonNull Timer timer) {
    this.timer = timer;
  }

  /** Create the scheduler with a new timer instance */
  public TimerScheduler() {
    this(new Timer());
  }

  @Override
  @NonNull
  public Countdown countdown(@NonNull Time repetition, @NonNull Countdown countdown) {
    this.schedule(
        new TimerTask() {
          @Override
          public void run() {
            if (countdown.isCancelled()) {
              this.cancel();
              TimerScheduler.this.tasks.remove(countdown);
              return;
            }
            countdown.run();
          }
        },
        repetition.millis(),
        repetition.millis());
    this.tasks.add(countdown);
    return countdown;
  }

  @Override
  public @NonNull RunLater later(@NonNull Time in, @NonNull RunLater later) {
    this.schedule(
        new TimerTask() {
          @Override
          public void run() {
            if (later.isCancelled()) {
              this.cancel();
              TimerScheduler.this.tasks.remove(later);
              return;
            }
            later.run();
          }
        },
        in.millis());
    this.tasks.add(later);
    return later;
  }

  @Override
  public @NonNull Collection<Task> getTasks() {
    return this.tasks;
  }
}
