import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class ComplexDistributionVisualization extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Define the data range for the X-axis
        double startX = -5.0;
        double endX = 30.0;
        int numPoints = 500;

        // Generate data points for visualization
        double[] xValues = new double[numPoints];
        double[] yValues = new double[numPoints];
        double step = (endX - startX) / (numPoints - 1);
        for (int i = 0; i < numPoints; i++) {
            xValues[i] = startX + i * step;
            yValues[i] = complexDistribution(xValues[i]);
        }

        // Create the line chart
        NumberAxis xAxis = new NumberAxis(startX, endX, step);
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Complex Distribution");
        lineChart.setCreateSymbols(false); // Remove data points symbols

        // Add the complex distribution data to the chart
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        for (int i = 0; i < numPoints; i++) {
            series.getData().add(new XYChart.Data<>(xValues[i], yValues[i]));
        }
        lineChart.getData().add(series);

        // Create and show the scene
        Scene scene = new Scene(lineChart, 800, 600);
        primaryStage.setTitle("Complex Distribution Visualization");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private double complexDistribution(double x) {
        // Define a mixture of three Gaussian distributions
        double mu1 = 5, sigma1 = 1;
        double mu2 = 12, sigma2 = 2;
        double mu3 = 20, sigma3 = 3;

        // Calculate the PDF of each Gaussian distribution
        double pdf1 = Math.exp(-0.5 * Math.pow((x - mu1) / sigma1, 2)) / (sigma1 * Math.sqrt(2 * Math.PI));
        double pdf2 = Math.exp(-0.5 * Math.pow((x - mu2) / sigma2, 2)) / (sigma2 * Math.sqrt(2 * Math.PI));
        double pdf3 = Math.exp(-0.5 * Math.pow((x - mu3) / sigma3, 2)) / (sigma3 * Math.sqrt(2 * Math.PI));

        // Combine the three PDFs to create a complex distribution
        // You can adjust the weights to change the contribution of each Gaussian
        return 0.4 * pdf1 + 0.3 * pdf2 + 0.3 * pdf3;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
