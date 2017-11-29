package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by Montclair Robotics on 11/29/2017.
 */

@TeleOp(name="testColorSensor Average")
public class TestColorSensorAvg extends OpMode
{
    int total;
    int samples;
    ColorSensor colorSensor;
    @Override
    public void init() {
        colorSensor = hardwareMap.get(ColorSensor.class, "sensor_color");
    }

    @Override
    public void loop() {
        telemetry.addData("RED", colorSensor.red());
        telemetry.addData("BLUE", colorSensor.blue());
        telemetry.addData("BLUE MINUS RED", colorSensor.blue() - colorSensor.red());
        if(gamepad1.x)
        {
            total+=colorSensor.blue()-colorSensor.red();
            samples++;
        }
        if(gamepad1.y)
        {
            total=0;
            samples=0;
        }
        if(samples>0) {
            telemetry.addData("Avg BLUE MINUS RED", (float) total / samples);
        }
        else {
            telemetry.addData("Avg BLUE MINUS RED", 0);
        }
    }

}
