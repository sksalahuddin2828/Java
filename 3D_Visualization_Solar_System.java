import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CelestialBodies extends Application {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private Group root;

    @Override
    public void start(Stage primaryStage) {
        root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT, Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Celestial Bodies");
        primaryStage.show();

        createBodies();
    }

    private void createBodies() {
        String[] bodyNames = {"Sun", "Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune"};
        double[] xCoordinates = {0, 50, 70, 100, 150, 220, 280, 350, 400};
        double[] yCoordinates = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        double[] zCoordinates = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        double[] sizes = {20, 5, 7, 7, 6, 18, 15, 12, 12};

        for (int i = 0; i < bodyNames.length; i++) {
            double radius = sizes[i];
            Sphere body = new Sphere(radius);
            body.setTranslateX(xCoordinates[i]);
            body.setTranslateY(yCoordinates[i]);
            body.setTranslateZ(zCoordinates[i]);
            body.setMaterial(new PhongMaterial(Color.WHITE));
            root.getChildren().add(body);

            Text label = new Text(bodyNames[i]);
            label.setTranslateX(xCoordinates[i] - radius);
            label.setTranslateY(HEIGHT - 20);
            label.setFill(Color.WHITE);
            label.setFont(Font.font("Arial", 12));
            root.getChildren().add(label);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
