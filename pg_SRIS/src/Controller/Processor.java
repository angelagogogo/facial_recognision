package Controller;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

/**
 *
 * This class detects faces instantly.
 *
 * @author Huan Gu
 * @version 1.0.0
 * @since 18/11/2014
 */
public class Processor {

    /**
     *
     * This variable holds the classifier.
     *
     */
    private CascadeClassifier face_cascade;

    /**
     *
     * This constructor constructs a object that can detect faces
     *
     */
    public Processor() {
        face_cascade = new CascadeClassifier("opencv/haarcascade_frontalface_alt.xml");
        if (face_cascade.empty()) {
            System.out.println("--(!)Error loading A\n");
        } else {
            System.out.println("Face classifier loooaaaaaded up...");
        }
    }

    /**
     *
     * This method detects face instantly
     *
     * @param inputframe the image that is used for detection
     * @return the image that is detected.
     */
    public Mat detect(Mat inputframe) {
        /**
         *
         * This variable holds the image that is used for detection
         *
         */
        Mat mRgba = new Mat();
        /**
         *
         * This variable holds the image that is used for detection
         *
         */
        Mat mGrey = new Mat();
        /**
         *
         * This variable holds the image from the detection.
         *
         */
        MatOfRect faces = new MatOfRect();
        inputframe.copyTo(mRgba);
        inputframe.copyTo(mGrey);
        Imgproc.cvtColor(mRgba, mGrey, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(mGrey, mGrey);
        face_cascade.detectMultiScale(mGrey, faces);
        //System.out.println(String.format("Detected %s faces", faces.toArray().length));
        for (Rect rect : faces.toArray()) {
            Core.rectangle(mRgba, new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 255, 255));
        }
        return mRgba;
    }
}
