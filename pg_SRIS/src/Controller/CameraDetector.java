package Controller;

import java.util.Calendar;
import java.util.GregorianCalendar;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

/**
 * This class opens the camera, take a picture and detect the face in the
 * picture.
 *
 * @author Huan Gu
 * @version 1.0.0
 * @since 18/11/2014
 */
public class CameraDetector {

    /**
     * This variable holds the time the photo taken.
     *
     */
    public static String time;
    /**
     *
     * This variable holds the frame that will show the image
     *
     */
    private static final CameraFrame frame = new CameraFrame();
    /**
     *
     * This variable holds the panel that will show the image.
     *
     */
    private static final PanelCameraDetect my_panel = new PanelCameraDetect();
    /**
     *
     * this variable control the open/close of the camera.
     *
     */
    public static int flag = 0;

    /**
     * This method is used to open camera and then detect face
     */
    public static void camera() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        frame.setVisible(true);
        frame.add(my_panel);

        Thread t = new Thread(new Runnable() {
            /**
             *
             * holds a processor object
             *
             */

            private Processor my_processor = new Processor();
            /**
             * hold a camera object
             *
             */
            private VideoCapture capture = new VideoCapture(0);

            @Override
            public void run() {
                //holds an frame object    
                Mat webcam_image = new Mat();

                //open camera
                if (capture.isOpened()) {
                    while (flag == 0) {
                        //capture the instant image.
                        capture.read(webcam_image);
                        if (!webcam_image.empty()) {
                            my_panel.setSize(webcam_image.width(), webcam_image.height());
                            // Apply the classifier to the captured image  
                            webcam_image = my_processor.detect(webcam_image);
                            time = time();
                            frame.setP(webcam_image);
                            //-- 4. Display the image  
                            my_panel.MatToBufferedImage(webcam_image); // We could look at the error...  
                            my_panel.repaint();
                        } else {
                            System.out.println(" --(!) No captured frame -- Break!");
                            break;
                        }
                    }
                }
                capture.release();
            }
        });
        t.start();

        //holds the camera that need to be open. 
        //     VideoCapture capture = new VideoCapture(0);
    }

    /**
     *
     * This method saved the picture taken.
     *
     * @param image the image detected by the camera
     * @return the file name of the picture taken
     */
    public static String savep(Mat image) {
        String fileName = "photoes/face/" + time + ".jpg";
        Highgui.imwrite(fileName, image);
        return fileName;
    }

    /**
     *
     * This method detects the face in the picture.
     *
     * @param fileName the file used for detection
     */
    public static void detectp(String fileName) {
        //holds the loaded classifier. 
        CascadeClassifier faceDetector = new CascadeClassifier("opencv/haarcascade_frontalface_alt.xml");

        //holds the image read. 
        Mat image = Highgui.imread(fileName);

        //holds the face detected. 
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

        for (Rect rect : faceDetections.toArray()) {
            //set the image into the same size.
            Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x
                    + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));

            //holds image after cut. 
            Mat sub = image.submat(rect);

            //holds the picture after the cut
            Mat mat = new Mat();

            //holds the size we need.
            Size size = new Size(134, 148);
            Imgproc.resize(sub, mat, size);

            Highgui.imwrite("photoes/face.jpg", mat);

        }
    }

    /**
     *
     * This method holds the current time.
     *
     * @return the current time
     */
    public static String time() {
        //holds a calendar object.
        Calendar calendar = new GregorianCalendar();

        //holds the current year.         
        int year = calendar.get(Calendar.YEAR);

        //holds the current month. 
        int month = calendar.get(Calendar.MONTH) + 1;

        //holds the current day. 
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //holds the current hour.
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        //holds the current minute.
        int min = calendar.get(Calendar.MINUTE);

        //holds the current second.        
        int second = calendar.get(Calendar.SECOND);

        //holds the current time.
        time = "" + day + "_" + month + "_" + year + "_" + hour + "_" + min + "_" + second;
        return time;
    }

}
