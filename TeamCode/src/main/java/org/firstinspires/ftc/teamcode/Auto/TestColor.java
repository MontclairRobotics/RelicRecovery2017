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
        telemetry.addData("Red",colorSensor.red());
        telemetry.addData("Blue",colorSensor.blue());


        colorSensor.enableLed(gamepad1.a);
        if(gamepad1.b)
            avgJewelColor(2000);
        telemetry.addData("RED",redTotal);
        telemetry.addData("BLUE",blueTotal);
        telemetry.addData("COLOR",jewelColor);

    }
}
