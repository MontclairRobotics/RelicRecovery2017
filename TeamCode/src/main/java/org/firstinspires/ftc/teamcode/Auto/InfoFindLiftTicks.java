package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by MHS Robotics on 11/10/2017.
 */
@Autonomous(name = "Info: Lift Ticks")
public class InfoFindLiftTicks extends DefultAutoMode {

    @Override
    public void init() {
        autoInit();
    }

    @Override
    public void loop() {
        hardware.glyphLeft.setPower(0.5);
        hardware.glyphRight.setPower(0.5);
        telemetry.addData("Info",hardware.getLiftTicks());
    }
}
