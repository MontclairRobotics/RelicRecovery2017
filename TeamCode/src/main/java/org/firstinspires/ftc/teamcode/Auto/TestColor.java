package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Montclair Robotics on 11/13/17.
 * @Author:Will
 * */
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
