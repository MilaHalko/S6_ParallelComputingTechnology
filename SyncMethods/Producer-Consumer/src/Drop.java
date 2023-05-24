public class Drop {
    private int[] data;
    private int pointerOnEmpty = 0;

    public Drop(int size) {
        data = new int[size];
    }

    public synchronized int take() {
        while (pointerOnEmpty == 0) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        int value = data[pointerOnEmpty-1];
        pointerOnEmpty--;
        notifyAll();

        System.out.format("POINTER: %-4s DATA RECEIVED: %s%n", pointerOnEmpty, value);
        return value;
    }

    public synchronized void put(int value) {
        while (pointerOnEmpty >= data.length) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        data[pointerOnEmpty++] = value;
        notifyAll();
    }
}