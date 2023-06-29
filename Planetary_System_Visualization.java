import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CelestialBodySimulator extends JPanel {
    private static final int SCREEN_WIDTH = 2160;
    private static final int SCREEN_HEIGHT = 2160;
    private static final int SUN_RADIUS = 50;

    // Planet data: name, radius, orbit radius, orbit speed, color
    private static final Object[][] PLANETS = {
        {"Mercury", 10, 100, 0.02, new Color(128, 128, 128)},
        {"Venus", 15, 150, 0.015, new Color(255, 165, 0)},
        {"Earth", 20, 200, 0.01, new Color(0, 0, 255)},
        {"Mars", 17, 250, 0.008, new Color(255, 0, 0)},
        {"Jupiter", 40, 350, 0.005, new Color(255, 215, 0)},
        {"Saturn", 35, 450, 0.004, new Color(210, 180, 140)},
        {"Uranus", 30, 550, 0.003, new Color(0, 255, 255)},
        {"Neptune", 30, 650, 0.002, new Color(0, 0, 139)},
        {"Pluto", 8, 750, 0.001, new Color(165, 42, 42)}
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

        public int[] getPosition() {
            int x = SCREEN_WIDTH / 2 + (int) (Math.cos(angle) * orbitRadius);
            int y = SCREEN_HEIGHT / 2 + (int) (Math.sin(angle) * orbitRadius);
            return new int[] {x, y};
        }

        public double calculateVolume() {
            double volume = (4.0 / 3.0) * Math.PI * Math.pow(radius, 3);
            return volume;
        }

        public double calculateSurfaceArea() {
            double surfaceArea = 4.0 * Math.PI * Math.pow(radius, 2);
            return surfaceArea;
        }

        public double calculateOrbitalVelocity() {
            if (orbitSpeed == 0) {
                return Double.POSITIVE_INFINITY;
            }
            double orbitalVelocity = 2.0 * Math.PI * orbitRadius / orbitSpeed;
            return orbitalVelocity;
        }

        public void draw(Graphics2D g2d) {
            int[] position = getPosition();
            int x = position[0];
            int y = position[1];

            // Display the name
            g2d.setFont(new Font("Arial", Font.PLAIN, 16));
            g2d.setColor(Color.WHITE);
            g2d.drawString(name, x, y + radius + 20);

            // Perform scientific calculations for each planet
            double volume = calculateVolume();
            double surfaceArea = calculateSurfaceArea();
            double orbitalVelocity = calculateOrbitalVelocity();

            // Display the calculations
            g2d.setFont(new Font("Arial", Font.PLAIN, 14));
            g2d.drawString("Volume: " + String.format("%.2f", volume), x, y + radius + 40);
            g2d.drawString("Surface Area: " + String.format("%.2f", surfaceArea), x, y + radius + 55);
            g2d.drawString("Orbital Velocity: " + String.format("%.2f", orbitalVelocity), x, y + radius + 70);

            // Draw the body
            g2d.setColor(color);
            g2d.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        }
    }

    private CelestialBody sun;
    private CelestialBody[] planets;

    public CelestialBodySimulator() {
        sun = new CelestialBody("Sun", SUN_RADIUS, 0, 0, new Color(255, 255, 0));

        planets = new CelestialBody[PLANETS.length];
        for (int i = 0; i < PLANETS.length; i++) {
            Object[] planetData = PLANETS[i];
            String name = (String) planetData[0];
            int radius = (int) planetData[1];
            int orbitRadius = (int) planetData[2];
            double orbitSpeed = (double) planetData[3];
            Color color = (Color) planetData[4];
            planets[i] = new CelestialBody(name, radius, orbitRadius, orbitSpeed, color);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        for (CelestialBody planet : planets) {
            planet.update(1.0 / 60.0); // Assuming 60 FPS
            planet.draw(g2d);
        }

        sun.draw(g2d);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Planetary System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.setResizable(false);

        CelestialBodySimulator simulator = new CelestialBodySimulator();
        frame.setContentPane(simulator);

        frame.setVisible(true);

        while (true) {
            simulator.repaint();
            try {
                Thread.sleep(16); // Delay for 60 FPS (approximately)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
