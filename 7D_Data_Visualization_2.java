import org.knowm.xchart.*;
import org.knowm.xchart.style.markers.*;

import java.awt.*;
import java.util.*;

public class Main {

  public static void main(String[] args) {
    int numSamples = 100;
    double[][] data7D = new double[numSamples][7];
    Random random = new Random(42);

    // Generate random 7D data with specific patterns
    for (int i = 0; i < numSamples; i++) {
      for (int j = 0; j < 7; j++) {
        data7D[i][j] = random.nextDouble();
      }
    }

    // Map the 7D data patterns to colors
    double[][] dataColors = new double[numSamples][3];
    for (int i = 0; i < numSamples; i++) {
      for (int j = 0; j < 3; j++) {
        dataColors[i][j] = data7D[i][j + 3];
      }
    }

    // Map the 7D data patterns to sizes
    double[] dataSizes = new double[numSamples];
    for (int i = 0; i < numSamples; i++) {
      dataSizes[i] = data7D[i][0] + data7D[i][1] + data7D[i][2];
    }

    // Create the chart
    Scatter3DChart chart =
        new Scatter3DChartBuilder()
            .width(800)
            .height(600)
            .title("Interactive 3D Plot")
            .xAxisTitle("Dimension 1")
            .yAxisTitle("Dimension 2")
            .zAxisTitle("Dimension 3")
            .build();

    // Add data points to the chart
    chart.addSeries(
        "Data",
        Arrays.stream(data7D).mapToDouble(row -> row[0]).toArray(),
        Arrays.stream(data7D).mapToDouble(row -> row[1]).toArray(),
        Arrays.stream(data7D).mapToDouble(row -> row[2]).toArray(),
        Arrays.stream(dataSizes).map(s -> s * 10).toArray(),
        Arrays.stream(dataColors).map(row -> new Color((float) row[0], (float) row[1], (float) row[2])).toArray(Color[]::new),
        new SphereMarker());

    // Show the chart
    new SwingWrapper<>(chart).displayChart();
  }
}
