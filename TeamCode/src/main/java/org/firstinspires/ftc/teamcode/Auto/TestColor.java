package org.firstinspires.ftc.teamcode.Auto;

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
        telemetry.addData("R",colorSensor.red());
        telemetry.addData("B",colorSensor.blue());
        getJewelColor();

        colorSensor.enableLed(true);
        if(gamepad1.a){
            colorSensor.enableLed(false);
        }
    }
}
