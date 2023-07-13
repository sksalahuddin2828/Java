import java.util.Random;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.math.plot.*;
import org.math.plot.plotObjects.*;
import org.math.plot.render.*;

public class SatelliteOrbits {
    public static void main(String[] args) {
        // Earth parameters
        double earth_radius = 6371; // Earth radius in kilometers

        // Satellite parameters
        int num_satellites = 10;
        double satellite_radius = 100; // Satellite radius in kilometers
        String satellite_color = "red";

        // Generate random semi-major axes and eccentricities for satellite orbits
        double[] semi_major_axes = new double[num_satellites];
        double[] eccentricities = new double[num_satellites];
        Random random = new Random();

        for (int i = 0; i < num_satellites; i++) {
            semi_major_axes[i] = 800 + random.nextDouble() * (1500 - 800);
            eccentricities[i] = 0.1 + random.nextDouble() * (0.4 - 0.1);
        }

        // Time array
        int num_frames = 100;
        double[] time = new double[num_frames];
        double step = 2 * Math.PI / num_frames;

        for (int i = 0; i < num_frames; i++) {
            time[i] = i * step;
        }

        // Set up the plot
        Plot3DPanel plot = new Plot3DPanel();
        plot.setAxisLabels("X (km)", "Y (km)", "Z (km)");
        plot.setFixedBounds(0, -1500, 1500);
        plot.setFixedBounds(1, -1500, 1500);
        plot.setFixedBounds(2, -1500, 1500);

        // Plotting the Earth
        double[][][] earthPoints = new double[100][50][3];

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 50; j++) {
                double u = i * 2 * Math.PI / 100;
                double v = j * Math.PI / 50;
                double x = earth_radius * Math.cos(u) * Math.sin(v);
                double y = earth_radius * Math.sin(u) * Math.sin(v);
                double z = earth_radius * Math.cos(v);
                earthPoints[i][j][0] = x;
                earthPoints[i][j][1] = y;
                earthPoints[i][j][2] = z;
            }
        }

        plot.addGridPlot("Earth", new double[]{0}, new double[]{0}, earthPoints);

        // Plotting the satellite orbits and markers
        for (int i = 0; i < num_satellites; i++) {
            double semi_major_axis = semi_major_axes[i];
            double eccentricity = eccentricities[i];

            // Parametric equations for satellite orbit
            double[] r = new double[num_frames];
            double[] x_satellite = new double[num_frames];
            double[] y_satellite = new double[num_frames];
            double[] z_satellite = new double[num_frames];

            for (int j = 0; j < num_frames; j++) {
                r[j] = semi_major_axis * (1 - eccentricity * eccentricity) / (1 + eccentricity * Math.cos(time[j]));
                x_satellite[j] = r[j] * Math.cos(time[j]);
                y_satellite[j] = r[j] * Math.sin(time[j]);
                z_satellite[j] = 0;
            }

            plot.addLinePlot("Satellite Orbit " + (i + 1), x_satellite, y_satellite, z_satellite, "gray");

            // Plotting the satellite markers
            double marker_x = x_satellite[num_frames - 1];
            double marker_y = y_satellite[num_frames - 1];
            double marker_z = z_satellite[num_frames - 1];
            plot.addScatterPlot("Satellite Marker " + (i + 1), new double[]{marker_x}, new double[]{marker_y},
                    new double[]{marker_z}, satellite_color, PlotPanel.CROSS, satellite_radius);
        }

        // Display the plot
        JFrame frame = new JFrame("Satellite Orbits");
        frame.setContentPane(plot);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
