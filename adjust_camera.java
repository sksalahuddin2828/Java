import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.CvType;
import org.opencv.videoio.VideoCapture;
import org.opencv.highgui.HighGui;

public class VideoCaptureExample {
    public static void main(String[] args) {
        // Load the OpenCV library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Create a VideoCapture object
        VideoCapture capture = new VideoCapture(0); // Adjust the camera index if needed

        if (!capture.isOpened()) {
            System.out.println("Failed to open video capture.");
            return;
        }

        // Create a Mat object to store the frame
        Mat frame = new Mat();

        // Main loop for video capture
        while (true) {
            // Read a frame from the video capture
            if (capture.read(frame)) {
                // Print the shape of the frame (height, width, channels)
                System.out.println("Shape: " + frame.size());

                // Display the frame
                HighGui.imshow("Frame", frame);
            } else {
                System.out.println("Failed to read frame");
                break;
            }

            // Exit the loop if 'q' is pressed
            if (HighGui.waitKey(1) == 'q') {
                break;
            }
        }

        // Release the video capture and close the window
        capture.release();
        HighGui.destroyAllWindows();
    }
}
