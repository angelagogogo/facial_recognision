package Controller;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.IntBuffer;
import static org.bytedeco.javacpp.helper.opencv_imgproc.cvCalcHist;
import static org.bytedeco.javacpp.opencv_contrib.*;
import org.bytedeco.javacpp.opencv_contrib.FaceRecognizer;
import static org.bytedeco.javacpp.opencv_core.*;
import org.bytedeco.javacpp.opencv_core.CvHistogram;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgproc.CV_COMP_CORREL;
import static org.bytedeco.javacpp.opencv_imgproc.cvCompareHist;
import static org.bytedeco.javacpp.opencv_imgproc.cvNormalizeHist;

/**
 * The class below takes two arguments: The path to the directory containing the
 * training faces and the path to the image you want to classify. Not that all
 * images has to be of the same size and that the faces already has to be
 * cropped out of their original images (Take a look here
 * http://fivedots.coe.psu.ac.th/~ad/jg/nui07/index.html if you haven't done the
 * face detection yet).
 *
 * Source: http://pcbje.com/2012/12/doing-face-recognition-with-javacv/
 *
 * @author Petter Christian Bjelland
 * @author Samuel Audet
 * @author Castiel Huang
 * @version 1.3.5
 * @since 15/11/2014
 */
public class JavaCVFaceRecognizer {

    private String trainingDir;
    private String testAdd;

    /**
     * Default Constructor
     */
    public JavaCVFaceRecognizer() {
        trainingDir = "photoes/groupTest";
        testAdd = "photoes/face.jpg";
    }

    /**
     * The Constructor
     *
     * @param photosAddr the address of training photos
     * @param matchPhoto the photo wanted to be matched
     */
    public JavaCVFaceRecognizer(String photosAddr, String matchPhoto) {
        trainingDir = photosAddr;
        testAdd = matchPhoto;
    }

    /**
     * This method is used to search the photo, which is most matched the test
     * photo in database
     */
    public String match() {
        Mat testImage = imread(testAdd, CV_LOAD_IMAGE_GRAYSCALE);
        File root = new File(trainingDir);

        FilenameFilter imgFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                name = name.toLowerCase();
                return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png");
            }
        };

        File[] imageFiles = root.listFiles(imgFilter);
        MatVector images = new MatVector(imageFiles.length);
        Mat labels = new Mat(imageFiles.length, 1, CV_32SC1);
        IntBuffer labelsBuf = labels.getIntBuffer();

        int counter = 0;    //to label the photos

        for (File image : imageFiles) {
            Mat img = imread(image.getAbsolutePath(), CV_LOAD_IMAGE_GRAYSCALE);

            int label = Integer.parseInt("+" + counter);
            images.put(counter, img);
            labelsBuf.put(counter, label);
            counter++;
        }

        //FaceRecognizer faceRecognizer = createFisherFaceRecognizer();
        //FaceRecognizer faceRecognizer = createEigenFaceRecognizer();
        FaceRecognizer faceRecognizer = createLBPHFaceRecognizer();

        faceRecognizer.train(images, labels);

        int predictedLabel = faceRecognizer.predict(testImage);
        //System.out.println(imageFiles[predictedLabel].getAbsolutePath());
        
        return imageFiles[predictedLabel].getAbsolutePath();
    }

    
    /**
     * This method is used to compare how similar two pictures are
     *
     * @param path1 the path of the first picture
     * @param path2 the path of the second picture
     * @return the degree of the similarity
     */
    public double CmpPic(String path1, String path2) {
        int l_bins = 20;
        int hist_size[] = {l_bins};

        float v_ranges[] = {0, 100};
        float ranges[][] = {v_ranges};

        IplImage Image1 = cvLoadImage(path1, CV_LOAD_IMAGE_GRAYSCALE);
        IplImage Image2 = cvLoadImage(path2, CV_LOAD_IMAGE_GRAYSCALE);

        IplImage imageArr1[] = {Image1};
        IplImage imageArr2[] = {Image2};

        CvHistogram Histogram1 = CvHistogram.create(1, hist_size,
                CV_HIST_ARRAY, ranges, 1);
        CvHistogram Histogram2 = CvHistogram.create(1, hist_size,
                CV_HIST_ARRAY, ranges, 1);

        cvCalcHist(imageArr1, Histogram1, 0, null);
        cvCalcHist(imageArr2, Histogram2, 0, null);

        cvNormalizeHist(Histogram1, 100.0);
        cvNormalizeHist(Histogram2, 100.0);

        cvCompareHist(Histogram1, Histogram2, CV_COMP_CORREL);

        return cvCompareHist(Histogram1, Histogram2, CV_COMP_CORREL);
    }

}
