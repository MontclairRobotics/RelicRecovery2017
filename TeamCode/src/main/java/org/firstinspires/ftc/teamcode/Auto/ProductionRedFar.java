package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Auto.Enums.AllianceColor;
import org.firstinspires.ftc.teamcode.Auto.Enums.StartPosition;

/**
 * Created by Montclair Robotics on 11/13/17.
 * @Author:Will
 * */

@Autonomous(name = "Production: Red Far")
public class ProductionRedFar extends Auto{
    @Override
    public void init() {
        super.init();
        allianceColor = AllianceColor.RED;
        startPosition = StartPosition.FAR;
        telemetry.addData("Alliance Color", allianceColor);
        telemetry.addData("Start Position" , startPosition);
    }
}
