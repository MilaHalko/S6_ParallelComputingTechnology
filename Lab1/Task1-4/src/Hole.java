import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Hole {
    private int x;
    private int y;
    private Component canvas;
    private int size;
    private boolean isHoleEnabled = true;

    public Hole(Component c, int x, int y, int size) {
        this.canvas = c;
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.blue);
        g2.fill(new Ellipse2D.Double(x, y, size, size));
    }

    public boolean checkBall(Ball b) {
        if (!isHoleEnabled) {
            return false;
        }
        int ballCenterX = b.getX() + b.getRadius();
        int ballCenterY = b.getY() + b.getRadius();
        int holeCenterX = x + size / 2;
        int holeCenterY = y + size / 2;
        int distance = (int) Math.sqrt(Math.pow(ballCenterX - holeCenterX, 2) + Math.pow(ballCenterY - holeCenterY, 2));
        return distance <= size / 2 + b.getRadius();
    }
    public void switchEnabled() {isHoleEnabled = !isHoleEnabled;}
    public boolean isEnabled() {return isHoleEnabled;}
}
