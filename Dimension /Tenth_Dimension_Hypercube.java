import org.jzy3d.colors.Color;
import org.jzy3d.colors.colormaps.ColorMapHotCold;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.colors.colormaps.ColorMapWhiteBlue;
import org.jzy3d.colors.colormaps.ColorMapWhiteGreen;
import org.jzy3d.colors.colormaps.ColorMapWhiteRed;
import org.jzy3d.javafx.JavaFXChartFactory;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid.Grid;
import org.jzy3d.plot3d.builder.concrete.OrthonormalTessellator;
import org.jzy3d.plot3d.primitives.LineStrip;
import org.jzy3d.plot3d.primitives.Scatter;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.plot3d.rendering.view.modes.CameraMode;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;

import javax.swing.*;

public class HypercubeVisualization {

    public static void main(String[] args) {
        int numDimensions = 10;

        // Generate hypercube vertices and edges
        double[][] vertices = generateHypercubeVertices(numDimensions);
        int[][] edges = generateHypercubeEdges(numDimensions);

        // Create a JFXPanel to hold the JavaFX-based 3D chart
        JFXPanel fxPanel = new JFXPanel();
        JFrame frame = new JFrame();
        frame.add(fxPanel);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Create the 3D chart
        JavaFXChartFactory factory = new JavaFXChartFactory();
        org.jzy3d.chart.Chart chart = factory.newChart(Quality.Advanced, "awt");
        fxPanel.setScene(new Scene(chart.getCanvas()));

        // Plot the 10D hypercube edges
        for (int[] edge : edges) {
            double[][] points = new double[3][2];
            for (int i = 0; i < 3; i++) {
                points[i][0] = vertices[edge[0]][i];
                points[i][1] = vertices[edge[1]][i];
            }
            LineStrip line = new LineStrip(points);
            line.setWidth(1.5f);
            line.setColor(Color.BLACK);
            chart.getScene().getGraph().add(line);
        }

        // Plot projected vertices with labels
        Scatter scatter = new Scatter(toCoords(vertices));
        scatter.setWidth(5f);
        scatter.setLegend(new ColorMapWhiteRed());
        chart.getScene().getGraph().add(scatter);

        // Create illusion lines connecting projected vertices in 3D space
        for (int i = 0; i < vertices.length; i++) {
            for (int j = i + 1; j < vertices.length; j++) {
                double[][] points = new double[3][2];
                for (int k = 0; k < 3; k++) {
                    points[k][0] = vertices[i][k];
                    points[k][1] = vertices[j][k];
                }
                LineStrip line = new LineStrip(points);
                line.setWidth(1.0f);
                line.setColor(new Color(0, 0, 0, 0.3f));
                chart.getScene().getGraph().add(line);
            }
        }

        // Rotate and scale the view
        chart.getView().setCameraMode(CameraMode.TOP);

        // Show the plot
        chart.open("Hypercube Visualization", 800, 800);
    }

    private static double[][] generateHypercubeVertices(int dimensions) {
        int numVertices = (int) Math.pow(2, dimensions);
        double[][] vertices = new double[numVertices][dimensions];
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < dimensions; j++) {
                vertices[i][j] = (i >> j & 1) == 0 ? -1 : 1;
            }
        }
        return vertices;
    }

    private static int[][] generateHypercubeEdges(int dimensions) {
        double[][] vertices = generateHypercubeVertices(dimensions);
        int numVertices = vertices.length;
        int numEdges = numVertices * (numVertices - 1) / 2;
        int[][] edges = new int[numEdges][2];
        int edgeCount = 0;
        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                if (hammingDistance(vertices[i], vertices[j]) == 1) {
                    edges[edgeCount][0] = i;
                    edges[edgeCount][1] = j;
                    edgeCount++;
                }
            }
        }
        return edges;
    }

    private static int hammingDistance(double[] v1, double[] v2) {
        int distance = 0;
        for (int i = 0; i < v1.length; i++) {
            if (v1[i] != v2[i]) {
                distance++;
            }
        }
        return distance;
    }

    private static Coord3d[] toCoords(double[][] points) {
        Coord3d[] coords = new Coord3d[points.length];
        for (int i = 0; i < points.length; i++) {
            coords[i] = new Coord3d(points[i][0], points[i][1], points[i][2]);
        }
        return coords;
    }
}
