package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by MHS Robotics on 11/8/2017.
 */
@Autonomous(name = "Test: Color Sensor")
public class TestColor extends DefaultAutoMode {

    @Override
    public void init() {
        colorSensor.enableLed(false);
    }

    @Override
    public void start() {
        colorSensor.enableLed(true);
    }

    @Override
    public void loop() {
        getJewelColor();

    }
}
