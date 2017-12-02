package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

/**
 * Created by Montclair Robotics on 11/13/17.
 * @Author:Will
 * */
@Autonomous(name = "Test: Vision")
@Disabled
public class TestVuforia extends DefaultAutoMode {
    @Override
    public void init() {
        autoInit();
    }

    @Override
    public void loop() {
        switch (state){

            case 0:
                nextState(getPictogram());
                break;

            case 1:
                pictogramDrive(pictogram);
                break;

            case 2:
                telemetry.addData("INFO","Last State Achieved");
                break;
        }
    }
}
