import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int iterations = 100000;
        Counter counter = new Counter();
        List<Thread> threads = List.of(
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < iterations; i++) {
                            counter.increment();
//                            counter.incrementSync();
//                            counter.incrementSyncBlock();
//                            counter.incrementLock();
                        }
                    }
                }),
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < iterations; i++) {
                            counter.decrement();
//                            counter.decrementSync();
//                            counter.decrementSyncBlock();
//                            counter.decrementLock();
                        }
                    }
                })
        );

        for (Thread thread : threads) thread.start();
        for (Thread thread : threads) thread.join();
        System.out.print(counter.getCount());
    }
}

