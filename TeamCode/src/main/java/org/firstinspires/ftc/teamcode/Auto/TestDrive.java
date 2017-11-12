package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.montclairrobotics.sprocket.geometry.XY;

/**
 * Created by MHS Robotics on 11/8/2017.
 */
@Autonomous(name = "Test: Drive and Turn")
@Disabled
public class TestDrive extends DefaultAutoMode {

    @Override
    public void init() {
        autoInit();
    }

    @Override
    public void loop() {
        telemetry.addData("Time", getMillis());
        telemetry.addData("state", state);
        switch(state){
            case 0:
                nextState(autoDrive(new XY(0,12),1),1);
                break;

            case 1:
                nextState(autoTurn(90,1),2);

            case 2:
                telemetry.addData("INFO", LSA);
                break;
        }
    }
}
