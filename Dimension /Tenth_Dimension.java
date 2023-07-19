import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.BitmapEncoder.TargetType;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;

public class HypercubePlot {
    public static double[][] generateHypercubeVertices(int dimensions) {
        int numVertices = (int) Math.pow(2, dimensions);
        double[][] vertices = new double[numVertices][dimensions];

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < dimensions; j++) {
                int mask = 1 << j;
                vertices[i][j] = (i & mask) == 0 ? -1 : 1;
            }
        }

        return vertices;
    }

    public static int[][] generateHypercubeEdges(int dimensions) {
        double[][] vertices = generateHypercubeVertices(dimensions);
        int numVertices = (int) Math.pow(2, dimensions);
        int numEdges = dimensions * numVertices / 2;
        int[][] edges = new int[numEdges][2];

        int edgeIdx = 0;
        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                int diffCount = 0;
                for (int k = 0; k < dimensions; k++) {
                    if (vertices[i][k] != vertices[j][k]) {
                        diffCount++;
                        if (diffCount > 1) break;
                    }
                }
                if (diffCount == 1) {
                    edges[edgeIdx][0] = i;
                    edges[edgeIdx][1] = j;
                    edgeIdx++;
                }
            }
        }

        return edges;
    }

    public static void main(String[] args) throws Exception {
        int numDimensions = 10;

        double[][] vertices = generateHypercubeVertices(numDimensions);
        int[][] edges = generateHypercubeEdges(numDimensions);

        // Create a 3D chart
        XYChart chart = new XYChart(800, 800, TargetType.PNG);
        chart.setTitle("10D Hypercube Projection");
        chart.getStyler().setLegendVisible(false);

        // Add the projected vertices to the chart
        for (int i = 0; i < vertices.length; i++) {
            chart.addSeries("Vertex " + i, new double[]{vertices[i][0], vertices[i][1]});
        }

        // Add edges to the chart
        for (int[] edge : edges) {
            int vertex1 = edge[0];
            int vertex2 = edge[1];
            double x1 = vertices[vertex1][0];
            double y1 = vertices[vertex1][1];
            double x2 = vertices[vertex2][0];
            double y2 = vertices[vertex2][1];
            chart.addSeries("Edge " + vertex1 + "-" + vertex2, new double[]{x1, x2}, new double[]{y1, y2});
        }

        // Save the chart as an image
        BitmapEncoder.saveBitmapWithDPI(chart, "./hypercube_projection", BitmapFormat.PNG, 300);
    }
}
