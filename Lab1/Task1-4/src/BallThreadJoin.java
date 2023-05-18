public class BallThreadJoin extends Thread {

    private final BallThread th1;
    private final BallThread th2;

    public BallThreadJoin(BallThread th1, BallThread th2) {
        this.th1 = th1;
        this.th2 = th2;
    }

    public void run () {
        th1.start();
        try {
            th1.join();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        };
        th2.start();
    }
}
