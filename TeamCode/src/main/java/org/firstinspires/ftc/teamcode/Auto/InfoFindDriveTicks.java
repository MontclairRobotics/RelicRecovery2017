package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.montclairrobotics.sprocket.geometry.XY;

/**
 * Created by MHS Robotics on 11/9/2017.
 */

@Autonomous(name = "Ticks")
@Disabled
public class InfoFindDriveTicks extends DefultAutoMode {

    //42.25, 42.0, 42.75
    double ticksPerInch = 1500/42.3;

    @Override
    public void init() {
        autoInit();
    }

    @Override
    public void loop() {
        super.loop();
        if(hardware.getTicks() < 48*ticksPerInch) {
            drive(new XY(0, 0.3), 0);
        } else {
            drive(new XY(0, 0), 0);
        }
        telemetry.addData("ticks", hardware.getTicks());
    }
}
