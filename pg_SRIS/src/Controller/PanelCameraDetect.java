package Controller;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import javax.swing.JPanel;
import org.opencv.core.Mat;

/**
 *
 * This class holds a panel that will display the image from the camera.
 *
 * @author Huan Gu
 * @version 1.0.0
 * @since 18/11/2014
 *
 */
public class PanelCameraDetect extends JPanel {

    /**
     *
     * This variable holds an image.
     *
     */
    private BufferedImage image;

    /**
     *
     * This is a default constructor.
     *
     */
    public PanelCameraDetect() {
        super();
    }

    /**
     * This method Converts a Mat into a BufferedImage.
     *
     * @param matBGR the image that will be converted
     * @return BufferedImage of type TYPE_3BYTE_BGR or TYPE_BYTE_GRAY
     */
    public boolean MatToBufferedImage(Mat matBGR) {

        /**
         * This variable holds the start time.
         */
        long startTime = System.nanoTime();
        /**
         *
         * This variable holds the width of the image.
         *
         */
        int width = matBGR.width();
        /**
         *
         * This variable holds the height of the image.
         *
         */
        int height = matBGR.height();
        /**
         *
         * This variable holds the channels of the image.
         *
         */
        int channels = matBGR.channels();
        /**
         *
         * This variable holds the area of the image.
         *
         */
        byte[] sourcePixels = new byte[width * height * channels];
        matBGR.get(0, 0, sourcePixels);
        // create new image and get reference to backing data  
        image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        /**
         *
         * This variable holds the array of pixels
         *
         */
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);
        /**
         *
         * This variable holds the end time.
         *
         */
        long endTime = System.nanoTime();
        //System.out.println(String.format("Elapsed time: %.2f ms", (float) (endTime - startTime) / 1000000));
        return true;
    }

    /**
     *
     * This method paint the component.
     *
     * @param g the component need to be painted
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.image == null) {
            return;
        }
        g.drawImage(this.image, 10, 10, this.image.getWidth(), this.image.getHeight(), null);

    }
}
