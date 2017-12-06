package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Enums.AllianceColor;

/**
 * Created by Montclair Robotics on 12/1/2017.
 */
@Autonomous(name="Production Red Jewel")
public class ProductionBlue extends AutoFunctions {
    @Override
    public void init() {
        autoInit();
        allianceColor = AllianceColor.BLUE;
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
