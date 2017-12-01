package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Enums.AllianceColor;

/**
 * Created by Montclair Robotics on 11/13/17.
 * @Author:Will
 * */
@Autonomous(name = "Test: Jewel ID")
public class TestJewelID extends DefaultAutoMode {

    @Override
    public void init() {
        autoInit();
        allianceColor = AllianceColor.RED;
        telemetry.addData("Alliance Color",allianceColor);
    }

    @Override
    public void loop() {
        switch (state) {
            case 0:
                nextState(getJewel());
                break;

            case 1:
                telemetry.addData("INFO",LSA);
                break;
        }
    }
}
