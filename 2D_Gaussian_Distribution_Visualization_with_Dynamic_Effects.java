import org.apache.commons.math3.distribution.MultivariateNormalDistribution;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularMatrixException;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.ColorPicker;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class GaussianDistributionVisualization extends Application {
    private static final int NUM_POINTS = 100;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final double MEAN_X = 0.0;
    private static final double MEAN_Y = 0.0;
    private static final double STD_X = 1.0;
    private static final double STD_Y = 1.0;
    private static final double COV_XY = 0.5;
    private static final double ANIMATION_SPEED = 0.1;

    private MultivariateNormalDistribution gaussianDistribution;
    private ScatterChart<Number, Number> chart;
    private XYChart.Series<Number, Number> series;
    private double[][] points;

    @Override
    public void start(Stage primaryStage) {
        gaussianDistribution = new MultivariateNormalDistribution(new double[]{MEAN_X, MEAN_Y},
                new double[][]{{STD_X * STD_X, COV_XY * STD_X * STD_Y},
                        {COV_XY * STD_X * STD_Y, STD_Y * STD_Y}});

        NumberAxis xAxis = new NumberAxis(-5, 5, 1);
        NumberAxis yAxis = new NumberAxis(-5, 5, 1);
        chart = new ScatterChart<>(xAxis, yAxis);
        chart.setAnimated(false);
        chart.setPrefSize(WIDTH, HEIGHT);
        series = new XYChart.Series<>();

        points = new double[NUM_POINTS][2];
        for (int i = 0; i < NUM_POINTS; i++) {
            double[] sample = gaussianDistribution.sample();
            points[i][0] = sample[0];
            points[i][1] = sample[1];
            series.getData().add(new XYChart.Data<>(sample[0], sample[1]));
        }

        chart.getData().add(series);

        HBox root = new HBox(chart, createColorPicker());

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("2D Gaussian Distribution Visualization");
        primaryStage.show();

        // Start the animation timer
        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 100_000_000L) {
                    update();
                    lastUpdate = now;
                }
            }
        };
        timer.start();
    }

    private void update() {
        try {
            double timeParam = Math.sin(System.currentTimeMillis() * ANIMATION_SPEED * 1e-9);
            for (int i = 0; i < NUM_POINTS; i++) {
                double scaledX = points[i][0] * timeParam;
                double scaledY = points[i][1] * timeParam;
                series.getData().get(i).setXValue(scaledX);
                series.getData().get(i).setYValue(scaledY);
            }
        } catch (SingularMatrixException ignored) {
            // Handle singular matrix exception if needed
        }
    }

    private ColorPicker createColorPicker() {
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setOnAction(e -> chart.setStyle("-fx-background-color: " + toRGBCode(colorPicker.getValue())));
        return colorPicker;
    }

    private static String toRGBCode(javafx.scene.paint.Color color) {
        return String.format("#%02X%02X%02X", (int) (color.getRed() * 255), (int) (color.getGreen() * 255), (int) (color.getBlue() * 255));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
