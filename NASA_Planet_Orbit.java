import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

class CelestialBody {
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

    public Point2D.Double getPosition() {
        double x = SolarSystem.SCREEN_WIDTH / 2 + Math.cos(angle) * orbitRadius;
        double y = SolarSystem.SCREEN_HEIGHT / 2 + Math.sin(angle) * orbitRadius;
        return new Point2D.Double(x, y);
    }

    public void draw(Graphics2D g2d) {
        Point2D.Double position = getPosition();
        double x = position.getX();
        double y = position.getY();

        g2d.setColor(color);

        // Draw planet
        Ellipse2D.Double planet = new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2);
        g2d.fill(planet);

        // Draw planet name
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);
        g2d.drawString(name, (float) x - radius, (float) y - radius - 5);

        // Draw orbit circle
        Ellipse2D.Double orbit = new Ellipse2D.Double(
                SolarSystem.SCREEN_WIDTH / 2 - orbitRadius,
                SolarSystem.SCREEN_HEIGHT / 2 - orbitRadius,
                orbitRadius * 2, orbitRadius * 2);
        g2d.setColor(color);
        g2d.draw(orbit);
    }
}

class SolarSystem extends JPanel {
    public static final int SCREEN_WIDTH = 1800;
    public static final int SCREEN_HEIGHT = 1800;
    private static final int SUN_RADIUS = 50;
    private static final int UPDATE_INTERVAL = 16; // milliseconds

    private CelestialBody sun;
    private List<CelestialBody> planets;

    public SolarSystem() {
        sun = new CelestialBody("Sun", SUN_RADIUS, 0, 0, Color.YELLOW);

        planets = new ArrayList<>();
        planets.add(new CelestialBody("Mercury", 10, 100, 0.02, Color.GRAY));
        planets.add(new CelestialBody("Venus", 15, 150, 0.015, Color.ORANGE));
        planets.add(new CelestialBody("Earth", 20, 200, 0.01, Color.BLUE));
        planets.add(new CelestialBody("Mars", 17, 250, 0.008, Color.RED));
        planets.add(new CelestialBody("Jupiter", 40, 350, 0.005, Color.YELLOW));
        planets.add(new CelestialBody("Saturn", 35, 450, 0.004, new Color(210, 180, 140)));
        planets.add(new CelestialBody("Uranus", 30, 550, 0.003, Color.CYAN));
        planets.add(new CelestialBody("Neptune", 30, 650, 0.002, new Color(0, 0, 139)));
        planets.add(new CelestialBody("Pluto", 8, 750, 0.001, new Color(165, 42, 42)));

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new UpdateTask(), 0, UPDATE_INTERVAL);
    }

    private class UpdateTask extends TimerTask {
        @Override
        public void run() {
            double dt = UPDATE_INTERVAL / 1000.0;

            for (CelestialBody planet : planets) {
                planet.update(dt);
            }

            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        for (CelestialBody planet : planets) {
            planet.draw(g2d);
        }

        sun.draw(g2d);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Solar System Simulator");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        SolarSystem solarSystem = new SolarSystem();
        frame.add(solarSystem);
        frame.setVisible(true);
    }
}
