// Jzy3d library added to your project --> following links:
// Jzy3d: http://www.jzy3d.org/
// Jzy3d Dependencies: http://www.jzy3d.org/dependencies.php

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import org.jzy3d.chart.Chart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.colors.colormaps.ColorMapHotCold;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.colors.colormaps.ColorMapRedAndGreen;
import org.jzy3d.colors.colormaps.ColorMapWhiteBlue;
import org.jzy3d.colors.colormaps.ColorMapWhiteGreen;
import org.jzy3d.colors.colormaps.ColorMapWhiteRed;
import org.jzy3d.colors.colormaps.ColorMapWhiteYellow;
import org.jzy3d.colors.colormaps.IColorMap;
import org.jzy3d.javafx.JavaFXChartFactory;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.LineStrip;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.rendering.canvas.Quality;

public class TesseractPlot {
    public static void main(String[] args) {
        JavaFXChartFactory factory = new JavaFXChartFactory();
        Chart chart = factory.newChart(Quality.Advanced, "swing");
        List<Coord3d> vertices = getTesseractVertices();
        List<LineStrip> edges = getTesseractEdges(vertices);
        List<Point> projectedVertices = getProjectedVertices(vertices);
        addEdgesToChart(chart, edges);
        addVerticesToChart(chart, projectedVertices);
        ChartLauncher.openChart(chart);
    }

    private static List<Coord3d> getTesseractVertices() {
        List<Coord3d> vertices = new ArrayList<>();
        vertices.add(new Coord3d(-1, -1, -1));
        vertices.add(new Coord3d(-1, -1, 1));
        vertices.add(new Coord3d(-1, 1, -1));
        vertices.add(new Coord3d(-1, 1, 1));
        vertices.add(new Coord3d(1, -1, -1));
        vertices.add(new Coord3d(1, -1, 1));
        vertices.add(new Coord3d(1, 1, -1));
        vertices.add(new Coord3d(1, 1, 1));
        return vertices;
    }

    private static List<LineStrip> getTesseractEdges(List<Coord3d> vertices) {
        List<LineStrip> edges = new ArrayList<>();
        edges.add(new LineStrip(0, 1));
        edges.add(new LineStrip(0, 2));
        edges.add(new LineStrip(0, 4));
        edges.add(new LineStrip(1, 3));
        edges.add(new LineStrip(1, 5));
        edges.add(new LineStrip(2, 3));
        edges.add(new LineStrip(2, 6));
        edges.add(new LineStrip(3, 7));
        edges.add(new LineStrip(4, 5));
        edges.add(new LineStrip(4, 6));
        edges.add(new LineStrip(5, 7));
        edges.add(new LineStrip(6, 7));
        return edges;
    }

    private static List<Point> getProjectedVertices(List<Coord3d> vertices) {
        double angle = Math.PI / 4;
        double[][] rotationMatrix = {
            {Math.cos(angle), 0, -Math.sin(angle)},
            {0, Math.cos(angle), -Math.sin(angle)},
            {Math.sin(angle), 0, Math.cos(angle)}
        };
        List<Point> projectedVertices = new ArrayList<>();
        for (Coord3d vertex : vertices) {
            double[] projected = new double[3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    projected[i] += vertex.get(j) * rotationMatrix[i][j];
                }
            }
            projectedVertices.add(new Point(new Coord3d(projected[0], projected[1], projected[2]), Color.BLACK));
        }
        return projectedVertices;
    }

    private static void addEdgesToChart(Chart chart, List<LineStrip> edges) {
        for (LineStrip edge : edges) {
            chart.getScene().getGraph().add(edge);
        }
    }

    private static void addVerticesToChart(Chart chart, List<Point> projectedVertices) {
        for (Point vertex : projectedVertices) {
            chart.getScene().getGraph().add(vertex);
        }
    }
}
