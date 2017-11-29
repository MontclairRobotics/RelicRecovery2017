package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

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
    public void start() {
        colorSensor.enableLed(true);
    }

    @Override
    public void loop() {
        getJewelColor();
    }
}
