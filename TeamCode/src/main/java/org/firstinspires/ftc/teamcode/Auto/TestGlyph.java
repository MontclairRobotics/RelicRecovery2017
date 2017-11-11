package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by MHS Robotics on 11/9/2017.
 */
@Autonomous(name = "Glyph")
public class TestGlyph extends DefultAutoMode {

    @Override
    public void init() {
        autoInit();
    }

    @Override
    public void loop() {
        switch (state){
            case 0:
                hardware.lift.closeAll();
                hardware.glyphLeft.setPower(0.75);
                hardware.glyphRight.setPower(0.75);
                nextState(pause(PAUSE_TIME));
                break;
        }
    }
}
