package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Montclair Robotics on 11/13/17.
 * @Author:Will
 * */
@TeleOp(name = "Test: Color Sensor")
public class TestColor extends AutoFunctions {

    @Override
    public void init() {
        autoInit();
    }

    @Override
    public void loop() {
        if(gamepad1.a) {
            avgJewelColor(2500);
        }
        telemetry.addData("Color",jewelColor);
    }
}
