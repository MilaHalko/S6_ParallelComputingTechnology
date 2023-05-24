import java.util.Random;

public class Consumer extends Thread {
    private Drop drop;

    public Consumer(Drop drop) {
        this.drop = drop;
    }

    @Override
    public void run() {
        while (true) {
            int data = drop.take();
        }
    }
}