import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import me.googas.commons.builder.LogBuilder;

public class ToStringBuildTest {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    ConsoleHandler consoleHandler = new ConsoleHandler();
    consoleHandler.setFormatter(new SimpleFormatter());
    Logger logger = Logger.getLogger("Test");
    logger.setUseParentHandlers(false);
    logger.addHandler(consoleHandler);
    while (true) {
      if (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (line.equalsIgnoreCase("exit")) {
          System.exit(0);
        } else if (line.equalsIgnoreCase("fibo")) {
          LogBuilder trace = new LogBuilder(Level.INFO, "Printing Fibonacci: \n");
          int x = 0;
          int y = 1;
          int z = 0;
          while (z < 50) {
            z = x + y;
            x = y;
            y = z;
            trace.append("Number: ").append(z).append(", \n");
          }
          trace.send(logger);
        }
      }
    }
  }
}
