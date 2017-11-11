package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by MHS Robotics on 11/8/2017.
 */
@Autonomous(name = "Test: Color Sensor")
public class TestColor extends DefultAutoMode {
    @Override
    public void init() {
        autoInit();
    }

    @Override
    public void loop() {
        switch (state){
            case 0:
                telemetry.addData("Red",sensorColor.red());
                telemetry.addData("Blue",sensorColor.blue());
                break;


            case 2:
                telemetry.addData("INFO","Last State Achieved");
                break;
        }
    }
}
