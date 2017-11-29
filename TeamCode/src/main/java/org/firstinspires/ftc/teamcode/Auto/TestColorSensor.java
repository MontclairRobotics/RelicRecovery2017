package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by Montclair Robotics on 11/29/2017.
 */
@Autonomous(name="testColorSensor")
public class TestColorSensor extends OpMode {
    ColorSensor colorSensor;
    @Override
    public void init() {
        colorSensor = hardwareMap.get(ColorSensor.class, "sensor_color");
    }

    @Override
    public void loop() {
        telemetry.addData("RED",colorSensor.red());
        telemetry.addData("BLUE",colorSensor.blue());
        telemetry.addData("BLUE MINUS RED",colorSensor.blue()-colorSensor.red());
    }
}
