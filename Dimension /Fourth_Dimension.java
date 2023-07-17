import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TesseractPlot extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    private double[][] vertices = { { -1, -1, -1, -1 }, { -1, -1, -1, 1 }, { -1, -1, 1, -1 }, { -1, -1, 1, 1 },
            { -1, 1, -1, -1 }, { -1, 1, -1, 1 }, { -1, 1, 1, -1 }, { -1, 1, 1, 1 }, { 1, -1, -1, -1 },
            { 1, -1, -1, 1 }, { 1, -1, 1, -1 }, { 1, -1, 1, 1 }, { 1, 1, -1, -1 }, { 1, 1, -1, 1 }, { 1, 1, 1, -1 },
            { 1, 1, 1, 1 } };

    private int[][] edges = { { 0, 1 }, { 0, 2 }, { 0, 4 }, { 1, 3 }, { 1, 5 }, { 2, 3 }, { 2, 6 }, { 3, 7 }, { 4, 5 },
            { 4, 6 }, { 5, 7 }, { 6, 7 }, { 8, 9 }, { 8, 10 }, { 8, 12 }, { 9, 11 }, { 9, 13 }, { 10, 11 }, { 10, 14 },
            { 11, 15 }, { 12, 13 }, { 12, 14 }, { 13, 15 }, { 14, 15 }, { 0, 8 }, { 1, 9 }, { 2, 10 }, { 3, 11 },
            { 4, 12 }, { 5, 13 }, { 6, 14 }, { 7, 15 } };

    private double[][] projectedVertices;
    private String[] labels;

    private double[][] rotationMatrix = { { Math.cos(Math.PI / 4), 0, -Math.sin(Math.PI / 4), 0 },
            { 0, Math.cos(Math.PI / 4), 0, -Math.sin(Math.PI / 4) },
            { Math.sin(Math.PI / 4), 0, Math.cos(Math.PI / 4), 0 },
            { 0, Math.sin(Math.PI / 4), 0, Math.cos(Math.PI / 4) } };

    public TesseractPlot() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        calculateProjectedVertices();
        labels = new String[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            labels[i] = "";
            for (int j = 0; j < vertices[i].length; j++) {
                labels[i] += String.valueOf(vertices[i][j]);
            }
        }
    }

    private void calculateProjectedVertices() {
        projectedVertices = new double[vertices.length][3];
        for (int i = 0; i < vertices.length; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 4; k++) {
                    projectedVertices[i][j] += vertices[i][k] * rotationMatrix[k][j];
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int[] edge : edges) {
            int x1 = (int) (projectedVertices[edge[0]][0] * WIDTH / 4 + WIDTH / 2);
            int y1 = (int) (projectedVertices[edge[0]][1] * HEIGHT / 4 + HEIGHT / 2);
            int x2 = (int) (projectedVertices[edge[1]][0] * WIDTH / 4 + WIDTH / 2);
            int y2 = (int) (projectedVertices[edge[1]][1] * HEIGHT / 4 + HEIGHT / 2);
            g.drawLine(x1, y1, x2, y2);
        }

        g.setColor(Color.BLACK);
        for (int i = 0; i < projectedVertices.length; i++) {
            int x = (int) (projectedVertices[i][0] * WIDTH / 4 + WIDTH / 2);
            int y = (int) (projectedVertices[i][1] * HEIGHT / 4 + HEIGHT / 2);
            g.fillOval(x - 5, y - 5, 10, 10);
            g.drawString(labels[i], x, y);
        }

        g.setColor(Color.GRAY);
        for (int i = 0; i < projectedVertices.length; i++) {
            for (int j = i + 1; j < projectedVertices.length; j++) {
                int x1 = (int) (projectedVertices[i][0] * WIDTH / 4 + WIDTH / 2);
                int y1 = (int) (projectedVertices[i][1] * HEIGHT / 4 + HEIGHT / 2);
                int x2 = (int) (projectedVertices[j][0] * WIDTH / 4 + WIDTH / 2);
                int y2 = (int) (projectedVertices[j][1] * HEIGHT / 4 + HEIGHT / 2);
                g.drawLine(x1, y1, x2, y2);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tesseract Plot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new TesseractPlot());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
