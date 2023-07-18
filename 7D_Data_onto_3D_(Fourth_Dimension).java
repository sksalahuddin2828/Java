import java.util.Random;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int numSamples = 100;
        double[][] data7d = generateRandomData(numSamples);

        // Project 7D data to 3D
        double[][] projectedData3d = project7dTo3d(data7d);

        // Create the plot
        PlotPanel plotPanel = new PlotPanel(projectedData3d, data7d);
        plotPanel.showPlot();
    }

    private static double[][] generateRandomData(int numSamples) {
        double[][] data7d = new double[numSamples][7];
        Random random = new Random(42);

        for (int i = 0; i < numSamples; i++) {
            for (int j = 0; j < 7; j++) {
                data7d[i][j] = random.nextDouble();
            }
        }

        return data7d;
    }

    private static double[][] project7dTo3d(double[][] data7d) {
        RealMatrix dataMatrix = new Array2DRowRealMatrix(data7d);
        EigenDecomposition decomposition = new EigenDecomposition(dataMatrix.transpose().multiply(dataMatrix));
        RealMatrix eigenVectors = decomposition.getV();

        RealMatrix projectedDataMatrix = dataMatrix.multiply(eigenVectors.getSubMatrix(0, 6, 0, 2));
        return projectedDataMatrix.getData();
    }

    private static class PlotPanel extends JPanel {
        private final double[][] projectedData3d;
        private final double[][] data7d;

        public PlotPanel(double[][] projectedData3d, double[][] data7d) {
            this.projectedData3d = projectedData3d;
            this.data7d = data7d;

            setPreferredSize(new Dimension(800, 800));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int numSamples = data7d.length;

            // Scatter plot with colors representing the additional dimensions (4th, 5th, 6th, and 7th)
            for (int i = 0; i < numSamples; i++) {
                int x = (int) (projectedData3d[i][0] * getWidth());
                int y = (int) (projectedData3d[i][1] * getHeight());
                int z = (int) (projectedData3d[i][2] * 255);
                Color color = new Color(z, z, z);
                g2d.setColor(color);
                g2d.fillOval(x - 5, y - 5, 10, 10);
            }

            // Add a color bar for the 4th dimension
            int colorBarWidth = 30;
            int colorBarHeight = 200;
            int colorBarX = getWidth() - colorBarWidth - 10;
            int colorBarY = getHeight() / 2 - colorBarHeight / 2;

            g2d.setColor(Color.WHITE);
            g2d.fillRect(colorBarX, colorBarY, colorBarWidth, colorBarHeight);

            for (int i = 0; i < colorBarHeight; i++) {
                int colorValue = (int) (i / (double) colorBarHeight * 255);
                Color color = new Color(colorValue, colorValue, colorValue);
                g2d.setColor(color);
                g2d.fillRect(colorBarX, colorBarY + i, colorBarWidth, 1);
            }

            g2d.setColor(Color.BLACK);
            g2d.drawRect(colorBarX, colorBarY, colorBarWidth, colorBarHeight);
            g2d.drawString("Fourth Dimension", colorBarX + colorBarWidth / 2 - 30, colorBarY - 10);
        }

        public void showPlot() {
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Scatter Plot");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(this);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            });
        }
    }
}
