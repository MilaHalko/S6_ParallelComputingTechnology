import java.util.ArrayList;

public class Clock {
    static private long startTime;
    static private long stopTime;
    static private final ArrayList<Long> averageTimes = new ArrayList<>();
    static private int times = 0;

    static void start() {
        times++;
        startTime = System.currentTimeMillis();
    }

    static void stop() {
        stopTime = System.currentTimeMillis();
        if (averageTimes.size() > 0) {
            averageTimes.set(averageTimes.size() - 1, averageTimes.get(averageTimes.size() - 1) + (stopTime - startTime));
        }
    }
    static void stop(String message) {
        stop();
        if (message != null) {
            System.out.println(message + ": " + (stopTime - startTime));
        }
    }

    static void averageOn() {
        averageTimes.add(0L);
    }

    static void averageOff(String message) {
        System.out.println(message + "Average time: " + averageTimes.get(averageTimes.size() - 1) / times);
        times = 0;
    }
}
