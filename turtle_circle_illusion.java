import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class CircleDance extends JPanel {
    private static final long serialVersionUID = 1L;
    private int population;
    private int resolution;
    private int loops;
    private int flip;
    private int lines;
    private int radius;
    private List<Turtle> turtles;
    private Random random;

    public CircleDance(int population, int resolution, int loops, int flip, int lines) {
        this.population = population;
        this.resolution = resolution;
        this.loops = loops;
        this.flip = flip;
        this.lines = lines;
        this.radius = 250;
        this.turtles = new ArrayList<>();
        this.random = new Random();

        if (this.lines == 1) {
            arrangeLines();
        }

        for (int i = 0; i < this.population; i++) {
            Turtle dancer = new Turtle(i);
            makeDancer(dancer, i, this.population);
            this.turtles.add(dancer);
        }
    }

    private void arrangeLines() {
        Graphics g = this.getGraphics();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);

        for (int n = 0; n < this.population; n++) {
            int x = this.getWidth() / 2;
            int y = this.getHeight() / 2;
            int angle = (int) (n / (double) this.population * 180);

            int startX = x + (int) (Math.cos(Math.toRadians(angle)) * this.radius);
            int startY = y + (int) (Math.sin(Math.toRadians(angle)) * this.radius);
            int endX = x - (int) (Math.cos(Math.toRadians(angle)) * this.radius);
            int endY = y - (int) (Math.sin(Math.toRadians(angle)) * this.radius);

            g2d.drawLine(startX, startY, endX, endY);
        }
    }

    private void makeDancer(Turtle dancer, int i, int population) {
        dancer.setHeading(i / (double) population * 180);
        dancer.setColor(randomTurtleColor());
        dancer.penUp();
        dancer.setShape("turtle");
        dancer.setTurtleSize(2);
    }

    private Color randomTurtleColor() {
        float red = random.nextFloat() * 0.9f;
        float green = 0.5f + random.nextFloat() * 0.5f;
        float blue = random.nextFloat() * 0.7f;
        return new Color(red, green, blue);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Turtle dancer : turtles) {
            dancer.draw(g2d);
        }
    }

    public void animate() {
        while (true) {
            for (int step = 0; step < this.resolution; step++) {
                double phase = step / (double) this.resolution * 2 * Math.PI;
                drawDancers(phase);
                repaint();

                try {
                    Thread.sleep(4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void drawDancers(double phase) {
        int population = turtles.size();

        for (int i = 0; i < population; i++) {
            double individualPhase = (phase + i / (double) population * this.loops * Math.PI) % (2 * Math.PI);
            Turtle dancer = turtles.get(i);

            if (flip == 1) {
                if (Math.PI / 2 < individualPhase && individualPhase <= 3 * Math.PI / 2) {
                    dancer.setTiltAngle(180);
                } else {
                    dancer.setTiltAngle(0);
                }
            }

            double distance = this.radius * Math.sin(individualPhase);
            dancer.setXY(0, 0);
            dancer.forward(distance);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                int population = Integer.parseInt(args[0]);
                int resolution = Integer.parseInt(args[1]);
                int loops = Integer.parseInt(args[2]);
                int flip = Integer.parseInt(args[3]);
                int lines = Integer.parseInt(args[4]);

                CircleDance circleDance = new CircleDance(population, resolution, loops, flip, lines);
                JFrame frame = new JFrame();
                frame.add(circleDance);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600, 600);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                circleDance.animate();
            }
        });
    }
}

class Turtle {
    private int id;
    private double heading;
    private Color color;
    private boolean penUp;
    private String shape;
    private double turtleSize;
    private int tiltAngle;
    private double x;
    private double y;

    public Turtle(int id) {
        this.id = id;
        this.heading = 0.0;
        this.color = Color.BLACK;
        this.penUp = true;
        this.shape = "turtle";
        this.turtleSize = 1.0;
        this.tiltAngle = 0;
        this.x = 0.0;
        this.y = 0.0;
    }

    public void setHeading(double heading) {
        this.heading = heading;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void penUp() {
        this.penUp = true;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public void setTurtleSize(double turtleSize) {
        this.turtleSize = turtleSize;
    }

    public void setTiltAngle(int tiltAngle) {
        this.tiltAngle = tiltAngle;
    }

    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void forward(double distance) {
        this.x += distance * Math.cos(Math.toRadians(this.heading));
        this.y += distance * Math.sin(Math.toRadians(this.heading));
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.translate(this.x, this.y);
        g2d.rotate(Math.toRadians(90 - this.heading));
        g2d.rotate(Math.toRadians(this.tiltAngle));

        int size = (int) (this.turtleSize * 20);
        int halfSize = size / 2;
        int[] xPoints = { -halfSize, 0, halfSize };
        int[] yPoints = { -halfSize, halfSize, -halfSize };

        g2d.fillPolygon(xPoints, yPoints, 3);
        g2d.rotate(Math.toRadians(-this.tiltAngle));
        g2d.rotate(Math.toRadians(this.heading - 90));
        g2d.translate(-this.x, -this.y);
    }
}
