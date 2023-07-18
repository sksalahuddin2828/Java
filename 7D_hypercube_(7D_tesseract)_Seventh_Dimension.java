import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TesseractPlot {
    public static void main(String[] args) {
        // Define tesseract vertices with the seventh dimension
        RealMatrix vertices = MatrixUtils.createRealMatrix(new double[][]{
            {-1, -1, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, -1, 1},
            {-1, -1, -1, -1, -1, 1, -1},
            {-1, -1, -1, -1, -1, 1, 1},
            {-1, -1, -1, -1, 1, -1, -1},
            {-1, -1, -1, -1, 1, -1, 1},
            {-1, -1, -1, -1, 1, 1, -1},
            {-1, -1, -1, -1, 1, 1, 1},
            {-1, -1, -1, 1, -1, -1, -1},
            {-1, -1, -1, 1, -1, -1, 1},
            {-1, -1, -1, 1, -1, 1, -1},
            {-1, -1, -1, 1, -1, 1, 1},
            {-1, -1, 1, -1, -1, -1, -1},
            {-1, -1, 1, -1, -1, -1, 1},
            {-1, -1, 1, -1, -1, 1, -1},
            {-1, -1, 1, -1, -1, 1, 1},
            {-1, -1, 1, -1, 1, -1, -1},
            {-1, -1, 1, -1, 1, -1, 1},
            {-1, -1, 1, -1, 1, 1, -1},
            {-1, -1, 1, -1, 1, 1, 1},
            {-1, -1, 1, 1, -1, -1, -1},
            {-1, -1, 1, 1, -1, -1, 1},
            {-1, -1, 1, 1, -1, 1, -1},
            {-1, -1, 1, 1, -1, 1, 1},
            {-1, -1, 1, 1, 1, -1, -1},
            {-1, -1, 1, 1, 1, -1, 1},
            {-1, -1, 1, 1, 1, 1, -1},
            {-1, -1, 1, 1, 1, 1, 1},
            {-1, 1, -1, -1, -1, -1, -1},
            {-1, 1, -1, -1, -1, -1, 1},
            {-1, 1, -1, -1, -1, 1, -1},
            {-1, 1, -1, -1, -1, 1, 1},
            {-1, 1, -1, -1, 1, -1, -1},
            {-1, 1, -1, -1, 1, -1, 1},
            {-1, 1, -1, -1, 1, 1, -1},
            {-1, 1, -1, -1, 1, 1, 1},
            {-1, 1, -1, 1, -1, -1, -1},
            {-1, 1, -1, 1, -1, -1, 1},
            {-1, 1, -1, 1, -1, 1, -1},
            {-1, 1, -1, 1, -1, 1, 1},
            {-1, 1, -1, 1, 1, -1, -1},
            {-1, 1, -1, 1, 1, -1, 1},
            {-1, 1, -1, 1, 1, 1, -1},
            {-1, 1, -1, 1, 1, 1, 1},
            {-1, 1, 1, -1, -1, -1, -1},
            {-1, 1, 1, -1, -1, -1, 1},
            {-1, 1, 1, -1, -1, 1, -1},
            {-1, 1, 1, -1, -1, 1, 1},
            {-1, 1, 1, -1, 1, -1, -1},
            {-1, 1, 1, -1, 1, -1, 1},
            {-1, 1, 1, -1, 1, 1, -1},
            {-1, 1, 1, -1, 1, 1, 1},
            {-1, 1, 1, 1, -1, -1, -1},
            {-1, 1, 1, 1, -1, -1, 1},
            {-1, 1, 1, 1, -1, 1, -1},
            {-1, 1, 1, 1, -1, 1, 1},
            {-1, 1, 1, 1, 1, -1, -1},
            {-1, 1, 1, 1, 1, -1, 1},
            {-1, 1, 1, 1, 1, 1, -1},
            {-1, 1, 1, 1, 1, 1, 1},
            {1, -1, -1, -1, -1, -1, -1},
            {1, -1, -1, -1, -1, -1, 1},
            {1, -1, -1, -1, -1, 1, -1},
            {1, -1, -1, -1, -1, 1, 1},
            {1, -1, -1, -1, 1, -1, -1},
            {1, -1, -1, -1, 1, -1, 1},
            {1, -1, -1, -1, 1, 1, -1},
            {1, -1, -1, -1, 1, 1, 1},
            {1, -1, -1, 1, -1, -1, -1},
            {1, -1, -1, 1, -1, -1, 1},
            {1, -1, -1, 1, -1, 1, -1},
            {1, -1, -1, 1, -1, 1, 1},
            {1, -1, -1, 1, 1, -1, -1},
            {1, -1, -1, 1, 1, -1, 1},
            {1, -1, -1, 1, 1, 1, -1},
            {1, -1, -1, 1, 1, 1, 1},
            {1, -1, 1, -1, -1, -1, -1},
            {1, -1, 1, -1, -1, -1, 1},
            {1, -1, 1, -1, -1, 1, -1},
            {1, -1, 1, -1, -1, 1, 1},
            {1, -1, 1, -1, 1, -1, -1},
            {1, -1, 1, -1, 1, -1, 1},
            {1, -1, 1, -1, 1, 1, -1},
            {1, -1, 1, -1, 1, 1, 1},
            {1, -1, 1, 1, -1, -1, -1},
            {1, -1, 1, 1, -1, -1, 1},
            {1, -1, 1, 1, -1, 1, -1},
            {1, -1, 1, 1, -1, 1, 1},
            {1, -1, 1, 1, 1, -1, -1},
            {1, -1, 1, 1, 1, -1, 1},
            {1, -1, 1, 1, 1, 1, -1},
            {1, -1, 1, 1, 1, 1, 1},
            {1, 1, -1, -1, -1, -1, -1},
            {1, 1, -1, -1, -1, -1, 1},
            {1, 1, -1, -1, -1, 1, -1},
            {1, 1, -1, -1, -1, 1, 1},
            {1, 1, -1, -1, 1, -1, -1},
            {1, 1, -1, -1, 1, -1, 1},
            {1, 1, -1, -1, 1, 1, -1},
            {1, 1, -1, -1, 1, 1, 1},
            {1, 1, -1, 1, -1, -1, -1},
            {1, 1, -1, 1, -1, -1, 1},
            {1, 1, -1, 1, -1, 1, -1},
            {1, 1, -1, 1, -1, 1, 1},
            {1, 1, -1, 1, 1, -1, -1},
            {1, 1, -1, 1, 1, -1, 1},
            {1, 1, -1, 1, 1, 1, -1},
            {1, 1, -1, 1, 1, 1, 1},
            {1, 1, 1, -1, -1, -1, -1},
            {1, 1, 1, -1, -1, -1, 1},
            {1, 1, 1, -1, -1, 1, -1},
            {1, 1, 1, -1, -1, 1, 1},
            {1, 1, 1, -1, 1, -1, -1},
            {1, 1, 1, -1, 1, -1, 1},
            {1, 1, 1, -1, 1, 1, -1},
            {1, 1, 1, -1, 1, 1, 1},
            {1, 1, 1, 1, -1, -1, -1},
            {1, 1, 1, 1, -1, -1, 1},
            {1, 1, 1, 1, -1, 1, -1},
            {1, 1, 1, 1, -1, 1, 1},
            {1, 1, 1, 1, 1, -1, -1},
            {1, 1, 1, 1, 1, -1, 1},
            {1, 1, 1, 1, 1, 1, -1},
            {1, 1, 1, 1, 1, 1, 1}
        });

        // Define edges of the tesseract
        List<int[]> edges = new ArrayList<>();
        edges.add(new int[]{0, 1});
        edges.add(new int[]{0, 2});
        edges.add(new int[]{0, 4});
        edges.add(new int[]{1, 3});
        edges.add(new int[]{1, 5});
        edges.add(new int[]{2, 3});
        edges.add(new int[]{2, 6});
        edges.add(new int[]{3, 7});
        edges.add(new int[]{4, 5});
        edges.add(new int[]{4, 6});
        edges.add(new int[]{5, 7});
        edges.add(new int[]{6, 7});
        edges.add(new int[]{8, 9});
        edges.add(new int[]{8, 10});
        edges.add(new int[]{8, 12});
        edges.add(new int[]{9, 11});
        edges.add(new int[]{9, 13});
        edges.add(new int[]{10, 11});
        edges.add(new int[]{10, 14});
        edges.add(new int[]{11, 15});
        edges.add(new int[]{12, 13});
        edges.add(new int[]{12, 14});
        edges.add(new int[]{13, 15});
        edges.add(new int[]{14, 15});
        edges.add(new int[]{0, 8});
        edges.add(new int[]{1, 9});
        edges.add(new int[]{2, 10});
        edges.add(new int[]{3, 11});
        edges.add(new int[]{4, 12});
        edges.add(new int[]{5, 13});
        edges.add(new int[]{6, 14});
        edges.add(new int[]{7, 15});
        edges.add(new int[]{16, 17});
        edges.add(new int[]{16, 18});
        edges.add(new int[]{16, 20});
        edges.add(new int[]{17, 19});
        edges.add(new int[]{17, 21});
        edges.add(new int[]{18, 19});
        edges.add(new int[]{18, 22});
        edges.add(new int[]{19, 23});
        edges.add(new int[]{20, 21});
        edges.add(new int[]{20, 22});
        edges.add(new int[]{21, 23});
        edges.add(new int[]{22, 23});
        edges.add(new int[]{0, 16});
        edges.add(new int[]{1, 17});
        edges.add(new int[]{2, 18});
        edges.add(new int[]{3, 19});
        edges.add(new int[]{4, 20});
        edges.add(new int[]{5, 21});
        edges.add(new int[]{6, 22});
        edges.add(new int[]{7, 23});

        XYSeriesCollection dataset = new XYSeriesCollection();

        RealMatrix rotationMatrix3D = MatrixUtils.createRealMatrix(new double[][]{
            // Define rotation matrix for the first three dimensions
            {Math.cos(Math.PI / 4), 0, -Math.sin(Math.PI / 4)},
            {0, Math.cos(Math.PI / 4), 0},
            {Math.sin(Math.PI / 4), 0, Math.cos(Math.PI / 4)}
        });
        RealMatrix projectedVertices3D = vertices.getSubMatrix(0, vertices.getRowDimension() - 1, 0, 2)
            .multiply(rotationMatrix3D);

        // Define rotation matrix for the fourth, fifth, sixth, and seventh dimensions
        RealMatrix rotationMatrix4567 = MatrixUtils.createRealMatrix(new double[][]{
            {1, 0, 0},
            {0, Math.cos(Math.PI / 4), -Math.sin(Math.PI / 4)},
            {0, Math.sin(Math.PI / 4), Math.cos(Math.PI / 4)}
        });
        RealMatrix projectedVertices4567 = projectedVertices3D.multiply(rotationMatrix4567);

        // Create a series for the scatter plot
        XYSeries series = new XYSeries("Tesseract");

        // Add projected vertices to the series
        for (int i = 0; i < projectedVertices3D.getRowDimension(); i++) {
            RealVector vertex = projectedVertices3D.getRowVector(i);
            double x = vertex.getEntry(0);
            double y = vertex.getEntry(1);
            double z = vertex.getEntry(2);
            series.add(x, y);
        }

        // Add the series to the dataset
        dataset.addSeries(series);

        // Create the scatter plot
        JFreeChart chart = ChartFactory.createScatterPlot("Tesseract Projection", "X", "Y",
                dataset, PlotOrientation.VERTICAL, true, true, false);

        // Create a chart panel to display the plot
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 500));

        // Create a frame to hold the chart panel
        JFrame frame = new JFrame("Tesseract Projection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
