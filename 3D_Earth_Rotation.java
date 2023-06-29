import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class EarthRotation extends Application {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.setCenter(canvas);

        Group group = new Group(root);
        Scene scene = new Scene(group, WIDTH, HEIGHT, Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.setTitle("3D Earth Rotation");
        primaryStage.show();

        GraphicsContext gc = canvas.getGraphicsContext2D();
        EarthMap earthMap = new EarthMap(gc);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                earthMap.update();
                earthMap.draw();
            }
        };
        timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class EarthMap {
    private GraphicsContext gc;
    private double lon = 0;

    public EarthMap(GraphicsContext gc) {
        this.gc = gc;
    }

    public void update() {
        lon += 2;
        if (lon >= 360) {
            lon = 0;
        }
    }

    public void draw() {
        gc.clearRect(0, 0, WIDTH, HEIGHT);

        gc.setStroke(Color.GRAY);
        gc.setLineWidth(1);
        gc.strokeRect(0, 0, WIDTH, HEIGHT);

        gc.save();
        gc.translate(WIDTH / 2.0, HEIGHT / 2.0);
        gc.rotate(-lon);

        gc.drawImage(/* Load and draw the Blue Marble image */);

        gc.restore();
    }
}
