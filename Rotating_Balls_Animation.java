import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class CircularMotionAnimation extends JPanel implements ActionListener {
    private static final int INTERVAL_TIME = 40;
    private static final int BALLS = 8;
    private static final int MAX_STEP = 64;

    private int step = 0;
    private long startTime;

    public CircularMotionAnimation() {
        setPreferredSize(new Dimension(800, 600));

        Timer timer = new Timer(INTERVAL_TIME, this);
        timer.start();

        startTime = System.currentTimeMillis();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        createBase(g2d);

        long elapsedTime = System.currentTimeMillis() - startTime;
        step = (int) ((elapsedTime * 1000 / INTERVAL_TIME) % MAX_STEP);
        double centerX = getWidth() / 2 + 115 * Math.cos(step * 2.0 / MAX_STEP * Math.PI);
        double centerY = getHeight() / 2 + 115 * Math.sin(step * 2.0 / MAX_STEP * Math.PI);

        for (int i = 0; i < BALLS; i++) {
            drawCircle(g2d, centerX + 115 * Math.cos((i * 2.0 / BALLS - step * 2.0 / MAX_STEP) * Math.PI),
                    centerY + 115 * Math.sin((i * 2.0 / BALLS - step * 2.0 / MAX_STEP) * Math.PI), 10, Color.WHITE);
        }
    }

    private void createBase(Graphics2D g2d) {
        drawCircle(g2d, getWidth() / 2, getHeight() / 2, 240, Color.BLACK);
        for (int i = 0; i < BALLS * 2; i++) {
            drawLine(g2d, getWidth() / 2, getHeight() / 2,
                    getWidth() / 2 + 240 * Math.cos(i * 1.0 / BALLS * Math.PI),
                    getHeight() / 2 + 240 * Math.sin(i * 1.0 / BALLS * Math.PI), Color.WHITE);
        }
    }

    private void drawLine(Graphics2D g2d, double x1, double y1, double x2, double y2, Color color) {
        g2d.setColor(color);
        g2d.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
    }

    private void drawCircle(Graphics2D g2d, double x, double y, double r, Color color) {
        g2d.setColor(color);
        g2d.fillOval((int) (x - r), (int) (y - r), (int) (2 * r), (int) (2 * r));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Circular Motion Animation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new CircularMotionAnimation());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
