package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import jav

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

/**
 * Created by Hymowitz on 11/15/2017.
 */

public class VisionRobot extends OpMode {


    public Object getImage(VuforiaLocalizer vuforia) throws InterruptedException {

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

        if(rgb==null) return null;
        rgb.getPixels();
    }

    @Override
    public void loop()
    {

    }
}
