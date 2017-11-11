package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.montclairrobotics.sprocket.geometry.XY;

/**
 * Created by MHS Robotics on 11/8/2017.
 */
@Autonomous(name = "Drive")
@Disabled
public class TestDrive extends DefultAutoMode {

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
                hardware.lift.closeBottom();
                hardware.glyphLeft.setPower(-0.75);
                hardware.glyphRight.setPower(0.75);
                nextState(pause(10));
                break;

            case 1:
                hardware.glyphLeft.setPower(0);
                hardware.glyphRight.setPower(0);
                nextState(pause(1));
                break;

            case 2:
                nextState(autoDrive(new XY(0,24),0.5));
                break;

            case 3:
                hardware.glyphLeft.setPower(0.75);
                hardware.glyphRight.setPower(-0.75);
                nextState(pause(2));
                break;

            case 4:
                hardware.glyphLeft.setPower(0);
                hardware.glyphRight.setPower(0);
                nextState(pause(1));
                break;

            case 5:
                hardware.lift.openBottom();
                nextState(pause(5));
                break;

            case 6:
                telemetry.addData("INFO", LSA);
                break;

        }
    }
}
