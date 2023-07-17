import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TesseractProjection extends JFrame implements GLEventListener {

    // Define tesseract vertices
    private float[][] vertices = {
            {-1, -1, -1, -1, -1},
            {-1, -1, -1, -1, 1},
            {-1, -1, -1, 1, -1},
            {-1, -1, -1, 1, 1},
            {-1, -1, 1, -1, -1},
            {-1, -1, 1, -1, 1},
            {-1, -1, 1, 1, -1},
            {-1, -1, 1, 1, 1},
            {-1, 1, -1, -1, -1},
            {-1, 1, -1, -1, 1},
            {-1, 1, -1, 1, -1},
            {-1, 1, -1, 1, 1},
            {-1, 1, 1, -1, -1},
            {-1, 1, 1, -1, 1},
            {-1, 1, 1, 1, -1},
            {-1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1} // Second dimension vertex
    };

    // Define edges of the tesseract
    private int[][] edges = {
            {0, 1}, {0, 2}, {0, 4}, {1, 3}, {1, 5}, {2, 3}, {2, 6}, {3, 7},
            {4, 5}, {4, 6}, {5, 7}, {6, 7}, {8, 9}, {8, 10}, {8, 12}, {9, 11},
            {9, 13}, {10, 11}, {10, 14}, {11, 15}, {12, 13}, {12, 14}, {13, 15},
            {14, 15}, {0, 8}, {1, 9}, {2, 10}, {3, 11}, {4, 12}, {5, 13}, {6, 14},
            {7, 15}
    };

    public TesseractProjection() {
        setTitle("3D Projection of a Tesseract (4D Hypercube)");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GLProfile glProfile = GLProfile.get(GLProfile.GL2);
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        GLCanvas canvas = new GLCanvas(glCapabilities);
        canvas.addGLEventListener(this);

        getContentPane().add(canvas, BorderLayout.CENTER);

        FPSAnimator animator = new FPSAnimator(canvas, 60);
        animator.start();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                animator.stop();
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TesseractProjection().setVisible(true));
    }

    @Override
    public void init(GLAutoDrawable drawable) {
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-2.0, 2.0, -2.0, 2.0, -2.0, 2.0);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        // Project vertices onto 3D space (select the first three components)
        float[][] projectedVertices = new float[vertices.length][3];
        for (int i = 0; i < vertices.length; i++) {
            projectedVertices[i][0] = (vertices[i][0] + vertices[i][1]) / 2.0f;
            projectedVertices[i][1] = (vertices[i][2] + vertices[i][3]) / 2.0f;
            projectedVertices[i][2] = (vertices[i][0] + vertices[i][2]) / 2.0f;
        }

        // Draw the tesseract edges
        gl.glBegin(GL2.GL_LINES);
        for (int[] edge : edges) {
            float[] v1 = projectedVertices[edge[0]];
            float[] v2 = projectedVertices[edge[1]];
            gl.glVertex3f(v1[0], v1[1], v1[2]);
            gl.glVertex3f(v2[0], v2[1], v2[2]);
        }
        gl.glEnd();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }
}
