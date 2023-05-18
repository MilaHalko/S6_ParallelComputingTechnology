import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class BounceFrame extends JFrame {
    public static final int WIDTH = 450;
    public static final int HEIGHT = 350;
    private Canvas canvas;
    private JLabel label;
    private int ballsInHoles = 0;

    public BounceFrame() {
        int lowPriorityBallsCount = 1000;
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce program");
        this.canvas = new Canvas(this);
        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);
        System.out.println("In Frame Thread name = "
                + Thread.currentThread().getName());

        JButton buttonStart = new JButton("Start");
        JButton buttonHolesSwitch = new JButton("Holes");
        JButton buttonColorBalls = new JButton("Priority");
        JButton buttonJoin = new JButton("Join");
        JButton buttonStop = new JButton("Stop");

        label = new JLabel();
        setLabelBallsInHoles();

        Hole h1 = new Hole(canvas, 300, 100, 20);
        Hole h2 = new Hole(canvas, 200, 200, 20);
        canvas.add(h1);
        canvas.add(h2);

        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ball b = new Ball(canvas);
                canvas.add(b);
                BallThread thread = new BallThread(b);
                thread.start();
                System.out.println("Thread name = " +
                        thread.getName());
            }
        });
        buttonHolesSwitch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.switchHolesEnabled();
                canvas.repaint();
            }
        });
        buttonColorBalls.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int x = new Random().nextInt(canvas.getWidth());
                int y = new Random().nextInt(canvas.getWidth());

                Ball b = new Ball(canvas, x, y, Color.red);
                canvas.add(b);
                BallThread thread = new BallThread(b);
                thread.setPriority(Thread.MAX_PRIORITY);
                thread.start();

                for (int i = 0; i < lowPriorityBallsCount; i++) {
                    b = new Ball(canvas, x, y, Color.blue);
                    canvas.add(b);
                    thread = new BallThread(b);
                    thread.setPriority(Thread.MIN_PRIORITY);
                    thread.start();
                }
            }
        });

        buttonJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ball b1 = new Ball(canvas);
                Ball b2 = new Ball(canvas);
                canvas.add(b1);
                canvas.add(b2);
                BallThreadJoin joinThread = new BallThreadJoin (new BallThread(b1), new BallThread(b2));
                joinThread.start();
            }
        });
        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonHolesSwitch);
        buttonPanel.add(buttonColorBalls);
        buttonPanel.add(buttonJoin);
        buttonPanel.add(buttonStop);
        buttonPanel.add(label);
        content.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setLabelBallsInHoles() {
        label.setText("Balls in holes: " + canvas.getBallsInHoles());
    }
}