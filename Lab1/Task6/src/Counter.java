import java.util.concurrent.locks.ReentrantLock;

public class Counter {
    static int count = 0;
    private ReentrantLock locker = new ReentrantLock();
    public Counter() {
    }

    public void increment() {
        count++;
    }

    public synchronized void incrementSync() {
        count++;
    }

    public void incrementSyncBlock() {
        synchronized (this) {
            count++;
        }
    }

    public void incrementLock() {
        try {
            locker.lock();
            count++;
        } finally {
            locker.unlock();
        }
    }

    public void decrement() {
        count--;
    }

    public synchronized void decrementSync() {
        count--;
    }

    public void decrementSyncBlock() {
        synchronized (this) {
            count--;
        }
    }

    public void decrementLock() {
        try {
            locker.lock();
            count--;
        } finally {
            locker.unlock();
        }
    }

    public int getCount() {return count;}
}
