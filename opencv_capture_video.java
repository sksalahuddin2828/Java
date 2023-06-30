import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;
import org.opencv.imgproc.Imgproc;
import org.opencv.highgui.HighGui;

public class WebcamVideoCapture {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        String filename = "video.avi"; // Change the filename and format here (e.g., "video.mp4")
        double framesPerSecond = 24.0;
        String res = "720p"; // Change the resolution here (options: "480p", "720p", "1080p", "4k")

        VideoCapture cap = new VideoCapture(0);
        VideoWriter writer = createVideoWriter(filename, framesPerSecond, res, cap);

        if (!cap.isOpened()) {
            System.out.println("Failed to open the camera!");
            return;
        }

        while (true) {
            Mat frame = new Mat();
            cap.read(frame);

            writer.write(frame);
            HighGui.imshow("frame", frame);

            if (HighGui.waitKey(1) == 'q') {
                break;
            }
        }

        cap.release();
        writer.release();
        HighGui.destroyAllWindows();
    }

    public static VideoWriter createVideoWriter(String filename, double framesPerSecond, String res, VideoCapture cap) {
        int fourcc = VideoWriter.fourcc('X', 'V', 'I', 'D'); // Change the fourcc code as per desired video format
        Size frameSize = getDims(res);
        VideoWriter writer = new VideoWriter(filename, fourcc, framesPerSecond, frameSize, true);

        if (!writer.isOpened()) {
            System.out.println("Failed to create video writer!");
            return null;
        }

        changeRes(cap, frameSize);
        return writer;
    }

    public static void changeRes(VideoCapture cap, Size frameSize) {
        cap.set(3, frameSize.width);
        cap.set(4, frameSize.height);
    }

    public static Size getDims(String res) {
        Size size = new Size(640, 480); // Default resolution: 480p

        switch (res) {
            case "480p":
                size = new Size(640, 480);
                break;
            case "720p":
                size = new Size(1280, 720);
                break;
            case "1080p":
                size = new Size(1920, 1080);
                break;
            case "4k":
                size = new Size(3840, 2160);
                break;
        }

        return size;
    }
}
