import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SolarSystem extends Application {
    private static final double AU_SCALE = 50;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private Group root;
    private Sphere sun;
    private Sphere[] planets;
    private Text[] labels;

    @Override
    public void start(Stage primaryStage) {
        root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT, Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Solar System");
        primaryStage.show();

        createSun();
        createPlanets();
        createLabels();

        root.getChildren().addAll(sun);
        root.getChildren().addAll(planets);
        root.getChildren().addAll(labels);
    }

    private void createSun() {
        sun = new Sphere(30);
        sun.setTranslateX(WIDTH / 2);
        sun.setTranslateY(HEIGHT / 2);
        sun.setTranslateZ(0);
        sun.setMaterial(new PhongMaterial(Color.YELLOW));
    }

    private void createPlanets() {
        String[] planetNames = {"Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune"};
        double[] planetDistances = {0.39, 0.72, 1.0, 1.52, 5.20, 9.58, 19.18, 30.07};
        planets = new Sphere[planetNames.length];

        for (int i = 0; i < planetNames.length; i++) {
            double radius = 5;
            Sphere planet = new Sphere(radius);
            planet.setTranslateX(WIDTH / 2 + planetDistances[i] * AU_SCALE);
            planet.setTranslateY(HEIGHT / 2);
            planet.setTranslateZ(0);
            planet.setMaterial(new PhongMaterial(Color.BLUE));
            planets[i] = planet;
        }
    }

    private void createLabels() {
        String[] planetNames = {"Sun", "Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune"};
        labels = new Text[planetNames.length];

        for (int i = 0; i < planetNames.length; i++) {
            Text label = new Text(planetNames[i]);
            label.setTranslateX(WIDTH / 2 + 30 + i * 80);
            label.setTranslateY(HEIGHT - 20);
            label.setFill(Color.WHITE);
            label.setFont(Font.font("Arial", 12));
            labels[i] = label;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
