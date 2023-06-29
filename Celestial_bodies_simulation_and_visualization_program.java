import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class CelestialBodies extends Application {
    private static final int SCREEN_WIDTH = 1900;
    private static final int SCREEN_HEIGHT = 1900;

    private static final int SUN_RADIUS = 50;

    private static final CelestialBody[] PLANETS = {
        new CelestialBody("Mercury", 10, 100, 0.02, Color.rgb(128, 128, 128)),
        new CelestialBody("Venus", 15, 150, 0.015, Color.rgb(255, 165, 0)),
        new CelestialBody("Earth", 20, 200, 0.01, Color.rgb(0, 0, 255)),
        new CelestialBody("Mars", 17, 250, 0.008, Color.rgb(255, 0, 0)),
        new CelestialBody("Jupiter", 40, 350, 0.005, Color.rgb(255, 215, 0)),
        new CelestialBody("Saturn", 35, 450, 0.004, Color.rgb(210, 180, 140)),
        new CelestialBody("Uranus", 30, 550, 0.003, Color.rgb(0, 255, 255)),
        new CelestialBody("Neptune", 30, 650, 0.002, Color.rgb(0, 0, 139)),
        new CelestialBody("Pluto", 8, 750, 0.001, Color.rgb(165, 42, 42))
    };

    private Group root;

    @Override
    public void start(Stage primaryStage) {
        root = new Group();
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT, Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Celestial Bodies");
        primaryStage.show();

        createCelestialBodies();
    }

    private void createCelestialBodies() {
        Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font("Arial", 16));

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.clearRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

                for (CelestialBody planet : PLANETS) {
                    planet.update();
                    drawCelestialBody(gc, planet);
                }

                drawCelestialBody(gc, SUN);
            }
        };

        animationTimer.start();
    }

    private void drawCelestialBody(GraphicsContext gc, CelestialBody body) {
        double x = body.getX();
        double y = body.getY();

        // Perform scientific calculations for each planet
        double volume = body.calculateVolume();
        double surfaceArea = body.calculateSurfaceArea();
        double orbitalVelocity = body.calculateOrbitalVelocity();

        // Print calculations to console
        System.out.println("Scientific Calculations:");
        System.out.println("Planet: " + body.getName());
        System.out.println("Volume: " + String.format("%.2f", volume));
        System.out.println("Surface Area: " + String.format("%.2f", surfaceArea));
        System.out.println("Orbital Velocity: " + String.format("%.2f", orbitalVelocity));
        System.out.println("------------------------");

        // Render the calculations as text on the screen
        gc.setFill(Color.WHITE);
        gc.fillText("Volume: " + String.format("%.2f", volume), x, y + body.getRadius() + 20);
        gc.fillText("Surface Area: " + String.format("%.2f", surfaceArea), x, y + body.getRadius() + 40);
        gc.fillText("Orbital Velocity: " + String.format("%.2f", orbitalVelocity), x, y + body.getRadius() + 60);

        // Draw the body
        gc.setFill(body.getColor());
        gc.fillOval(x - body.getRadius(), y - body.getRadius(), body.getRadius() * 2, body.getRadius() * 2);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class CelestialBody {
    private String name;
    private double radius;
    private double orbitRadius;
    private double orbitSpeed;
    private Color color;
    private double angle;

    public CelestialBody(String name, double radius, double orbitRadius, double orbitSpeed, Color color) {
        this.name = name;
        this.radius = radius;
        this.orbitRadius = orbitRadius;
        this.orbitSpeed = orbitSpeed;
        this.color = color;
        this.angle = 0;
    }

    public void update() {
        angle += orbitSpeed;
    }

    public double getX() {
        return SCREEN_WIDTH / 2 + Math.cos(angle) * orbitRadius;
    }

    public double getY() {
        return SCREEN_HEIGHT / 2 + Math.sin(angle) * orbitRadius;
    }

    public double getRadius() {
        return radius;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public double calculateVolume() {
        double volume = (4 / 3) * Math.PI * Math.pow(radius, 3);
        return volume;
    }

    public double calculateSurfaceArea() {
        double surfaceArea = 4 * Math.PI * Math.pow(radius, 2);
        return surfaceArea;
    }

    public double calculateOrbitalVelocity() {
        if (orbitSpeed == 0) {
            return Double.POSITIVE_INFINITY;
        }
        double orbitalVelocity = 2 * Math.PI * orbitRadius / orbitSpeed;
        return orbitalVelocity;
    }
}
