import java.util.Random;

public class Producer extends Thread {
    public static int value = 1;
    private Drop drop;

    public Producer(Drop drop) {
        this.drop = drop;
    }

    @Override
    public void run() {
        while (true) {
            drop.put(value++);
        }
    }
}