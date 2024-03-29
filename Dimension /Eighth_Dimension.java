import java.awt.*;
import javax.swing.*;

public class HypercubePlot extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    private double[][] vertices = {
        {-1, -1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1,  1},
        {-1, -1, -1, -1, -1, -1,  1, -1},
        {-1, -1, -1, -1, -1, -1,  1,  1},
        {-1, -1, -1, -1, -1,  1, -1, -1},
        {-1, -1, -1, -1, -1,  1, -1,  1},
        {-1, -1, -1, -1, -1,  1,  1, -1},
        {-1, -1, -1, -1, -1,  1,  1,  1},

        {-1, -1, -1, -1,  1, -1, -1, -1},
        {-1, -1, -1, -1,  1, -1, -1,  1},
        {-1, -1, -1, -1,  1, -1,  1, -1},
        {-1, -1, -1, -1,  1, -1,  1,  1},
        {-1, -1, -1, -1,  1,  1, -1, -1},
        {-1, -1, -1, -1,  1,  1, -1,  1},
        {-1, -1, -1, -1,  1,  1,  1, -1},
        {-1, -1, -1, -1,  1,  1,  1,  1},

        {-1, -1, -1,  1, -1, -1, -1, -1},
        {-1, -1, -1,  1, -1, -1, -1,  1},
        {-1, -1, -1,  1, -1, -1,  1, -1},
        {-1, -1, -1,  1, -1, -1,  1,  1},
        {-1, -1, -1,  1, -1,  1, -1, -1},
        {-1, -1, -1,  1, -1,  1, -1,  1},
        {-1, -1, -1,  1, -1,  1,  1, -1},
        {-1, -1, -1,  1, -1,  1,  1,  1},

        {-1, -1,  1, -1, -1, -1, -1, -1},
        {-1, -1,  1, -1, -1, -1, -1,  1},
        {-1, -1,  1, -1, -1, -1,  1, -1},
        {-1, -1,  1, -1, -1, -1,  1,  1},
        {-1, -1,  1, -1, -1,  1, -1, -1},
        {-1, -1,  1, -1, -1,  1, -1,  1},
        {-1, -1,  1, -1, -1,  1,  1, -1},
        {-1, -1,  1, -1, -1,  1,  1,  1},

        {-1, -1,  1, -1,  1, -1, -1, -1},
        {-1, -1,  1, -1,  1, -1, -1,  1},
        {-1, -1,  1, -1,  1, -1,  1, -1},
        {-1, -1,  1, -1,  1, -1,  1,  1},
        {-1, -1,  1, -1,  1,  1, -1, -1},
        {-1, -1,  1, -1,  1,  1, -1,  1},
        {-1, -1,  1, -1,  1,  1,  1, -1},
        {-1, -1,  1, -1,  1,  1,  1,  1},

        {-1, -1,  1,  1, -1, -1, -1, -1},
        {-1, -1,  1,  1, -1, -1, -1,  1},
        {-1, -1,  1,  1, -1, -1,  1, -1},
        {-1, -1,  1,  1, -1, -1,  1,  1},
        {-1, -1,  1,  1, -1,  1, -1, -1},
        {-1, -1,  1,  1, -1,  1, -1,  1},
        {-1, -1,  1,  1, -1,  1,  1, -1},
        {-1, -1,  1,  1, -1,  1,  1,  1},

        {-1, -1,  1,  1,  1, -1, -1, -1},
        {-1, -1,  1,  1,  1, -1, -1,  1},
        {-1, -1,  1,  1,  1, -1,  1, -1},
        {-1, -1,  1,  1,  1, -1,  1,  1},
        {-1, -1,  1,  1,  1,  1, -1, -1},
        {-1, -1,  1,  1,  1,  1, -1,  1},
        {-1, -1,  1,  1,  1,  1,  1, -1},
        {-1, -1,  1,  1,  1,  1,  1,  1},

        {-1,  1, -1, -1, -1, -1, -1, -1},
        {-1,  1, -1, -1, -1, -1, -1,  1},
        {-1,  1, -1, -1, -1, -1,  1, -1},
        {-1,  1, -1, -1, -1, -1,  1,  1},
        {-1,  1, -1, -1, -1,  1, -1, -1},
        {-1,  1, -1, -1, -1,  1, -1,  1},
        {-1,  1, -1, -1, -1,  1,  1, -1},
        {-1,  1, -1, -1, -1,  1,  1,  1},

