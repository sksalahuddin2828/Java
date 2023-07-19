import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.chart.controllers.mouse.camera.AWTCameraMouseController;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapHotCold;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.LineStrip;
import org.jzy3d.plot3d.rendering.canvas.Quality;

public class HypercubePlot {
    public static void main(String[] args) {
        int numDimensions = 5;

        // Generate hypercube edges
        List<Edge> edges = generateHypercubeEdges(numDimensions);

        // Create a chart
        Chart chart = new Chart(Quality.Advanced, "swing");
        chart.getView().setAxeBoxDisplayed(false);
        chart.getView().getCamera().setViewportModeEnabled(true);
        chart.getView().getCamera().setViewportModeRestricted(true);

        // Plot the hypercube edges
        for (Edge edge : edges) {
            List<Coord3d> vertices = new ArrayList<>();
            for (int j = 0; j < numDimensions; j++) {
                int vertex = edge.vertices[j];
                Coord3d coord = new Coord3d();
                for (int k = 0; k < numDimensions; k++) {
                    coord.addSelf(new Coord3d((vertex >> k) & 1, 0, 0));
                }
                vertices.add(coord);
            }
            LineStrip lineStrip = new LineStrip(vertices.toArray(new Coord3d[0]));
            lineStrip.setColor(Color.RED);
            lineStrip.setWireframeColor(Color.RED);
            lineStrip.setWireframeDisplayed(true);
            lineStrip.setWireframeWidth(1.0f);
            chart.getScene().getGraph().add(lineStrip);
        }

        // Generate hypercube vertices
        int numVertices = (int) Math.pow(2, numDimensions);
        Coord3d[] vertices = new Coord3d[numVertices];
        for (int i = 0; i < numVertices; i++) {
            vertices[i] = new Coord3d();
            for (int j = 0; j < numDimensions; j++) {
                vertices[i].x += ((i >> j) & 1) == 0 ? 1 : -1;
            }
        }

        // Apply rotations to the vertices
        Coord3d[] projectedVertices = new Coord3d[numVertices];
        for (int i = 0; i < numVertices; i++) {
            Coord3d vertex = vertices[i];
            projectedVertices[i] = vertex;
            for (int j = 3; j < numDimensions; j++) {
                projectedVertices[i] = vertex.rotateSelf(new Coord3d(0, 0, 1), Math.PI * 2 / numDimensions);
            }
        }

        // Add random offsets to each vertex
        Random random = new Random();
        for (int i = 0; i < numVertices; i++) {
            Coord3d offset = new Coord3d(random.nextDouble() * 0.4 - 0.2, random.nextDouble() * 0.4 - 0.2, random.nextDouble() * 0.4 - 0.2);
            projectedVertices[i] = projectedVertices[i].add(offset);
        }

        // Plot projected vertices with labels
        for (int i = 0; i < numVertices; i++) {
            Coord3d vertex = projectedVertices[i];
            String label = "";
            for (int j = 0; j < numDimensions; j++) {
                label += ((i >> j) & 1) == 0 ? "1" : "-1";
            }
            chart.getScene().getGraph().add(new Coord3dTextSprite(vertex, label));
        }

        // Create illusion lines connecting projected vertices in 3D space
        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                Coord3d v1 = projectedVertices[i];
                Coord3d v2 = projectedVertices[j];
                chart.getScene().getGraph().add(new LineStrip(new Coord3d[] { v1, v2 }, Color.BLACK, 1.0f));
            }
        }

        // Configure the color bar for the third dimension
        ColorMapper colorMapper = new ColorMapper(new ColorMapHotCold(), chart.getView().getBounds().getZmin(), chart.getView().getBounds().getZmax(), new Color(1, 1, 1, 0.5f));
        chart.getView().setBackgroundColor(Color.WHITE);
        chart.getView().setBoundManual(true);
        chart.getView().setBounds(-1, 1, -1, 1, -1, 1);
        chart.getView().setAxeBoxDisplayed(false);
        chart.getView().setGridDisplayed(false);
        chart.getView().setLegendDisplayed(false);
        chart.getView().setViewPortModeRotation(true);

        // Create a frame and add the chart
        JFrame frame = new JFrame("Hypercube Plot");
        frame.setPreferredSize(new Dimension(800, 800));
        frame.getContentPane().add(chart.getCanvas());

        // Configure the mouse controller for rotation
        AWTCameraMouseController mouseController = new AWTCameraMouseController(chart);
        chart.addController(mouseController);

        // Launch the frame
        ChartLauncher.openChart(chart, new Dimension(800, 800), "Hypercube Plot");
    }

    private static List<Edge> generateHypercubeEdges(int dimensions) {
        int numVertices = (int) Math.pow(2, dimensions);
        int[] grayCodes = new int[numVertices];
        for (int i = 0; i < numVertices; i++) {
            grayCodes[i] = i ^ (i >> 1);
        }

        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < dimensions; j++) {
                int neighbor = i ^ (1 << j);
                if (grayCodes[i] < grayCodes[neighbor]) {
                    edges.add(new Edge(i, neighbor));
                }
            }
        }

        return edges;
    }

    private static class Edge {
        private int[] vertices;

        public Edge(int vertex1, int vertex2) {
            vertices = new int[] { vertex1, vertex2 };
        }
    }

    private static class Coord3dTextSprite extends SpriteText {
        private Coord3d position;

        public Coord3dTextSprite(Coord3d position, String text) {
            super(text);
            this.position = position;
        }

        @Override
        public Coord3d getPosition() {
            return position;
        }
    }
}
