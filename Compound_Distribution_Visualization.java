import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class CompoundDistributionVisualization extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Define the data range for the X-axis
        double startX = 0.0;
        double endX = 20.0;
        int numPoints = 500;

        // Generate data points for visualization
        double[] xValues = new double[numPoints];
        double[] yValues = new double[numPoints];
        double step = (endX - startX) / (numPoints - 1);
        for (int i = 0; i < numPoints; i++) {
            xValues[i] = startX + i * step;
            yValues[i] = compoundDistribution(xValues[i]);
        }

        // Create the line chart
        NumberAxis xAxis = new NumberAxis(startX, endX, step);
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Compound Distribution");
        lineChart.setCreateSymbols(false); // Remove data points symbols

        // Add the compound distribution data to the chart
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        for (int i = 0; i < numPoints; i++) {
            series.getData().add(new XYChart.Data<>(xValues[i], yValues[i]));
        }
        lineChart.getData().add(series);

        // Create and show the scene
        Scene scene = new Scene(lineChart, 800, 600);
        primaryStage.setTitle("Compound Distribution Visualization");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private double compoundDistribution(double x) {
        // Define a Gaussian distribution
        double gaussianMean = 10, gaussianStd = 2;
        double gaussian = Math.exp(-0.5 * Math.pow((x - gaussianMean) / gaussianStd, 2)) / (gaussianStd * Math.sqrt(2 * Math.PI));

        // Define an Exponential distribution
        double exponentialLambda = 0.2;
        double exponential = exponentialLambda * Math.exp(-exponentialLambda * x);

        // Define a Uniform distribution
        double uniformLow = 5, uniformHigh = 15;
        double uniform = (x >= uniformLow && x <= uniformHigh) ? 1 / (uniformHigh - uniformLow) : 0;

        // Combine the distributions to create a compound distribution
        // You can adjust the weights to change the contribution of each distribution
        return 0.6 * gaussian + 0.2 * exponential + 0.2 * uniform;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
