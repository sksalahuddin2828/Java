import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

class HypercubePlot extends JPanel {
    private static final long serialVersionUID = 1L;

    private int numDimensions;
    private double[][] vertices;
    private double[][] projectedVertices3D;
    private double[][] projectedVertices45678;
    private String[] labels;

    public HypercubePlot(int numDimensions) {
        this.numDimensions = numDimensions;
        generateHypercubeVertices();
        calculateProjection();
        setPreferredSize(new Dimension(800, 800));
    }

    private void generateHypercubeVertices() {
        int numVertices = (int) Math.pow(2, numDimensions);
        vertices = new double[numVertices][numDimensions];
        int[] pattern = new int[numDimensions];
        for (int i = 0; i < numDimensions; i++) {
            pattern[i] = -1;
        }
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numDimensions; j++) {
                if (pattern[j] == -1) {
                    vertices[i][j] = -1;
                } else {
                    vertices[i][j] = 1;
                }
            }
            for (int j = numDimensions - 1; j >= 0; j--) {
                pattern[j] *= -1;
                if (pattern[j] == 1) {
                    break;
                }
            }
        }
    }

    private void calculateProjection() {
        double angle = Math.PI / 4;

        // Define rotation matrix for the first three dimensions
        double[][] rotationMatrix3D = {
            { Math.cos(angle), 0, -Math.sin(angle) },
            { 0, Math.cos(angle), 0 },
            { Math.sin(angle), 0, Math.cos(angle) }
        };

        // Project vertices onto 3D space
        projectedVertices3D = new double[vertices.length][3];
        for (int i = 0; i < vertices.length; i++) {
            for (int j = 0; j < 3; j++) {
                projectedVertices3D[i][j] = 0;
                for (int k = 0; k < numDimensions; k++) {
                    projectedVertices3D[i][j] += vertices[i][k] * rotationMatrix3D[k][j];
                }
            }
        }

        // Define rotation matrix for the fourth, fifth, sixth, seventh, and eighth dimensions
        double[][] rotationMatrix45678 = {
            { 1, 0, 0 },
            { 0, Math.cos(angle), -Math.sin(angle) },
            { 0, Math.sin(angle), Math.cos(angle) }
        };

        // Project vertices from 3D space to the fourth, fifth, sixth, seventh, and eighth dimensions
        projectedVertices45678 = new double[projectedVertices3D.length][3];
        for (int i = 0; i < projectedVertices3D.length; i++) {
            for (int j = 0; j < 3; j++) {
                projectedVertices45678[i][j] = 0;
                for (int k = 0; k < 3; k++) {
                    projectedVertices45678[i][j] += projectedVertices3D[i][k] * rotationMatrix45678[k][j];
                }
            }
        }

        // Generate labels for vertices
        labels = new String[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            labels[i] = "";
            for (int j = 0; j < numDimensions; j++) {
                labels[i] += vertices[i][j];
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // Plot the 8D hypercube edges
        g2.setColor(Color.BLACK);
        for (int[] edge : getEdges()) {
            int x1 = (int) (projectedVertices3D[edge[0]][0] * 100) + centerX;
            int y1 = (int) (projectedVertices3D[edge[0]][1] * 100) + centerY;
            int x2 = (int) (projectedVertices3D[edge[1]][0] * 100) + centerX;
            int y2 = (int) (projectedVertices3D[edge[1]][1] * 100) + centerY;
            g2.draw(new Line2D.Double(x1, y1, x2, y2));
        }

        // Plot projected vertices with labels
        FontMetrics metrics = g2.getFontMetrics();
        for (int i = 0; i < projectedVertices3D.length; i++) {
            int x = (int) (projectedVertices3D[i][0] * 100) + centerX;
            int y = (int) (projectedVertices3D[i][1] * 100) + centerY;
            g2.setColor(Color.BLUE);
            g2.fillOval(x - 5, y - 5, 10, 10);
            g2.setColor(Color.BLACK);
            g2.drawString(labels[i], x - metrics.stringWidth(labels[i]) / 2, y + metrics.getHeight() / 2);
        }
    }

    private int[][] getEdges() {
        int numVertices = (int) Math.pow(2, numDimensions);
        int numEdges = numDimensions * numVertices / 2;
        int[][] edges = new int[numEdges][2];
        int edgeIndex = 0;
        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                int diffCount = 0;
                for (int k = 0; k < numDimensions; k++) {
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
        return edges;
    }
}

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Hypercube Plot");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new HypercubePlot(8));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
