import me.googas.commons.cache.Catchable;
import me.googas.commons.time.Time;
import org.jetbrains.annotations.NotNull;

public class ReferencedCatchable implements Catchable {

    @NotNull
    private final String string;

    public ReferencedCatchable(@NotNull String string) {
        this.string = string;
    }

    @NotNull
    public String getString() {
        return this.string;
    }

    @Override
    public void onRemove() {
        System.out.println(this + " has been unloaded");
    }

    /**
     * Get the time for the object to be removed from cache
     *
     * @return the time to be removed
     */
    @Override
    public @NotNull Time getToRemove() {
        return Time.fromString("2m");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReferencedCatchable)) return false;

        ReferencedCatchable that = (ReferencedCatchable) o;

        return this.string.equals(that.string);
    }

    @Override
    public int hashCode() {
        return this.string.hashCode();
    }

    @Override
    public String toString() {
        return "ReferencedCatchable{" +
                "string='" + this.string + '\'' +
                '}';
    }
}
