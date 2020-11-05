import me.googas.commons.cache.thread.Cache;

import java.lang.ref.SoftReference;
import java.util.Scanner;

public class CacheTest {

    public static void main(String[] args) {
        TestCatchable testCatchable = new TestCatchable("1");
        SoftReference<TestCatchable> reference = new SoftReference<>(testCatchable);
        new TestCatchable("2");
        new TestCatchable("3");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                if (s.equalsIgnoreCase("exit")) {
                    System.exit(0);
                } else if (s.equalsIgnoreCase("print")) {
                    System.out.println(Cache.getCache());
                } else if (s.equalsIgnoreCase("gc")) {
                    System.gc();
                } else if (s.equalsIgnoreCase("reference")) {
                    System.out.println(reference.get());
                } else if (s.equalsIgnoreCase("remove-reference")) {
                    reference.clear();
                }
            }
        }
    }
}
