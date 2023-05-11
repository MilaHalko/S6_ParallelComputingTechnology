import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Canvas extends JPanel {
    private BounceFrame frame;
    private ArrayList<Ball> balls = new ArrayList<>();
    private ArrayList<Hole> holes = new ArrayList<>();
    private int ballsInHoles = 0;
    public Canvas(BounceFrame frame) {
        this.frame = frame;
    }

    public void add(Ball b) {
        this.balls.add(b);
    }

    public void add(Hole h) {
        this.holes.add(h);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
            for (Hole hole : holes) {
                if (hole.isEnabled()) {
                    hole.draw(g2);
                }
            }
        for (Ball ball : balls) {
            ball.draw(g2);
        }
    }

    public List<Hole> getHoles() {
        return holes;
    }

    public int getBallsInHoles() {
        return ballsInHoles;
    }

    public void switchHolesEnabled() {
        for (Hole hole : holes) {
            hole.switchEnabled();
        }
    }

    public synchronized void remove(Ball b) {
        balls.remove(b);
        ballsInHoles++;
        frame.setLabelBallsInHoles();
    }
}