import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class RotatingPatternViewer extends Application {
    private static final int WIDTH = 240;
    private static final int HEIGHT = 240;
    private static final double PI = Math.PI;
    private static final double CIRCLE = PI * 2;
    private static final int LENGTH = 12;
    private static final int HALF_LENGTH = LENGTH / 2;
    private static final double CX = 115;
    private static final double CY = 115;
    private static final double W = 90.0;
    private static final double H = 90.0;

    private double step = 0.0;

    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        AnimationTimer timer = new AnimationTimer() {
            public void handle(long now) {
                step -= 0.09;
                drawPattern(gc);
            }
        };
        timer.start();

        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setTitle("Rotating Pattern");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void drawPattern(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        for (int i = 0; i < LENGTH; i++) {
            double a = (i / (double) LENGTH) * CIRCLE;
            double x = CX + Math.round(Math.cos(a) * W);
            double y = CY + Math.round(Math.sin(a) * H);
            drawCircle(gc, x, y, Color.WHITE);

            if (i < HALF_LENGTH) {
                continue;
            }

            double rangeVal = Math.cos(a + step);
            x = CX + Math.round(Math.cos(a) * (W - 1) * rangeVal);
            y = CY + Math.round(Math.sin(a) * (H - 1) * rangeVal);
            drawCircle(gc, x, y, Color.WHITE);
        }
    }

    private void drawCircle(GraphicsContext gc, double x, double y, Color color) {
        gc.setFill(color);
        gc.fillOval(x - 5, y - 5, 10, 10);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
