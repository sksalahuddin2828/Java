import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.videoio.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.highgui.HighGui;

public class WebcamCapture {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        VideoCapture cap = new VideoCapture(0);

        if (!cap.isOpened()) {
            System.out.println("Failed to open the camera!");
            return;
        }

        make_1080p(cap);
        // make_720p(cap);
        // make_480p(cap);
        // change_res(cap, 640, 480);

        while (true) {
            Mat frame = new Mat();
            cap.read(frame);

            frame = rescaleFrame(frame, 30);
            HighGui.imshow("frame", frame);

            Mat frame2 = rescaleFrame(frame, 500);
            HighGui.imshow("frame2", frame2);

            if (HighGui.waitKey(20) == 'q') {
                break;
            }
        }

        cap.release();
        HighGui.destroyAllWindows();
    }

    public static void make_1080p(VideoCapture cap) {
        cap.set(3, 1920);
        cap.set(4, 1080);
    }

    public static void make_720p(VideoCapture cap) {
        cap.set(3, 1020);
        cap.set(4, 720);
    }

    public static void make_480p(VideoCapture cap) {
        cap.set(3, 640);
        cap.set(4, 480);
    }

    public static void change_res(VideoCapture cap, int width, int height) {
        cap.set(3, width);
        cap.set(4, height);
    }

    public static Mat rescaleFrame(Mat frame, double percent) {
        int width = (int) (frame.width() * percent / 100);
        int height = (int) (frame.height() * percent / 100);
        Size dim = new Size(width, height);
        Mat resizedFrame = new Mat();
        Imgproc.resize(frame, resizedFrame, dim, 0, 0, Imgproc.INTER_AREA);
        return resizedFrame;
    }
}
