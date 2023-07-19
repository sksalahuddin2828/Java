import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class HypercubePlot extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final int numDimensions = 9;
    private static final int[] vertexValues = { -1, 1 };
    private static final double angle = Math.PI / 4;

    private static final int[][] vertices = generateHypercubeVertices(numDimensions);
    private static final int[][] edges = generateHypercubeEdges(numDimensions);

    private static int[][] generateHypercubeVertices(int dimensions) {
        int[][] result = new int[(int) Math.pow(2, dimensions)][dimensions];
        int numVertices = result.length;
        int numPerSide = numVertices / 2;

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < dimensions; j++) {
                int value = vertexValues[(i / numPerSide) % 2];
                result[i][j] = value;
                numPerSide /= 2;
            }
        }
        return result;
    }

    private static int[][] generateHypercubeEdges(int dimensions) {
        int numVertices = (int) Math.pow(2, dimensions);
        int numEdges = dimensions * numVertices / 2;

        int[][] result = new int[numEdges][2];
        int edgeIndex = 0;

        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                if (numDifferingBits(i, j) == 1) {
                    result[edgeIndex][0] = i;
                    result[edgeIndex][1] = j;
                    edgeIndex++;
                }
            }
        }
        return result;
    }

    private static int numDifferingBits(int a, int b) {
        int diff = a ^ b;
        int count = 0;
        while (diff != 0) {
            count++;
            diff &= (diff - 1);
        }
        return count;
    }

    private static int[][] projectVertices(double[][] vertices, double[][] rotationMatrix) {
        int numVertices = vertices.length;
        int numDimensions = vertices[0].length;
        int numProjectedDimensions = rotationMatrix[0].length;

        int[][] projectedVertices = new int[numVertices][numProjectedDimensions];

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numProjectedDimensions; j++) {
                double sum = 0;
                for (int k = 0; k < numDimensions; k++) {
                    sum += vertices[i][k] * rotationMatrix[k][j];
                }
                projectedVertices[i][j] = (int) Math.round(sum);
            }
        }
        return projectedVertices;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Plot the hypercube edges
        for (int[] edge : edges) {
            int x1 = (int) (centerX + vertices[edge[0]][0] * 50);
            int y1 = (int) (centerY + vertices[edge[0]][1] * 50);
            int x2 = (int) (centerX + vertices[edge[1]][0] * 50);
            int y2 = (int) (centerY + vertices[edge[1]][1] * 50);

            g2d.draw(new Line2D.Double(x1, y1, x2, y2));
        }

        // Project vertices onto 2D space
        double[][] rotationMatrix3D = { { Math.cos(angle), 0, -Math.sin(angle) },
                { 0, Math.cos(angle), 0 }, { Math.sin(angle), 0, Math.cos(angle) } };
        int[][] projectedVertices3D = projectVertices(vertices, rotationMatrix3D);

        double[][] rotationMatrix45678 = { { 1, 0, 0 }, { 0, Math.cos(angle), -Math.sin(angle) },
                { 0, Math.sin(angle), Math.cos(angle) } };
        int[][] projectedVertices45678 = projectVertices(projectedVertices3D, rotationMatrix45678);

        double[][] rotationMatrix9 = { { Math.cos(angle), -Math.sin(angle), 0 },
                { Math.sin(angle), Math.cos(angle), 0 }, { 0, 0, 1 } };
        int[][] projectedVertices9 = projectVertices(projectedVertices45678, rotationMatrix9);

        // Plot projected vertices with labels
        for (int i = 0; i < projectedVertices3D.length; i++) {
            int x = (int) (centerX + projectedVertices3D[i][0] * 50);
            int y = (int) (centerY + projectedVertices3D[i][1] * 50);

            g2d.setColor(Color.BLACK);
            g2d.fillOval(x - 3, y - 3, 6, 6);
            g2d.drawString(String.valueOf(i), x - 3, y - 3);

            g2d.setColor(Color.GRAY);
            for (int j = i + 1; j < projectedVertices3D.length; j++) {
                int x1 = (int) (centerX + projectedVertices3D[i][0] * 50);
                int y1 = (int) (centerY + projectedVertices3D[i][1] * 50);
                int x2 = (int) (centerX + projectedVertices3D[j][0] * 50);
                int y2 = (int) (centerY + projectedVertices3D[j][1] * 50);

                g2d.draw(new Line2D.Double(x1, y1, x2, y2));
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hypercube Plot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.add(new HypercubePlot());
        frame.setVisible(true);
    }
}
