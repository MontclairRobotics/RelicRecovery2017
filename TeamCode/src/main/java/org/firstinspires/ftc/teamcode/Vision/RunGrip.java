package org.firstinspires.ftc.teamcode.Vision;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.Vision.GripPipeline;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.imgcodecs.Imgcodecs;

import java.nio.ByteBuffer;

/**
 * Created by Will on 11/15/17.
 */

public class RunGrip extends Thread{

    VuforiaLocalizer vuforia;
    int blobsDetected;
    Object lock = new Object();

    public void startVuforia(){
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = "AVhGMov/////AAAAGUk16JthIkWst4BeQ3creo+NTUF+BxVD6iSoptSHES0tn3qxxl8EoEMBtZfR9lS5zeb8wa5m+susmQEk+ELlMZvkhfCo5hwgtQVQo95VhTaduQjLatwooAcigCDfAK19KDQPw7O4/Q0p0G79ni5UlnYrw/lF1ZC2iv+41EGTjOTT8yC6wWMzzi2ugWGtIYs9Qy62b9S+Jr2/JjoqtzoaeUX7cmshji5IRmPojALj71tKJb1Gay4XcCb7fMMkO10SDaY84E66Vt0aEhgyA4VY/ASABIEEBlpDoq7N/tTSMxDfahX0xP76BXUSNEug7Y378HPg9siRGv5AQns3Y44RfPqBu6kQN1yDXb+43Zl3ZkzF";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
    }

    //https://ftcforum.usfirst.org/forum/ftc-technology/android-studio/6086-grabbing-frames-from-vuforia-for-analysis
    public Mat getMat(VuforiaLocalizer vuforia) throws InterruptedException {

        vuforia.setFrameQueueCapacity(1);
        VuforiaLocalizer.CloseableFrame frame = new VuforiaLocalizer.CloseableFrame(vuforia.getFrameQueue().take());

        long numImages = frame.getNumImages();

        Image rgb = null;
        for (int i = 0; i < numImages; i++) {
            if (frame.getImage(i).getFormat() == PIXEL_FORMAT.RGB565) {
                rgb = frame.getImage(i);
                break;
            }
        }

        frame.close();

        https://stackoverflow.com/questions/21113190/how-to-get-the-mat-object-from-the-byte-in-opencv-android
        if(rgb==null) return null;
        ByteBuffer pixels = rgb.getPixels();
        byte[] bytes = pixels.array();
        Mat mat = Imgcodecs.imdecode(new MatOfByte(bytes), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
        return mat;
    }

    public MatOfKeyPoint runOpenCV() throws InterruptedException {
        GripPipeline gripPipeline = new GripPipeline();
        return gripPipeline.process(getMat(vuforia));
    }

    public void run(){
        int x = 0;
        try {
            x = runOpenCV().toArray().length;
//            x = runOpenCV().toList().size();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (lock){
            blobsDetected = x;
        }
    }

    public int getBlobsDetected(){
        synchronized (lock){
            return blobsDetected;
        }
    }


}
