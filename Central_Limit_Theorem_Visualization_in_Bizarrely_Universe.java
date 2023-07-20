import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CLTVisualization extends Application {
    private static final int NUM_GALAXIES = 1000;
    private static final int NUM_DIMENSIONS = 3;
    private static final int NUM_SAMPLES = 100;
    private static final int SAMPLE_SIZE = 10;

    private Random random = new Random();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("CLT Bizarrely Universe");

        // Step 1: Generate the Bizarrely Universe data (3D scatter plot)
        List<double[]> galaxies = generateBizarrelyUniverseData(NUM_GALAXIES, NUM_DIMENSIONS);
        show3DScatterPlot(galaxies);

        // Step 2: Apply the Central Limit Theorem and create the animation
        List<List<Double>> sampleMeans = applyCentralLimitTheorem(NUM_GALAXIES, NUM_DIMENSIONS, NUM_SAMPLES, SAMPLE_SIZE);
        createCLTAnimation(sampleMeans);

        primaryStage.show();
    }

    // Step 1: Generate the Bizarrely Universe data (3D scatter plot)
    private List<double[]> generateBizarrelyUniverseData(int numGalaxies, int numDimensions) {
        List<double[]> galaxies = new ArrayList<>();
        for (int i = 0; i < numGalaxies; i++) {
            double[] galaxy = new double[numDimensions];
            for (int j = 0; j < numDimensions; j++) {
                galaxy[j] = random.nextGaussian();
            }
            galaxies.add(galaxy);
        }
        return galaxies;
    }

    // Step 1: Show the 3D scatter plot
    private void show3DScatterPlot(List<double[]> galaxies) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        NumberAxis zAxis = new NumberAxis();
        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
        scatterChart.setTitle("Bizarrely Universe Visualization");

        for (double[] galaxy : galaxies) {
            XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(galaxy[0], galaxy[1]);
            scatterChart.getData().add(new XYChart.Series<>(dataPoint));
        }

        StackPane root = new StackPane(scatterChart);
        Scene scene = new Scene(root, 800, 600);
        Stage stage = new Stage();
        stage.setTitle("Bizarrely Universe Visualization");
        stage.setScene(scene);
        stage.show();
    }

    // Step 2: Apply the Central Limit Theorem and collect sample means
    private List<List<Double>> applyCentralLimitTheorem(int numGalaxies, int numDimensions, int numSamples, int sampleSize) {
        List<List<Double>> sampleMeans = new ArrayList<>();
        for (int i = 0; i < numSamples; i++) {
            List<double[]> sample = new ArrayList<>();
            for (int j = 0; j < sampleSize; j++) {
                int randomGalaxyIndex = random.nextInt(numGalaxies);
                sample.add(generateBizarrelyUniverseData(numGalaxies, numDimensions).get(randomGalaxyIndex));
            }
            double sampleMean = calculateSampleMean(sample);
            sampleMeans.add(List.of((double) i, sampleMean));
        }
        return sampleMeans;
    }

    // Step 2: Calculate the sample mean
    private double calculateSampleMean(List<double[]> sample) {
        double sum = 0.0;
        for (double[] data : sample) {
            sum += data[0]; // Assuming we are interested in the first dimension's value
        }
        return sum / sample.size();
    }

    // Step 2: Create the Central Limit Theorem animation
    private void createCLTAnimation(List<List<Double>> sampleMeans) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis(0, 1, 0.1);
        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
        scatterChart.setTitle("Central Limit Theorem Animation");

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        scatterChart.getData().add(series);

        StackPane root = new StackPane(scatterChart);
        Scene scene = new Scene(root, 800, 600);
        Stage stage = new Stage();
        stage.setTitle("Central Limit Theorem Animation");
        stage.setScene(scene);
        stage.show();

        for (List<Double> sample : sampleMeans) {
            double xValue = sample.get(0);
            double yValue = sample.get(1);
            XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(xValue, yValue);
            series.getData().add(dataPoint);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
