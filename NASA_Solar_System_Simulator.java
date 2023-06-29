import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SolarSystemSimulator extends JPanel {
    private static final int SCREEN_WIDTH = 1850;
    private static final int SCREEN_HEIGHT = 1850;
    private static final int SUN_RADIUS = 50;

    // Speed up
    private static final CelestialBody[] PLANETS = {
        new CelestialBody("Mercury", 10, 100, 0.2, Color.GRAY),
        new CelestialBody("Venus", 15, 150, 0.15, Color.ORANGE),
        new CelestialBody("Earth", 20, 200, 0.1, Color.BLUE),
        new CelestialBody("Mars", 17, 250, 0.08, Color.RED),
        new CelestialBody("Jupiter", 40, 350, 0.05, Color.YELLOW),
        new CelestialBody("Saturn", 35, 450, 0.04, new Color(210, 180, 140)),
        new CelestialBody("Uranus", 30, 550, 0.03, Color.CYAN),
        new CelestialBody("Neptune", 30, 650, 0.02, Color.BLUE.darker()),
        new CelestialBody("Pluto", 8, 750, 0.01, new Color(160, 82, 45))
    };

    private static class CelestialBody {
        private String name;
        private int radius;
        private int orbitRadius;
        private double orbitSpeed;
        private Color color;
        private double angle;

        public CelestialBody(String name, int radius, int orbitRadius, double orbitSpeed, Color color) {
            this.name = name;
            this.radius = radius;
            this.orbitRadius = orbitRadius;
            this.orbitSpeed = orbitSpeed;
            this.color = color;
            this.angle = 0;
        }

        public void update(double dt) {
            angle += orbitSpeed * dt;
        }

        public double getX() {
            return SCREEN_WIDTH / 2 + Math.cos(angle) * orbitRadius;
        }

        public double getY() {
            return SCREEN_HEIGHT / 2 + Math.sin(angle) * orbitRadius;
        }

        public void draw(Graphics2D g2d) {
            double x = getX();
            double y = getY();

            // Draw planet
            g2d.setColor(color);
            g2d.fillOval((int) (x - radius), (int) (y - radius), radius * 2, radius * 2);

            // Draw planet name
            g2d.setColor(Color.WHITE);
            Font font = new Font("Arial", Font.PLAIN, 12);
            g2d.setFont(font);
            g2d.drawString(name, (int) x - radius, (int) y - radius - 20);

            // Draw orbit circle
            g2d.setColor(color);
            g2d.drawOval((int) (SCREEN_WIDTH / 2 - orbitRadius), (int) (SCREEN_HEIGHT / 2 - orbitRadius),
                    orbitRadius * 2, orbitRadius * 2);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Clear the screen
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        CelestialBody sun = new CelestialBody("Sun", SUN_RADIUS, 0, 0, Color.YELLOW);

        for (CelestialBody planet : PLANETS) {
            planet.update(0.016);
            planet.draw(g2d);
        }

        sun.draw(g2d);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Solar System Simulator");
        SolarSystemSimulator panel = new SolarSystemSimulator();
        frame.add(panel);
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
