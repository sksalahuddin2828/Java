import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class RotatingPatternAnimation extends Application {
    private static final int WIDTH = 1020;
    private static final int HEIGHT = 1020;
    private static final int BALL_RADIUS = 15;
    private static final int CIRCLE_RADIUS = 480;
    private static final int LINE_COUNT = 24;

    private double angle = 0.0;

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT, Color.BLACK);

        Circle baseCircle = new Circle(WIDTH / 2, HEIGHT / 2, CIRCLE_RADIUS, Color.BLACK);
        root.getChildren().add(baseCircle);

        for (int i = 0; i < LINE_COUNT; i++) {
            double angle = (i / (double) LINE_COUNT) * 2 * Math.PI;
            double x = WIDTH / 2 + CIRCLE_RADIUS * Math.cos(angle);
            double y = HEIGHT / 2 + CIRCLE_RADIUS * Math.sin(angle);
            Line line = new Line(WIDTH / 2, HEIGHT / 2, x, y);
            line.setStroke(Color.WHITE);
            root.getChildren().add(line);
        }

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                root.getChildren().removeIf(node -> node instanceof Circle);

                double circle = Math.PI * 2;
                int length = 12;
                int halfLength = length / 2;
                double cx = 510;
                double cy = 510;
                double w = 450;
                double h = 450;

                for (int i = 0; i < length; i++) {
                    double a = (i / (double) length) * circle;
                    double x = cx + Math.round(Math.cos(a) * w);
                    double y = cy + Math.round(Math.sin(a) * h);
                    Circle ball = new Circle(x, y, BALL_RADIUS, Color.WHITE);
                    root.getChildren().add(ball);

                    if (i >= halfLength) {
                        double innerAngle = a + angle;
                        double rangeVal = Math.cos(innerAngle);
                        x = cx + Math.round(Math.cos(a) * (w - 1) * rangeVal);
                        y = cy + Math.round(Math.sin(a) * (h - 1) * rangeVal);
                        ball = new Circle(x, y, BALL_RADIUS, Color.WHITE);
                        root.getChildren().add(ball);
                    }
                }

                angle += 0.01;
            }
        };
        animationTimer.start();

        primaryStage.setTitle("Rotating Pattern");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
