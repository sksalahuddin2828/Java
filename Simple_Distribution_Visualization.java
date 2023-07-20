import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class SimpleGaussianDistribution {

    public static double simpleGaussian(double x, double mu, double sigma) {
        return Math.exp(-0.5 * Math.pow((x - mu) / sigma, 2)) / (sigma * Math.sqrt(2 * Math.PI));
    }

    public static void main(String[] args) {
        // Define parameters for the simple Gaussian distribution
        double muSimple = 10;
        double sigmaSimple = 2;

        // Generate data points for visualization
        int numPoints = 500;
        double[] xVals = new double[numPoints];
        double[] yValsSimple = new double[numPoints];

        double stepSize = 20.0 / (numPoints - 1);
        for (int i = 0; i < numPoints; i++) {
            xVals[i] = i * stepSize;
            yValsSimple[i] = simpleGaussian(xVals[i], muSimple, sigmaSimple);
        }

        // Plot the simple Gaussian distribution
        JFrame frame = new JFrame("Simple Gaussian Distribution");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();

                double xScale = width / 20.0;
                double yScale = height / (sigmaSimple * Math.sqrt(2 * Math.PI));

                Path2D path = new Path2D.Double();
                path.moveTo(xVals[0] * xScale, height - yValsSimple[0] * yScale);
                for (int i = 1; i < numPoints; i++) {
                    path.lineTo(xVals[i] * xScale, height - yValsSimple[i] * yScale);
                }

                g2d.draw(path);
            }
        };

        frame.add(panel);
        frame.setVisible(true);
    }
}
