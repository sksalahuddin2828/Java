import org.jzy3d.chart.Chart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.Scatter;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int numSamples = 100;
        Random random = new Random(42);

        Coord3d[] dataPoints = new Coord3d[numSamples];
        Color[] dataColors = new Color[numSamples];
        float[] dataSizes = new float[numSamples];

        for (int i = 0; i < numSamples; i++) {
            float x = random.nextFloat();
            float y = random.nextFloat();
            float z = random.nextFloat();
            dataPoints[i] = new Coord3d(x, y, z);

            float r = random.nextFloat();
            float g = random.nextFloat();
            float b = random.nextFloat();
            dataColors[i] = new Color(r, g, b);

            float size = x + y + z;
            dataSizes[i] = size;
        }

        Scatter scatter = new Scatter(dataPoints, dataColors, dataSizes);
        Chart chart = new Chart();
        chart.getScene().add(scatter);

        ChartLauncher.openChart(chart);
    }
}
