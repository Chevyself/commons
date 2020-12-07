package me.googas.commons.scheduler;

import java.util.function.Consumer;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.googas.commons.time.Time;
import me.googas.commons.time.Unit;

public class SimpleCountdown implements Countdown {

  @NonNull private final int id;
  @NonNull @Getter @Setter private Time time;
  @Setter private long secondsLeft;
  @NonNull @Getter @Setter private Consumer<Time> onSecondPassed;

  @NonNull @Getter @Setter private Runnable onFinish;

  private boolean cancelled = false;

  @Setter private boolean paused = false;

  public SimpleCountdown(
      @NonNull Time time,
      @NonNull int id,
      @NonNull Consumer<Time> onSecondPassed,
      @NonNull Runnable onFinish) {
    this.time = time;
    this.id = id;
    this.onSecondPassed = onSecondPassed;
    this.onFinish = onFinish;
    this.refresh();
  }

  @Override
  public void run() {
    if (this.paused || this.cancelled) return;
    this.secondsLeft--;
    if (this.secondsLeft > 0) {
      this.onSecondPassed.accept(this.getTimeLeft());
    } else {
      this.onFinish.run();
      this.cancel();
    }
  }

  @Override
  public int getId() {
    return this.id;
  }

  @Override
  public boolean cancel() {
    if (this.cancelled) return false;
    this.cancelled = true;
    return true;
  }

  @Override
  public boolean pause() {
    if (this.cancelled || this.paused) return false;
    this.paused = true;
    return true;
  }

  @Override
  public boolean isCancelled() {
    return this.cancelled;
  }

  @Override
  public boolean isPaused() {
    return this.paused;
  }

  @Override
  @NonNull
  public SimpleCountdown refresh() {
    this.secondsLeft = this.time.getValue(Unit.SECONDS);
    return this;
  }

  @Override
  public long getMillisLeft() {
    return this.secondsLeft * 1000;
  }
}
