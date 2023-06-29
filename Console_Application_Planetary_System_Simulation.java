import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

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

    public double getX() {
        return Main.SCREEN_WIDTH / 2 + Math.cos(angle) * orbitRadius;
    }

    public double getY() {
        return Main.SCREEN_HEIGHT / 2 + Math.sin(angle) * orbitRadius;
    }

    public double getVolume() {
        return (4.0 / 3.0) * Math.PI * Math.pow(radius, 3);
    }

    public double getSurfaceArea() {
        return 4.0 * Math.PI * Math.pow(radius, 2);
    }

    public double getOrbitalVelocity() {
        if (orbitSpeed == 0) {
            return Double.POSITIVE_INFINITY;
        }
        return 2.0 * Math.PI * orbitRadius / orbitSpeed;
    }

    public void draw(Graphics2D g2) {
        double x = getX();
        double y = getY();

        // Display the name
        Font font = new Font("Arial", Font.PLAIN, 16);
        g2.setFont(font);
        g2.setColor(Color.WHITE);
        g2.drawString(name, (int) x, (int) y + radius + 20);

        // Perform scientific calculations for each planet
        double volume = getVolume();
        double surfaceArea = getSurfaceArea();
        double orbitalVelocity = getOrbitalVelocity();

        // Display the calculations on console
        System.out.println("Planet: " + name);
        System.out.printf("Volume: %.2f%n", volume);
        System.out.printf("Surface Area: %.2f%n", surfaceArea);
        System.out.printf("Orbital Velocity: %.2f%n", orbitalVelocity);
        System.out.println("-------------------");

        // Draw the body
        Ellipse2D.Double planetShape = new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2);
        g2.setColor(color);
        g2.fill(planetShape);
    }
}

class DrawingPanel extends JPanel {
    private ArrayList<CelestialBody> planets;
    private CelestialBody sun;

    public DrawingPanel(ArrayList<CelestialBody> planets, CelestialBody sun) {
        this.planets = planets;
        this.sun = sun;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (CelestialBody planet : planets) {
            planet.update(1.0 / 50.0);
            planet.draw(g2);
        }

        sun.draw(g2);
    }
}

public class Main extends JFrame {
    public static final int SCREEN_WIDTH = 1850;
    public static final int SCREEN_HEIGHT = 1850;
    public static final int SUN_RADIUS = 50;

    public static final Object[][] PLANETS = {
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

    public Main() {
        setTitle("Planetary System");
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);

        ArrayList<CelestialBody> planets = new ArrayList<>();
        for (Object[] planetData : PLANETS) {
            String name = (String) planetData[0];
            int radius = (int) planetData[1];
            int orbitRadius = (int) planetData[2];
            double orbitSpeed = (double) planetData[3];
            Color color = (Color) planetData[4];
            CelestialBody planet = new CelestialBody(name, radius, orbitRadius, orbitSpeed, color);
            planets.add(planet);
        }

        CelestialBody sun = new CelestialBody("Sun", SUN_RADIUS, 0, 0, new Color(255, 255, 0));
        DrawingPanel panel = new DrawingPanel(planets, sun);
        add(panel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.setVisible(true);
    }
}
