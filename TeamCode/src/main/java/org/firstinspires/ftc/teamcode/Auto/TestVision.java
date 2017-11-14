package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

/**
 * Created by MHS Robotics on 11/8/2017.
 */
@Autonomous(name = "Test: Vision")
@Disabled
public class TestVision extends DefaultAutoMode {
    @Override
    public void init() {
        autoInit();
    }

    @Override
    public void loop() {
        switch (state){

            case 0:
                getPictogram();
                break;

            case 1:
                nextState(pictogramDrive(pictogram));
                break;

            case 2:
                telemetry.addData("INFO","Last State Achieved");
                break;
        }
    }
}
