import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class HypercubePlot extends JPanel {

    private static final int NUM_DIMENSIONS = 8;
    private static final int WINDOW_SIZE = 800;
    private static final double ANGLE = Math.PI / 4;

    private double[][] vertices;
    private int[][] edges;
    private double[][] projectedVertices3D;
    private double[][] projectedVertices45678;
    private String[] labels;

    public HypercubePlot() {
        generateHypercubeVertices(NUM_DIMENSIONS);
        generateHypercubeEdges(NUM_DIMENSIONS);
        rotateVertices3D();
        projectVertices45678();

        labels = new String[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            StringBuilder labelBuilder = new StringBuilder();
            for (double v : vertices[i]) {
                labelBuilder.append((int) v);
            }
            labels[i] = labelBuilder.toString();
        }

        setPreferredSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));
    }

    private void generateHypercubeVertices(int dimensions) {
        int numVertices = (int) Math.pow(2, dimensions);
        vertices = new double[numVertices][dimensions];
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < dimensions; j++) {
                vertices[i][j] = i / (int) Math.pow(2, j) % 2 == 0 ? -1 : 1;
            }
        }
    }

    private void generateHypercubeEdges(int dimensions) {
        int numVertices = (int) Math.pow(2, dimensions);
        int maxNumEdges = dimensions * (numVertices - 1) / 2;
        edges = new int[maxNumEdges][2];
        int edgeIndex = 0;
        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                int diffCount = 0;
                for (int k = 0; k < dimensions; k++) {
                    if (vertices[i][k] != vertices[j][k]) {
                        diffCount++;
                    }
                }
                if (diffCount == 1) {
                    edges[edgeIndex][0] = i;
                    edges[edgeIndex][1] = j;
                    edgeIndex++;
                }
            }
        }
    }

    private void rotateVertices3D() {
        projectedVertices3D = new double[vertices.length][3];
        double[][] rotationMatrix3D = {
                {Math.cos(ANGLE), 0, -Math.sin(ANGLE)},
                {0, Math.cos(ANGLE), 0},
                {Math.sin(ANGLE), 0, Math.cos(ANGLE)}
        };
        for (int i = 0; i < vertices.length; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < NUM_DIMENSIONS; k++) {
                    projectedVertices3D[i][j] += vertices[i][k] * rotationMatrix3D[k][j];
                }
            }
        }
    }

    private void projectVertices45678() {
        projectedVertices45678 = new double[projectedVertices3D.length][5];
        double[][] rotationMatrix45678 = {
                {1, 0, 0},
                {0, Math.cos(ANGLE), -Math.sin(ANGLE)},
                {0, Math.sin(ANGLE), Math.cos(ANGLE)}
        };
        for (int i = 0; i < projectedVertices3D.length; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 3; k++) {
                    projectedVertices45678[i][j] += projectedVertices3D[i][k] * rotationMatrix45678[k][j];
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int offsetX = getWidth() / 2;
        int offsetY = getHeight() / 2;

        // Plot the 3D hypercube edges
        for (int[] edge : edges) {
            int x1 = (int) projectedVertices3D[edge[0]][0] + offsetX;
            int y1 = (int) projectedVertices3D[edge[0]][1] + offsetY;
            int x2 = (int) projectedVertices3D[edge[1]][0] + offsetX;
            int y2 = (int) projectedVertices3D[edge[1]][1] + offsetY;
            g2d.setColor(Color.BLACK);
            g2d.drawLine(x1, y1, x2, y2);
        }

        // Plot projected vertices with labels
        for (int i = 0; i < projectedVertices3D.length; i++) {
            int x = (int) projectedVertices3D[i][0] + offsetX;
            int y = (int) projectedVertices3D[i][1] + offsetY;
            g2d.setColor(Color.RED);
            g2d.fillOval(x - 5, y - 5, 10, 10);
            g2d.setColor(Color.BLACK);
            g2d.drawString(labels[i], x, y);
        }

        // Create illusion lines connecting projected vertices in 3D space
        for (int i = 0; i < projectedVertices3D.length; i++) {
            for (int j = i + 1; j < projectedVertices3D.length; j++) {
                int x1 = (int) projectedVertices3D[i][0] + offsetX;
                int y1 = (int) projectedVertices3D[i][1] + offsetY;
                int x2 = (int) projectedVertices3D[j][0] + offsetX;
                int y2 = (int) projectedVertices3D[j][1] + offsetY;
                g2d.setColor(Color.GRAY);
                g2d.drawLine(x1, y1, x2, y2);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hypercube Plot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        HypercubePlot hypercubePlot = new HypercubePlot();
        frame.getContentPane().add(hypercubePlot);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
