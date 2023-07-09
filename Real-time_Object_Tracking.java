import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.*;
import org.opencv.videoio.VideoCapture;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class ObjectTracking {
    private static JFrame frame;
    private static JLabel imageLabel;
    private static Rect2d bbox;
    private static boolean startTracking = false;
    private static boolean cancelTracking = false;
    private static boolean isTracking = false;
    private static boolean isLost = false;
    private static boolean isQuit = false;

    public static void main(String[] args) {
        // Load the OpenCV library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Create the video capture
        VideoCapture capture = new VideoCapture(0);
        if (!capture.isOpened()) {
            System.out.println("Failed to open video capture.");
            return;
        }

        // Create the frame and image label for displaying the video
        frame = new JFrame("Object Tracking");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        imageLabel = new JLabel();
        frame.getContentPane().add(imageLabel, BorderLayout.CENTER);
        frame.setVisible(true);

        // Add key listener to the frame for capturing key events
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    startTracking = true;
                } else if (e.getKeyChar() == KeyEvent.VK_C) {
                    cancelTracking = true;
                } else if (e.getKeyChar() == KeyEvent.VK_Q) {
                    isQuit = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        // Create the tracker
        Tracker tracker = TrackerKCF.create();

        // Main loop for video processing
        while (true) {
            // Read a frame from the video
            Mat frameMat = new Mat();
            if (capture.read(frameMat)) {
                // Create a copy of the frame for drawing
                Mat drawMat = frameMat.clone();

                // Start or cancel tracking based on user input
                if (startTracking) {
                    bbox = Imgproc.selectROI("Tracking", frameMat, false);
                    tracker.init(frameMat, bbox);
                    isTracking = true;
                    startTracking = false;
                } else if (cancelTracking) {
                    tracker.clear();
                    isTracking = false;
                    cancelTracking = false;
                }

                // Update the tracker with the current frame
                if (isTracking) {
                    isLost = !tracker.update(frameMat, bbox);
                }

                // Draw bounding box and status on the image
                if (isTracking) {
                    Imgproc.rectangle(drawMat, bbox.tl(), bbox.br(), new Scalar(0, 255, 255), 3);
                    Imgproc.putText(drawMat, "Tracking", bbox.tl(), Imgproc.FONT_HERSHEY_SIMPLEX, 0.9, new Scalar(0, 255, 255), 2);
                } else if (isLost) {
                    Imgproc.putText(drawMat, "Lost", new Point(20, 40), Imgproc.FONT_HERSHEY_SIMPLEX, 0.9, new Scalar(0, 0, 255), 2);
                }

                // Display the image
                BufferedImage image = MatToBufferedImage(drawMat);
                ImageIcon imageIcon = new ImageIcon(image);
                imageLabel.setIcon(imageIcon);
                frame.pack();

                // Exit the loop if 'q' is pressed or window is closed
                if (isQuit || frame.isVisible()) {
                    break;
                }
            }
        }

        // Release the video capture and close windows
        capture.release();
        frame.dispose();
    }

    // Convert OpenCV Mat to BufferedImage
    private static BufferedImage MatToBufferedImage(Mat mat) {
        int width = mat.cols();
        int height = mat.rows();
        int channels = mat.channels();
        byte[] sourceData = new byte[width * height * channels];
        mat.get(0, 0, sourceData);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        byte[] targetData = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(sourceData, 0, targetData, 0, sourceData.length);

        return image;
    }
}
