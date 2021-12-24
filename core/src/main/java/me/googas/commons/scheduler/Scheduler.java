package me.googas.commons.scheduler;

import java.util.Collection;
import java.util.function.Consumer;
import lombok.NonNull;
import me.googas.commons.RandomUtils;
import me.googas.commons.time.Time;
import me.googas.commons.time.Unit;

/** An scheduler is an object used to create and manage {@link Task} */
public interface Scheduler {

  /**
   * Get a task by its id
   *
   * @param id the id of the task
   * @return the task if the id matches else null
   */
  default Task getTask(int id) {
    for (Task task : this.getTasks()) {
      if (task.getId() == id) return task;
    }
    return null;
  }

  /**
   * Create a countdown
   *
   * @param repetition how often should the countdown repeat. This means that how ofter should the
   *     countdown decrease its time. For example: In a {@link SimpleCountdown} which has a seconds
   *     left method must run every second
   * @param countdown the countdown to schedule
   * @return the scheduled countdown
   */
  @NonNull
  Countdown countdown(@NonNull Time repetition, @NonNull Countdown countdown);

  /**
   * Create a countdown
   *
   * @param time the time for the countdown to finish
   * @param onSecond a consumer that runs every second that passes and has the time left as the
   *     parameter
   * @param finish the method to run when the countdown is over
   * @return the scheduled countdown
   */
  @NonNull
  default Countdown countdown(@NonNull Time time, Consumer<Time> onSecond, Runnable finish) {
    return this.countdown(
        new Time(1, Unit.SECONDS),
        new SimpleCountdown(
            time,
            this.nextId(),
            onSecond == null ? second -> {} : onSecond,
            finish == null ? () -> {} : finish));
  }

  /**
   * Run a method later
   *
   * @param in how long until we run this method
   * @param later the run later task to run
   * @return the run later task
   */
  @NonNull
  RunLater later(@NonNull Time in, @NonNull RunLater later);

  /**
   * Run a method later
   *
   * @param in how long until we run this method
   * @param later the run later task to run
   * @return a simple run later task
   */
  @NonNull
  default RunLater later(@NonNull Time in, @NonNull Runnable later) {
    return this.later(in, new SimpleRunLater(this.nextId(), later));
  }

  /**
   * Create a repetitive task
   *
   * @param initial the initial time until the task repeats
   * @param period the period in which the task will repeat
   * @param repetitive the repetitive task to run
   * @return the repetitive task
   */
  @NonNull
  Repetitive repeat(@NonNull Time initial, @NonNull Time period, @NonNull Repetitive repetitive);

  /**
   * Create a repetitive task
   *
   * @param initial the initial time until the task repeats
   * @param period the period in which the task will repeat
   * @param repetitive the repetitive task to run
   * @return a simple repetitive task
   */
  @NonNull
  default Repetitive repeat(
      @NonNull Time initial, @NonNull Time period, @NonNull Runnable repetitive) {
    return this.repeat(initial, period, new SimpleRepetitive(this.nextId(), repetitive));
  }

  /**
   * Get a new id for a task
   *
   * @return the new id
   */
  default int nextId() {
    int id = RandomUtils.getRandom().nextInt();
    if (this.getTask(id) == null) return id;
    return this.nextId();
  }

  /**
   * Get all the tasks that are running in the scheduler
   *
   * @return the tasks running in the scheduler
   */
  @NonNull
  Collection<Task> getTasks();
}
