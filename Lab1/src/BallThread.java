import java.util.List;

public class BallThread extends Thread {
    private Ball b;
    private List<Hole> holes;

    public BallThread(Ball ball) {
        b = ball;
        holes = b.getCanvas().getHoles();
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i < 10000; i++) {
                b.move();
                for (Hole hole : holes) {
                    if (hole.checkBall(b)) {
                        b.getCanvas().remove(b);
                        b.getCanvas().repaint();
                        return;
                    }
                }
                System.out.println("Thread name = "
                        + Thread.currentThread().getName());
                Thread.sleep(5);
            }
        } catch (InterruptedException ex) {
        }
    }
}
