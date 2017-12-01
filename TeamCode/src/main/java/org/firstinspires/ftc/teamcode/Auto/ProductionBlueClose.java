package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Enums.AllianceColor;
import org.firstinspires.ftc.teamcode.Enums.StartPosition;

/**
 * Created by Montclair Robotics on 11/13/17.
 * @Author:Will
 * */

@Autonomous(name = "Production: Blue Close")
public class ProductionBlueClose extends DefaultAuto {
    @Override
    public void init() {
        super.init();
        allianceColor = AllianceColor.BLUE;
        startPosition = StartPosition.CLOSE;
        telemetry.addData("Alliance Color" , allianceColor);
        telemetry.addData("Start Position", startPosition);
    }
}