        {-1,  1, -1, -1,  1, -1, -1, -1},
        {-1,  1, -1, -1,  1, -1, -1,  1},
        {-1,  1, -1, -1,  1, -1,  1, -1},
        {-1,  1, -1, -1,  1, -1,  1,  1},
        {-1,  1, -1, -1,  1,  1, -1, -1},
        {-1,  1, -1, -1,  1,  1, -1,  1},
        {-1,  1, -1, -1,  1,  1,  1, -1},
        {-1,  1, -1, -1,  1,  1,  1,  1},

        {-1,  1, -1,  1, -1, -1, -1, -1},
        {-1,  1, -1,  1, -1, -1, -1,  1},
        {-1,  1, -1,  1, -1, -1,  1, -1},
        {-1,  1, -1,  1, -1, -1,  1,  1},
        {-1,  1, -1,  1, -1,  1, -1, -1},
        {-1,  1, -1,  1, -1,  1, -1,  1},
        {-1,  1, -1,  1, -1,  1,  1, -1},
        {-1,  1, -1,  1, -1,  1,  1,  1},

        {-1,  1,  1, -1, -1, -1, -1, -1},
        {-1,  1,  1, -1, -1, -1, -1,  1},
        {-1,  1,  1, -1, -1, -1,  1, -1},
        {-1,  1,  1, -1, -1, -1,  1,  1},
        {-1,  1,  1, -1, -1,  1, -1, -1},
        {-1,  1,  1, -1, -1,  1, -1,  1},
        {-1,  1,  1, -1, -1,  1,  1, -1},
        {-1,  1,  1, -1, -1,  1,  1,  1},

        {-1,  1,  1, -1,  1, -1, -1, -1},
        {-1,  1,  1, -1,  1, -1, -1,  1},
        {-1,  1,  1, -1,  1, -1,  1, -1},
        {-1,  1,  1, -1,  1, -1,  1,  1},
        {-1,  1,  1, -1,  1,  1, -1, -1},
        {-1,  1,  1, -1,  1,  1, -1,  1},
        {-1,  1,  1, -1,  1,  1,  1, -1},
        {-1,  1,  1, -1,  1,  1,  1,  1},

        {-1,  1,  1,  1, -1, -1, -1, -1},
        {-1,  1,  1,  1, -1, -1, -1,  1},
        {-1,  1,  1,  1, -1, -1,  1, -1},
        {-1,  1,  1,  1, -1, -1,  1,  1},
        {-1,  1,  1,  1, -1,  1, -1, -1},
        {-1,  1,  1,  1, -1,  1, -1,  1},
        {-1,  1,  1,  1, -1,  1,  1, -1},
        {-1,  1,  1,  1, -1,  1,  1,  1},

        {-1,  1,  1,  1,  1, -1, -1, -1},
        {-1,  1,  1,  1,  1, -1, -1,  1},
        {-1,  1,  1,  1,  1, -1,  1, -1},
        {-1,  1,  1,  1,  1, -1,  1,  1},
        {-1,  1,  1,  1,  1,  1, -1, -1},
        {-1,  1,  1,  1,  1,  1, -1,  1},
        {-1,  1,  1,  1,  1,  1,  1, -1},
        {-1,  1,  1,  1,  1,  1,  1,  1},

        { 1, -1, -1, -1, -1, -1, -1, -1},
        { 1, -1, -1, -1, -1, -1, -1,  1},
        { 1, -1, -1, -1, -1, -1,  1, -1},
        { 1, -1, -1, -1, -1, -1,  1,  1},
        { 1, -1, -1, -1, -1,  1, -1, -1},
        { 1, -1, -1, -1, -1,  1, -1,  1},
        { 1, -1, -1, -1, -1,  1,  1, -1},
        { 1, -1, -1, -1, -1,  1,  1,  1},

        { 1, -1, -1, -1,  1, -1, -1, -1},
        { 1, -1, -1, -1,  1, -1, -1,  1},
        { 1, -1, -1, -1,  1, -1,  1, -1},
        { 1, -1, -1, -1,  1, -1,  1,  1},
        { 1, -1, -1, -1,  1,  1, -1, -1},
        { 1, -1, -1, -1,  1,  1, -1,  1},
        { 1, -1, -1, -1,  1,  1,  1, -1},
        { 1, -1, -1, -1,  1,  1,  1,  1},

        { 1, -1, -1,  1, -1, -1, -1, -1},
        { 1, -1, -1,  1, -1, -1, -1,  1},
        { 1, -1, -1,  1, -1, -1,  1, -1},
        { 1, -1, -1,  1, -1, -1,  1,  1},
        { 1, -1, -1,  1, -1,  1, -1, -1},
        { 1, -1, -1,  1, -1,  1, -1,  1},
        { 1, -1, -1,  1, -1,  1,  1, -1},
        { 1, -1, -1,  1, -1,  1,  1,  1},

