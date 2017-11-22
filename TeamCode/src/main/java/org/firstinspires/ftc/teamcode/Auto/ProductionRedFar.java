package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Auto.Enums.Color;
import org.firstinspires.ftc.teamcode.Auto.Enums.StartPosition;

/**
 * Created by Montclair Robotics on 11/13/17.
 * @Author:Will
 * */

@Autonomous(name = "Production: Red Far")
public class ProductionRedFar extends DefaultAuto {
    @Override
    public void init() {
        super.init();
        color = Color.RED;
        startPosition = StartPosition.FAR;
        telemetry.addData("Alliance Color", color);
        telemetry.addData("Start Position" , startPosition);
    }
}
