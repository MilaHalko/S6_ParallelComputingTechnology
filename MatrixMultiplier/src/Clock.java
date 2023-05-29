import java.util.ArrayList;

public class Clock {
    static private long startTime;
    static private final ArrayList<Long> averageTimes = new ArrayList<>();
    static private int times = 0;

    static void start() {
        times++;
        startTime = System.currentTimeMillis();
    }

    static void stop() {
        long stopTime = System.currentTimeMillis();
        if (averageTimes.size() > 0) {
            averageTimes.set(averageTimes.size() - 1, averageTimes.get(averageTimes.size() - 1) + (stopTime - startTime));
        }
    }

    static void averageOn() {
        averageTimes.add(0L);
    }

    static void averageOff() {
        averageTimes.clear();
        times = 0;
    }

    static Long getAverage() {
        return averageTimes.get(averageTimes.size() - 1) / times;
    }
}
