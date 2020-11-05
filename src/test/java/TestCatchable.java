import me.googas.commons.cache.thread.Catchable;
import me.googas.commons.time.Time;
import me.googas.commons.time.Unit;
import org.jetbrains.annotations.NotNull;

public class TestCatchable extends Catchable {

    @NotNull
    private final String string;

    public TestCatchable(String string) {
        super(new Time(1, Unit.SECONDS), false);
        this.string = string;
        this.addToCache();
    }

    public String getString() {
        return this.string;
    }

    /**
     * When seconds have passed inside the cache this method will be called
     */
    @Override
    public void onSecondPassed() {
    }

    /**
     * When the seconds left is less than 0 and the object will be removed this will be called
     */
    @Override
    public void onRemove() {
        System.out.println(this + " has been unloaded");

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestCatchable)) return false;

        TestCatchable that = (TestCatchable) o;

        return this.string.equals(that.string);
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Cache object finalized: " + this);
    }
}
