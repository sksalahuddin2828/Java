import org.opencv.core.*;
import org.opencv.core.Rect;
import org.opencv.video.*;
import org.opencv.videoio.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.highgui.HighGui;
import org.opencv.core.Scalar;
import org.opencv.core.Point;

public class ObjectTracking {
    private static Rect2d bbox;
    private static boolean startTracking = false;
    private static boolean cancelTracking = false;
    private static boolean isTracking = false;
    private static boolean isLost = false;

    public static void main(String[] args) {
        // Load the OpenCV library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Create the video capture
        VideoCapture cap = new VideoCapture(0); // Adjust the camera index if needed

        if (!cap.isOpened()) {
            System.out.println("Failed to open video capture.");
            return;
        }

        // Create a tracker object
        Tracker tracker = TrackerKCF.create();

        // Read the first frame from the video
        Mat frame = new Mat();
        if (!cap.read(frame)) {
            System.out.println("Failed to read frame.");
            return;
        }

        // Select the region of interest (ROI) for tracking
        bbox = HighGui.selectROI("Tracking", frame, false);

        // Initialize the tracker with the selected ROI
        tracker.init(frame, bbox);
        isTracking = true;

        // Main loop for video processing
        while (true) {
            // Read a frame from the video
            if (!cap.read(frame)) {
                System.out.println("Failed to read frame.");
                break;
            }

            // Start or cancel tracking based on user input
            if (startTracking) {
                bbox = HighGui.selectROI("Tracking", frame, false);
                tracker.init(frame, bbox);
                isTracking = true;
                startTracking = false;
            } else if (cancelTracking) {
                tracker.clear();
                isTracking = false;
                cancelTracking = false;
            }

            // Update the tracker with the current frame
            if (isTracking) {
                isLost = !tracker.update(frame, bbox);
            }

            // If tracking is successful, draw the bounding box
            if (isTracking) {
                Imgproc.rectangle(frame, bbox.tl(), bbox.br(), new Scalar(255, 0, 255), 3);
                Imgproc.putText(frame, "Tracking", bbox.tl(), Imgproc.FONT_HERSHEY_SIMPLEX, 0.7, new Scalar(0, 255, 0), 2);
            } else if (isLost) {
                Imgproc.putText(frame, "Lost", new Point(100, 75), Imgproc.FONT_HERSHEY_SIMPLEX, 0.7, new Scalar(0, 0, 255), 2);
            }

            // Calculate and display FPS
            double fps = HighGui.getTickFrequency() / (HighGui.getTickCount() - frame.nativeObjAddr);
            Scalar myColor;
            if (fps > 60) {
                myColor = new Scalar(20, 230, 20);
            } else if (fps > 20) {
                myColor = new Scalar(230, 20, 20);
            } else {
                myColor = new Scalar(20, 20, 230);
            }
            Imgproc.rectangle(frame, new Point(15, 15), new Point(200, 90), new Scalar(255, 0, 255), 2);
            Imgproc.putText(frame, "fps:", new Point(20, 40), Imgproc.FONT_HERSHEY_SIMPLEX, 0.7, new Scalar(255, 0, 255), 2);
            Imgproc.putText(frame, String.valueOf((int) fps), new Point(75, 40), Imgproc.FONT_HERSHEY_SIMPLEX, 0.7, myColor, 2);

            // Display the frame
            HighGui.imshow("Tracking", frame);

            // Exit the loop if 'q' is pressed
            if (HighGui.waitKey(1) == 'q') {
                break;
            }
        }

        // Release the video capture and close windows
        cap.release();
        HighGui.destroyAllWindows();
    }
}
