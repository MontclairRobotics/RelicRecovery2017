package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Enums.AllianceColor;
import org.firstinspires.ftc.teamcode.Enums.StartPosition;

/**
 * Created by Montclair Robotics on 11/13/17.
 * @Author:Will
 * */

@Autonomous(name = "Production: Blue Far")
public class ProductionBlueFar extends DefaultAuto {
    @Override
    public void init() {
        super.init();
        allianceColor = AllianceColor.BLUE;
        startPosition = StartPosition.FAR;
        telemetry.addData("Alliance Color" , allianceColor);
        telemetry.addData("Start Position", startPosition);
    }
}
