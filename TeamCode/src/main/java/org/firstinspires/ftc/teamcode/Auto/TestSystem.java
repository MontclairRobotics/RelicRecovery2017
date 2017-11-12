package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Auto.Enums.AllianceColor;
import org.firstinspires.ftc.teamcode.Auto.Enums.StartPosition;

/**
 * Created by MHS Robotics on 11/11/2017.
 */
@Autonomous(name = "Test: System Flow")
@Disabled
public class TestSystem extends DefaultAutoMode {

    @Override
    public void init() {
        autoInit();
        allianceColor = AllianceColor.BLUE;
        startPosition = StartPosition.BLUE_CLOSE;
        telemetry.addData("Alliance Color",allianceColor);
        telemetry.addData("Start Position", startPosition);
    }

    @Override
    public void loop() {

        switch (state){
            case 0:
                telemetry.addData("Alliance Color",allianceColor);
                telemetry.addData("Start Position", startPosition);
                break;
        }

    }
}
