import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.math.plot.Plot3DPanel;

public class TesseractPlot {

    public static void main(String[] args) {
        double[][] vertices = { { -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, 1 }, { -1, -1, -1, -1, 1, -1 },
                { -1, -1, -1, -1, 1, 1 }, { -1, -1, -1, 1, -1, -1 }, { -1, -1, -1, 1, -1, 1 }, { -1, -1, -1, 1, 1, -1 },
                { -1, -1, -1, 1, 1, 1 }, { -1, 1, -1, -1, -1, -1 }, { -1, 1, -1, -1, -1, 1 }, { -1, 1, -1, -1, 1, -1 },
                { -1, 1, -1, -1, 1, 1 }, { -1, 1, 1, -1, -1, -1 }, { -1, 1, 1, -1, -1, 1 }, { -1, 1, 1, -1, 1, -1 },
                { -1, 1, 1, -1, 1, 1 }, { 1, -1, -1, -1, -1, -1 }, { 1, -1, -1, -1, -1, 1 }, { 1, -1, -1, -1, 1, -1 },
                { 1, -1, -1, -1, 1, 1 }, { 1, -1, 1, -1, -1, -1 }, { 1, -1, 1, -1, -1, 1 }, { 1, -1, 1, -1, 1, -1 },
                { 1, -1, 1, -1, 1, 1 }, { 1, 1, -1, -1, -1, -1 }, { 1, 1, -1, -1, -1, 1 }, { 1, 1, -1, -1, 1, -1 },
                { 1, 1, -1, -1, 1, 1 }, { 1, 1, 1, -1, -1, -1 }, { 1, 1, 1, -1, -1, 1 }, { 1, 1, 1, -1, 1, -1 },
                { 1, 1, 1, -1, 1, 1 } };

        int[][] edges = { { 0, 1 }, { 0, 2 }, { 0, 4 }, { 1, 3 }, { 1, 5 }, { 2, 3 }, { 2, 6 }, { 3, 7 }, { 4, 5 },
                { 4, 6 }, { 5, 7 }, { 6, 7 }, { 8, 9 }, { 8, 10 }, { 8, 12 }, { 9, 11 }, { 9, 13 }, { 10, 11 },
                { 10, 14 }, { 11, 15 }, { 12, 13 }, { 12, 14 }, { 13, 15 }, { 14, 15 }, { 0, 8 }, { 1, 9 }, { 2, 10 },
                { 3, 11 }, { 4, 12 }, { 5, 13 }, { 6, 14 }, { 7, 15 } };

        double angle = Math.PI / 4;

        double[][] rotationMatrix3D = { { Math.cos(angle), 0, -Math.sin(angle) }, { 0, Math.cos(angle), 0 },
                { Math.sin(angle), 0, Math.cos(angle) } };

        double[][] projectedVertices3D = multiplyMatrix(vertices, getSubMatrix(vertices, 0, 2), rotationMatrix3D);

        double[][] rotationMatrix456 = { { 1, 0, 0 }, { 0, Math.cos(angle), -Math.sin(angle) },
                { 0, Math.sin(angle), Math.cos(angle) } };

        double[][] projectedVertices456 = multiplyMatrix(projectedVertices3D, getSubMatrix(projectedVertices3D, 0, 2),
                rotationMatrix456);

        Plot3DPanel plot = new Plot3DPanel();
        plot.setAxisLabels("X", "Y", "Z");

        for (int i = 0; i < edges.length; i++) {
            double[] x = { projectedVertices3D[edges[i][0]][0], projectedVertices3D[edges[i][1]][0] };
            double[] y = { projectedVertices3D[edges[i][0]][1], projectedVertices3D[edges[i][1]][1] };
            double[] z = { projectedVertices3D[edges[i][0]][2], projectedVertices3D[edges[i][1]][2] };
            plot.addLinePlot("", Color.BLACK, x, y, z);
        }

        for (int i = 0; i < projectedVertices3D.length; i++) {
            double x = projectedVertices3D[i][0];
            double y = projectedVertices3D[i][1];
            double z = projectedVertices3D[i][2];
            String label = getStringLabel(vertices[i]);
            plot.addLabel(label, Color.BLACK, x, y, z);
        }

        JFrame frame = new JFrame("Tesseract Plot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.add(plot);
        frame.setVisible(true);
    }

    private static double[][] multiplyMatrix(double[][] matrix1, double[][] matrix2, double[][] result) {
        int rows = matrix1.length;
        int cols = matrix2[0].length;
        int inner = matrix2.length;

        double[][] multipliedMatrix = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double sum = 0;
                for (int k = 0; k < inner; k++) {
                    sum += matrix1[i][k] * matrix2[k][j];
                }
                multipliedMatrix[i][j] = sum;
            }
        }

        return multipliedMatrix;
    }

    private static double[][] getSubMatrix(double[][] matrix, int startRow, int endRow) {
        int rows = endRow - startRow + 1;
        int cols = matrix[0].length;

        double[][] subMatrix = new double[rows][cols];

        for (int i = startRow; i <= endRow; i++) {
            for (int j = 0; j < cols; j++) {
                subMatrix[i - startRow][j] = matrix[i][j];
            }
        }

        return subMatrix;
    }

    private static String getStringLabel(double[] vertex) {
        StringBuilder sb = new StringBuilder();
        for (double v : vertex) {
            sb.append((int) v);
        }
        return sb.toString();
    }
}
