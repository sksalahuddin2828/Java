import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.SurfaceChart;
import javafx.scene.chart.XYZChart;
import javafx.stage.Stage;

public class GaussianDistribution3DVisualization extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Define the parameters for the Gaussian distribution
        double meanX = 0.0;
        double meanY = 0.0;
        double sigmaX = 1.0;
        double sigmaY = 1.0;
        double covarianceXY = 0.5;

        // Create a meshgrid for the 2D plot
        int numPoints = 100;
        double[] x = new double[numPoints];
        double[] y = new double[numPoints];
        double[][] z = new double[numPoints][numPoints];

        double step = 10.0 / (numPoints - 1);
        for (int i = 0; i < numPoints; i++) {
            x[i] = -5.0 + i * step;
            y[i] = -5.0 + i * step;
            for (int j = 0; j < numPoints; j++) {
                double xVal = x[i];
                double yVal = y[j];
                z[i][j] = gaussianProbabilityDensity(xVal, yVal, meanX, meanY, sigmaX, sigmaY, covarianceXY);
            }
        }

        // Create the 3D surface plot
        NumberAxis xAxis = new NumberAxis(-5, 5, step);
        NumberAxis yAxis = new NumberAxis(-5, 5, step);
        SurfaceChart<Number, Number, Number> surfaceChart = new SurfaceChart<>(xAxis, yAxis);
        surfaceChart.setTitle("2D Gaussian Distribution");

        // Add the data to the chart
        XYZChart.Series<Number, Number, Number> series = new XYZChart.Series<>();
        for (int i = 0; i < numPoints; i++) {
            for (int j = 0; j < numPoints; j++) {
                series.getData().add(new XYZChart.Data<>(x[i], y[j], z[i][j]));
            }
        }
        surfaceChart.getData().add(series);

        // Create and show the scene
        Scene scene = new Scene(new Group(surfaceChart), 800, 600);
        primaryStage.setTitle("2D Gaussian Distribution 3D Visualization");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Gaussian probability density function
    private double gaussianProbabilityDensity(double x, double y, double meanX, double meanY, double sigmaX, double sigmaY, double covarianceXY) {
        double xTerm = Math.pow(x - meanX, 2) / (2 * Math.pow(sigmaX, 2));
        double yTerm = Math.pow(y - meanY, 2) / (2 * Math.pow(sigmaY, 2));
        double xyTerm = (x - meanX) * (y - meanY) * covarianceXY / (sigmaX * sigmaY);
        double exponent = -(xTerm + yTerm - xyTerm);
        double coefficient = 1 / (2 * Math.PI * sigmaX * sigmaY * Math.sqrt(1 - Math.pow(covarianceXY, 2)));
        return coefficient * Math.exp(exponent);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
