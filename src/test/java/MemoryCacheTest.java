import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import lombok.NonNull;
import me.googas.commons.cache.MemoryCache;

public class MemoryCacheTest {

  @NonNull private static final Timer timer = new Timer();

  @NonNull private static final MemoryCache memory = new MemoryCache();

  public static void main(String[] args) {
    MemoryCacheTest.timer.schedule(
        new TimerTask() {
          @Override
          public void run() {
            MemoryCacheTest.memory.run();
          }
        },
        0,
        1000);
    Scanner scanner = new Scanner(System.in);
    while (true) {
      if (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (line.equalsIgnoreCase("print")) {
          MemoryCacheTest.memory
              .getMap()
              .forEach(
                  (refence, seconds) -> {
                    System.out.println(refence.get() + "= " + seconds);
                  });
        } else if (line.startsWith("get-")) {
          System.out.println(
              MemoryCacheTest.memory.get(
                  ReferencedCatchable.class,
                  referenced -> referenced.getString().equalsIgnoreCase(line.substring(4))));
        } else {
          try {
            MemoryCacheTest.memory.add(new ReferencedCatchable(line));
          } catch (IllegalStateException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }
}
