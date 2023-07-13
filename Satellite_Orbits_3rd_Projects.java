import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class SatelliteOrbits {

    public static void main(String[] args) {
        // Earth parameters
        double earthRadius = 6371;  // Earth radius in kilometers
        double rotationPeriod = 24;  // Earth rotation period in hours

        // Satellite orbit data
        double[][] satellites = {
            {800, 0.1, 45, 0, 0},
            {1000, 0.2, 60, 0, 120},
            {1200, 0.3, 75, 0, 240},
            {1400, 0.15, 30, 0, 60},
            {1600, 0.25, 50, 0, 180}
        };

        // Time array
        int numFrames = 100;
        double[] time = new double[numFrames];
        for (int i = 0; i < numFrames; i++) {
            time[i] = 2 * Math.PI * i / (numFrames - 1);
        }

        // Initialize the plot
        XYSeriesCollection dataset = new XYSeriesCollection();
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Satellite Orbits", "X (km)", "Y (km)", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        JFrame frame = new JFrame("Satellite Orbits");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel chartPanel = new ChartPanel(chart);
        frame.getContentPane().add(chartPanel);

        // Earth surface coordinates
        double[] u = new double[100];
        double[] v = new double[50];
        for (int i = 0; i < u.length; i++) {
            u[i] = 2 * Math.PI * i / (u.length - 1);
        }
        for (int i = 0; i < v.length; i++) {
            v[i] = Math.PI * i / (v.length - 1);
        }

        double[][] xEarth = new double[u.length][v.length];
        double[][] yEarth = new double[u.length][v.length];
        double[][] zEarth = new double[u.length][v.length];
        for (int i = 0; i < u.length; i++) {
            for (int j = 0; j < v.length; j++) {
                xEarth[i][j] = earthRadius * Math.cos(u[i]) * Math.sin(v[j]);
                yEarth[i][j] = earthRadius * Math.sin(u[i]) * Math.sin(v[j]);
                zEarth[i][j] = earthRadius * Math.cos(v[j]);
            }
        }

        // Plot Earth surface
        XYSeries earthSeries = new XYSeries("Earth");
        for (int i = 0; i < u.length; i++) {
            for (int j = 0; j < v.length; j++) {
                earthSeries.add(xEarth[i][j], yEarth[i][j]);
            }
        }
        dataset.addSeries(earthSeries);

        // Initialize satellite lines
        XYSeries[] satelliteSeries = new XYSeries[satellites.length];
        for (int i = 0; i < satellites.length; i++) {
            satelliteSeries[i] = new XYSeries("Satellite " + (i + 1));
            dataset.addSeries(satelliteSeries[i]);
        }

        // Plotting the satellite orbits
        for (int i = 0; i < satellites.length; i++) {
            double semiMajorAxis = satellites[i][0];
            double eccentricity = satellites[i][1];
            double inclination = satellites[i][2];
            double argumentOfPeriapsis = satellites[i][3];
            double ascendingNode = satellites[i][4];

            // Parametric equations for satellite orbit
            double[] r = new double[numFrames];
            double[] xSatellite = new double[numFrames];
            double[] ySatellite = new double[numFrames];
            double[] zSatellite = new double[numFrames];
            for (int j = 0; j < numFrames; j++) {
                r[j] = semiMajorAxis * (1 - eccentricity * eccentricity) / (1 + eccentricity * Math.cos(time[j]));
                xSatellite[j] = r[j] * (Math.cos(ascendingNode) * Math.cos(argumentOfPeriapsis + time[j]) - Math.sin(ascendingNode) * Math.sin(argumentOfPeriapsis + time[j]) * Math.cos(inclination));
                ySatellite[j] = r[j] * (Math.sin(ascendingNode) * Math.cos(argumentOfPeriapsis + time[j]) + Math.cos(ascendingNode) * Math.sin(argumentOfPeriapsis + time[j]) * Math.cos(inclination));
                zSatellite[j] = r[j] * Math.sin(argumentOfPeriapsis + time[j]) * Math.sin(inclination);
            }

            // Plot the satellite orbit
            for (int j = 0; j < numFrames; j++) {
                satelliteSeries[i].add(xSatellite[j], ySatellite[j]);
            }
        }

        frame.setVisible(true);
    }
}
