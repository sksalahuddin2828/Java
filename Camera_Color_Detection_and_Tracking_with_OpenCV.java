import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.CvType;
import org.opencv.core.CvType.CV_8U;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Color;

public class HSVColorDetection {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private static int frameWidth = 640;
    private static int frameHeight = 480;
    private static Scalar lower = new Scalar(0, 0, 0);
    private static Scalar upper = new Scalar(179, 255, 255);

    private static void emptyFunction(int a) {
        int threshold1 = cv2.getTrackbarPos("Threshold1", "Parameters");
        int threshold2 = cv2.getTrackbarPos("Threshold2", "Parameters");
        int area = cv2.getTrackbarPos("Area", "Parameters");
        System.out.println("Threshold1: " + threshold1);
        System.out.println("Threshold2: " + threshold2);
        System.out.println("Area: " + area);
    }

    public static void main(String[] args) {
        VideoCapture cap = new VideoCapture(0);
        cap.set(3, frameWidth);
        cap.set(4, frameHeight);

        JFrame frame = new JFrame("HSV");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 240);
        frame.setLayout(null);

        JLabel imageLabel = new JLabel();
        imageLabel.setBounds(0, 0, 640, 240);
        frame.add(imageLabel);
        frame.setVisible(true);

        Mat frameMat = new Mat();
        Mat imgHsv = new Mat();
        Mat mask = new Mat();
        Mat result = new Mat();
        Mat hStack = new Mat();

        while (true) {
            cap.read(frameMat);

            Imgproc.cvtColor(frameMat, imgHsv, Imgproc.COLOR_BGR2HSV);

            int hMin = 0;  // Default value
            int hMax = 179;  // Default value
            int sMin = 0;  // Default value
            int sMax = 255;  // Default value
            int vMin = 0;  // Default value
            int vMax = 255;  // Default value

            lower.set(new double[]{hMin, sMin, vMin});
            upper.set(new double[]{hMax, sMax, vMax});

            Core.inRange(imgHsv, lower, upper, mask);
            Core.bitwise_and(frameMat, frameMat, result, mask);

            Imgproc.cvtColor(mask, mask, Imgproc.COLOR_GRAY2BGR);

            hStack = new Mat();
            Core.hconcat(new Mat[]{frameMat, mask, result}, hStack);

            // Convert the OpenCV Mat to a Java BufferedImage
            MatOfByte matOfByte = new MatOfByte();
            Imgcodecs.imencode(".jpg", hStack, matOfByte);
            byte[] byteArray = matOfByte.toArray();
            BufferedImage bufImage = null;
            try {
                InputStream in = new ByteArrayInputStream(byteArray);
                bufImage = ImageIO.read(in);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Display the BufferedImage in the JFrame
            imageLabel.setIcon(new ImageIcon(bufImage));

            if (frameMat.empty()) {
                System.out.println("Failed to capture frame from the camera.");
                break;
            }

            if (frameMat.empty()) {
                System.out.println("Failed to capture frame from the camera.");
                break;
            }

            if (Core.waitKey(1) == 'q') {
                break;
            }
        }

        cap.release();
        Core.destroyAllWindows();
    }
}
