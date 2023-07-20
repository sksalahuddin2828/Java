import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularValueDecomposition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GaussianDistributionVisualization extends JFrame {
    // Define the parameters for the Gaussian distribution
    private static final double[] mean = new double[]{0, 0};
    private static final double[][] covarianceMatrix = {{1, 0.5}, {0.5, 1}};

    // Define the function f(x) = ac - cx^2
    private static final double a = 2;
    private static final double c = 1;

    public GaussianDistributionVisualization() {
        // Create a meshgrid for the 2D plot
        int resolution = 100;
        double[] x = linspace(-5, 5, resolution);
        double[] y = linspace(-5, 5, resolution);
        double[][] pos = meshgrid(x, y);

        // Calculate the Gaussian probability at each point in the meshgrid
        RealMatrix covarianceMatrixObj = new Array2DRowRealMatrix(covarianceMatrix);
        RealMatrix invCovarianceMatrix = new SingularValueDecomposition(covarianceMatrixObj).getSolver().getInverse();
        double[][] ZGaussian = gaussianPDF(pos, mean, invCovarianceMatrix);

        // Combine Gaussian with f(x) and g(y) to get the desired distribution
        double[][] Z = combineFunctions(ZGaussian, x, y);

        // Create the 3D plot
        setTitle("2D Gaussian Distribution with Rotational Symmetry and Hersehel Maxwell Divination");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the surface plot
        SurfacePlot surface = new SurfacePlot(Z, x, y);
        add(surface, BorderLayout.CENTER);

        // Start animation timer
        Timer timer = new Timer(50, new ActionListener() {
            double t = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                double timeParam = Math.sin(t * 0.1); // Replace this with your 4D data source
                double[][] updatedZ = applyTimeParameter(Z, timeParam);
                surface.setZData(updatedZ);
                surface.repaint();
                t += 0.1;
            }
        });
        timer.start();

        // Set the size of the window and display it
        setSize(800, 600);
        setVisible(true);
    }

    private double[] linspace(double start, double end, int numPoints) {
        double[] array = new double[numPoints];
        double step = (end - start) / (numPoints - 1);
        for (int i = 0; i < numPoints; i++) {
            array[i] = start + i * step;
        }
        return array;
    }

    private double[][] meshgrid(double[] x, double[] y) {
        int nx = x.length;
        int ny = y.length;
        double[][] grid = new double[nx][ny];
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                grid[i][j] = x[i];
            }
        }
        for (int j = 0; j < ny; j++) {
            for (int i = 0; i < nx; i++) {
                grid[i][j] = y[j];
            }
        }
        return grid;
    }

    private double[][] gaussianPDF(double[][] pos, double[] mean, RealMatrix invCovarianceMatrix) {
        int n = pos.length;
        int m = pos[0].length;
        double[][] Z = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                RealMatrix diff = new Array2DRowRealMatrix(new double[]{pos[i][j] - mean[0], pos[i][j] - mean[1]});
                RealMatrix diffT = diff.transpose();
                RealMatrix exponent = diffT.multiply(invCovarianceMatrix).multiply(diff);
                Z[i][j] = Math.exp(-0.5 * exponent.getEntry(0, 0)) / (2 * Math.PI * Math.sqrt(covarianceMatrixDeterminant()));
            }
        }
        return Z;
    }

    private double covarianceMatrixDeterminant() {
        return covarianceMatrix[0][0] * covarianceMatrix[1][1] - covarianceMatrix[0][1] * covarianceMatrix[1][0];
    }

    private double[][] combineFunctions(double[][] ZGaussian, double[] x, double[] y) {
        int n = ZGaussian.length;
        int m = ZGaussian[0].length;
        double[][] Z = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Z[i][j] = ZGaussian[i][j] * (a * c - c * x[i] * x[i]) * (a * c - c * y[j] * y[j]);
            }
        }
        return Z;
    }

    private double[][] applyTimeParameter(double[][] Z, double timeParam) {
        int n = Z.length;
        int m = Z[0].length;
        double[][] updatedZ = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                updatedZ[i][j] = Z[i][j] * timeParam;
            }
        }
        return updatedZ;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GaussianDistributionVisualization::new);
    }
}

class SurfacePlot extends JPanel {
    private double[][] Z;
    private double[] x;
    private double[] y;

    public SurfacePlot(double[][] Z, double[] x, double[] y) {
        this.Z = Z;
        this.x = x;
        this.y = y;
    }

    public void setZData(double[][] Z) {
        this.Z = Z;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (Z == null || Z.length == 0 || Z[0].length == 0) {
            return;
        }
        int width = getWidth();
        int height = getHeight();

        double xStep = width / (double) (x.length - 1);
        double yStep = height / (double) (y.length - 1);

        double maxValue = Double.MIN_VALUE;
        double minValue = Double.MAX_VALUE;

        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < y.length; j++) {
                maxValue = Math.max(maxValue, Z[i][j]);
                minValue = Math.min(minValue, Z[i][j]);
            }
        }

        for (int i = 0; i < x.length - 1; i++) {
            for (int j = 0; j < y.length - 1; j++) {
                double[] XPoints = {i * xStep, (i + 1) * xStep, (i + 1) * xStep, i * xStep};
                double[] YPoints = {j * yStep, j * yStep, (j + 1) * yStep, (j + 1) * yStep};

                int[] screenXPoints = new int[4];
                int[] screenYPoints = new int[4];

                for (int k = 0; k < 4; k++) {
                    screenXPoints[k] = (int) XPoints[k];
                    screenYPoints[k] = height - (int) YPoints[k];
                }

                double value = Z[i][j];
                int colorValue = (int) (255 * (value - minValue) / (maxValue - minValue));
                Color color = new Color(colorValue, colorValue, colorValue);

                g.setColor(color);
                g.fillPolygon(screenXPoints, screenYPoints, 4);
            }
        }
    }
}
