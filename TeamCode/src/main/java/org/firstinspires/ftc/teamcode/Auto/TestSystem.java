package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Auto.Enums.AllianceColor;
import org.firstinspires.ftc.teamcode.Auto.Enums.StartPosition;
import org.montclairrobotics.sprocket.auto.AutoMode;

/**
 * Created by MHS Robotics on 11/11/2017.
 */

public class TestSystem extends DefultAutoMode {

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
