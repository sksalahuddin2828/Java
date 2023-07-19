import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class DataVisualization extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Generate random 7D data with specific patterns
        int numSamples = 100;
        double[][] data7d = new double[numSamples][7];
        for (int i = 0; i < numSamples; i++) {
            for (int j = 0; j < 7; j++) {
                data7d[i][j] = Math.random();
            }
        }

        // Perform clustering on the data
        int numClusters = 3;
        KMeans kmeans = new KMeans(numClusters);
        int[] dataLabels = kmeans.fit(data7d);

        // Create a scatter chart
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        NumberAxis zAxis = new NumberAxis();
        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
        scatterChart.setTitle("7D Data Visualization");
        scatterChart.setAnimated(false);

        // Add data points to the scatter chart
        for (int i = 0; i < numSamples; i++) {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>(data7d[i][0], data7d[i][1]));
            scatterChart.getData().add(series);
        }

        // Create a scene and add the scatter chart
        Scene scene = new Scene(scatterChart, 800, 800);

        // Show the JavaFX window
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
