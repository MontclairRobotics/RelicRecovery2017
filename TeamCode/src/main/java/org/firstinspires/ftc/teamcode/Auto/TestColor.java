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
        sensorColor.enableLed(false);
    }

    @Override
    public void loop() {
        if(sensorColor == null) {
            telemetry.addData("Stuff", "Color sensor is null");
        }else {
            telemetry.addData("Red", sensorColor.red());
            telemetry.addData("Blue", sensorColor.blue());
            telemetry.addData("Green", sensorColor.green());
            getJewelColor();
        }
    }
}
