package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by MHS Robotics on 11/8/2017.
 */
@Autonomous(name = "Test: Color Sensor")
public class TestColor extends DefaultAutoMode {
    @Override
    public void init() {
        autoInit();
    }

    @Override
    public void loop() {
        telemetry.addData("Red", sensorColor.red());
        telemetry.addData("Blue", sensorColor.blue());
        telemetry.addData("Green", sensorColor.green());
        telemetry.addData("Alpha", sensorColor.alpha());
        telemetry.addData("aRGB", sensorColor.argb());
    }
}