        { 1, -1,  1, -1, -1, -1, -1, -1},
        { 1, -1,  1, -1, -1, -1, -1,  1},
        { 1, -1,  1, -1, -1, -1,  1, -1},
        { 1, -1,  1, -1, -1, -1,  1,  1},
        { 1, -1,  1, -1, -1,  1, -1, -1},
        { 1, -1,  1, -1, -1,  1, -1,  1},
        { 1, -1,  1, -1, -1,  1,  1, -1},
        { 1, -1,  1, -1, -1,  1,  1,  1},

        { 1, -1,  1, -1,  1, -1, -1, -1},
        { 1, -1,  1, -1,  1, -1, -1,  1},
        { 1, -1,  1, -1,  1, -1,  1, -1},
        { 1, -1,  1, -1,  1, -1,  1,  1},
        { 1, -1,  1, -1,  1,  1, -1, -1},
        { 1, -1,  1, -1,  1,  1, -1,  1},
        { 1, -1,  1, -1,  1,  1,  1, -1},
        { 1, -1,  1, -1,  1,  1,  1,  1},

        { 1, -1,  1,  1, -1, -1, -1, -1},
        { 1, -1,  1,  1, -1, -1, -1,  1},
        { 1, -1,  1,  1, -1, -1,  1, -1},
        { 1, -1,  1,  1, -1, -1,  1,  1},
        { 1, -1,  1,  1, -1,  1, -1, -1},
        { 1, -1,  1,  1, -1,  1, -1,  1},
        { 1, -1,  1,  1, -1,  1,  1, -1},
        { 1, -1,  1,  1, -1,  1,  1,  1},

        { 1, -1,  1,  1,  1, -1, -1, -1},
        { 1, -1,  1,  1,  1, -1, -1,  1},
        { 1, -1,  1,  1,  1, -1,  1, -1},
        { 1, -1,  1,  1,  1, -1,  1,  1},
        { 1, -1,  1,  1,  1,  1, -1, -1},
        { 1, -1,  1,  1,  1,  1, -1,  1},
        { 1, -1,  1,  1,  1,  1,  1, -1},
        { 1, -1,  1,  1,  1,  1,  1,  1},

        { 1,  1, -1, -1, -1, -1, -1, -1},
        { 1,  1, -1, -1, -1, -1, -1,  1},
        { 1,  1, -1, -1, -1, -1,  1, -1},
        { 1,  1, -1, -1, -1, -1,  1,  1},
        { 1,  1, -1, -1, -1,  1, -1, -1},
        { 1,  1, -1, -1, -1,  1, -1,  1},
        { 1,  1, -1, -1, -1,  1,  1, -1},
        { 1,  1, -1, -1, -1,  1,  1,  1},

        { 1,  1, -1, -1,  1, -1, -1, -1},
        { 1,  1, -1, -1,  1, -1, -1,  1},
        { 1,  1, -1, -1,  1, -1,  1, -1},
        { 1,  1, -1, -1,  1, -1,  1,  1},
        { 1,  1, -1, -1,  1,  1, -1, -1},
        { 1,  1, -1, -1,  1,  1, -1,  1},
        { 1,  1, -1, -1,  1,  1,  1, -1},
        { 1,  1, -1, -1,  1,  1,  1,  1},

        { 1,  1, -1,  1, -1, -1, -1, -1},
        { 1,  1, -1,  1, -1, -1, -1,  1},
        { 1,  1, -1,  1, -1, -1,  1, -1},
        { 1,  1, -1,  1, -1, -1,  1,  1},
        { 1,  1, -1,  1, -1,  1, -1, -1},
        { 1,  1, -1,  1, -1,  1, -1,  1},
        { 1,  1, -1,  1, -1,  1,  1, -1},
        { 1,  1, -1,  1, -1,  1,  1,  1},

        { 1,  1,  1, -1, -1, -1, -1, -1},
        { 1,  1,  1, -1, -1, -1, -1,  1},
        { 1,  1,  1, -1, -1, -1,  1, -1},
        { 1,  1,  1, -1, -1, -1,  1,  1},
        { 1,  1,  1, -1, -1,  1, -1, -1},
        { 1,  1,  1, -1, -1,  1, -1,  1},
        { 1,  1,  1, -1, -1,  1,  1, -1},
        { 1,  1,  1, -1, -1,  1,  1,  1},

