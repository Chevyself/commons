package me.googas.starbox.gameplay.match;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.googas.annotations.Nullable;
import me.googas.commons.scheduler.Countdown;
import me.googas.commons.scheduler.Repetitive;
import me.googas.commons.scheduler.RunLater;
import me.googas.commons.scheduler.Scheduler;
import me.googas.commons.scheduler.Task;
import me.googas.commons.time.Time;
import me.googas.commons.time.Unit;
import me.googas.starbox.Starbox;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MatchScheduler implements Scheduler, Runnable {

  /** A map with the task and the total ticks to run */
  @NonNull private final Map<Task, Long> map = new HashMap<>();
  /** This map contains the ticks for a Repetitive task to start another loop */
  @NonNull private final Map<Task, Long> restartMap = new HashMap<>();

  @NonNull private final Map<MatchState, Long> total = new HashMap<>();
  @Nullable @Getter @Setter private Match match;
  @Nullable private BukkitTask task;

  public void start() {
    this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(Starbox.getPlugin(), this, 1, 1);
  }

  public void stop() {
    if (this.task != null) this.task.cancel();
  }

  @NonNull
  public Time getTotal(@NonNull MatchState state) {
    return new Time(this.total.getOrDefault(state, 0L), Unit.MINECRAFT_TICKS);
  }

  @NonNull
  private Set<Task> copy() {
    return new HashSet<>(this.map.keySet());
  }

  @Override
  public void run() {
    for (Task registeredTask : this.copy()) {
      if (registeredTask.isCancelled()) {
        this.map.remove(registeredTask);
        this.restartMap.remove(registeredTask);
        continue;
      }
      if (!registeredTask.isPaused()) {
        this.map.put(registeredTask, this.map.get(registeredTask) - 1);
      } else {
        continue;
      }
      System.out.println("this.map.get(registeredTask) = " + this.map.get(registeredTask));
      if (this.map.get(registeredTask) < 0) {
        Starbox.getScheduler().sync(registeredTask);
        if (registeredTask instanceof RunLater) {
          registeredTask.cancel();
          this.map.remove(registeredTask);
        } else if (registeredTask instanceof Repetitive) {
          this.map.put(registeredTask, this.restartMap.getOrDefault(registeredTask, 20L));
        }
      }
    }
    if (this.match != null) {
      this.total.put(this.match.getState(), this.total.getOrDefault(this.match.getState(), 0L) + 1);
    }
  }

  @Override
  public @NonNull Countdown countdown(@NonNull Time time, @NonNull Countdown countdown) {
    return (Countdown) this.repeat(time, time, countdown);
  }

  @Override
  public @NonNull RunLater later(@NonNull Time time, @NonNull RunLater runLater) {
    this.map.put(runLater, time.getValue(Unit.MINECRAFT_TICKS));
    return runLater;
  }

  @Override
  public @NonNull Repetitive repeat(
      @NonNull Time time, @NonNull Time time1, @NonNull Repetitive repetitive) {
    this.map.put(repetitive, time.getValue(Unit.MINECRAFT_TICKS));
    this.restartMap.put(repetitive, time1.getValue(Unit.MINECRAFT_TICKS));
    return repetitive;
  }

  @Override
  public @NonNull Collection<Task> getTasks() {
    return new HashSet<>(this.map.keySet());
  }
}
