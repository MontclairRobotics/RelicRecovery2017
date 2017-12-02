package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by Montclair Robotics on 11/13/17.
 * @Author:Will
 * */
@TeleOp(name = "Test: Color Sensor")
public class TestColor extends DefaultAutoMode {

    @Override
    public void init() {
        autoInit();
    }

    @Override
    public void loop() {
        telemetry.addData("R",colorSensor.red());
        telemetry.addData("B",colorSensor.blue());
        getJewelColor();

        colorSensor.enableLed(true   );
        if(gamepad1.a){
            colorSensor.enableLed(false);
        }
    }
}