        { 1,  1,  1, -1,  1, -1, -1, -1},
        { 1,  1,  1, -1,  1, -1, -1,  1},
        { 1,  1,  1, -1,  1, -1,  1, -1},
        { 1,  1,  1, -1,  1, -1,  1,  1},
        { 1,  1,  1, -1,  1,  1, -1, -1},
        { 1,  1,  1, -1,  1,  1, -1,  1},
        { 1,  1,  1, -1,  1,  1,  1, -1},
        { 1,  1,  1, -1,  1,  1,  1,  1},

        { 1,  1,  1,  1, -1, -1, -1, -1},
        { 1,  1,  1,  1, -1, -1, -1,  1},
        { 1,  1,  1,  1, -1, -1,  1, -1},
        { 1,  1,  1,  1, -1, -1,  1,  1},
        { 1,  1,  1,  1, -1,  1, -1, -1},
        { 1,  1,  1,  1, -1,  1, -1,  1},
        { 1,  1,  1,  1, -1,  1,  1, -1},
        { 1,  1,  1,  1, -1,  1,  1,  1},

        { 1,  1,  1,  1,  1, -1, -1, -1},
        { 1,  1,  1,  1,  1, -1, -1,  1},
        { 1,  1,  1,  1,  1, -1,  1, -1},
        { 1,  1,  1,  1,  1, -1,  1,  1},
        { 1,  1,  1,  1,  1,  1, -1, -1},
        { 1,  1,  1,  1,  1,  1, -1,  1},
        { 1,  1,  1,  1,  1,  1,  1, -1},
        { 1,  1,  1,  1,  1,  1,  1,  1},
    };

    private double[][] projectedVertices;

    private double theta = 0.01;
    private double phi = 0.01;

    public HypercubePlot() {
        projectedVertices = new double[vertices.length][2];
    }

    public void rotateHypercube() {
        double cosTheta = Math.cos(theta);
        double sinTheta = Math.sin(theta);
        double cosPhi = Math.cos(phi);
        double sinPhi = Math.sin(phi);

        for (int i = 0; i < vertices.length; i++) {
            double x = vertices[i][0];
            double y = vertices[i][1];
            double z = vertices[i][2];

            // Apply rotation around the x-axis (theta)
            double rotatedX = x * cosTheta - y * sinTheta;
            double rotatedY = x * sinTheta + y * cosTheta;
            double rotatedZ = z;

            // Apply rotation around the y-axis (phi)
            double tempX = rotatedX;
            double tempY = rotatedY;

            rotatedX = tempX * cosPhi - rotatedZ * sinPhi;
            rotatedZ = tempX * sinPhi + rotatedZ * cosPhi;
            rotatedY = tempY;

            projectedVertices[i][0] = rotatedX / (4 - rotatedZ);
            projectedVertices[i][1] = rotatedY / (4 - rotatedZ);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.translate(WIDTH / 2, HEIGHT / 2);

        for (int i = 0; i < projectedVertices.length; i++) {
            for (int j = 0; j < projectedVertices.length; j++) {
                if (i != j && edgeConnected(i, j)) {
                    int x1 = (int) (projectedVertices[i][0] * 200);
                    int y1 = (int) (projectedVertices[i][1] * 200);
                    int x2 = (int) (projectedVertices[j][0] * 200);
                    int y2 = (int) (projectedVertices[j][1] * 200);
                    g2d.drawLine(x1, y1, x2, y2);
                }
            }
        }
    }

    private boolean edgeConnected(int i, int j) {
        int count = 0;
        for (int k = 0; k < vertices[0].length; k++) {
            if (vertices[i][k] != vertices[j][k]) {
                count++;
            }
        }
        return count == 2;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Hypercube Plot");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            HypercubePlot hypercubePlot = new HypercubePlot();
            frame.add(hypercubePlot);

            frame.pack();
            frame.setVisible(true);

            while (true) {
                hypercubePlot.rotateHypercube();
                hypercubePlot.repaint();

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}


//----------------------------------------------------------------------------------------------------------------------------------------------------
//---------To use this class and see the hypercube animation, you can create a JFrame and add an instance of HypercubePlot to it. For example---------
//----------------------------------------------------------------------------------------------------------------------------------------------------


import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Hypercube Plot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        HypercubePlot hypercubePlot = new HypercubePlot();
        frame.add(hypercubePlot);

        frame.setSize(HypercubePlot.WIDTH, HypercubePlot.HEIGHT);
        frame.setVisible(true);

        while (true) {
            hypercubePlot.rotateHypercube();
            hypercubePlot.repaint();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

