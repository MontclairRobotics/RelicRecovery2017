package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Robot;
import org.montclairrobotics.sprocket.actions.StateMachine;
import org.montclairrobotics.sprocket.auto.states.DriveTime;
import org.montclairrobotics.sprocket.geometry.XY;

/**
 * Created by thegb on 11/8/2017.
 */

@Autonomous(name="SquareDance",group="test")
public class SquareAuto extends Robot {

    @Override
    public void autoSetup()
    {
        action=new StateMachine(
                new DriveTime(0.5,new XY(1,0)),
                new DriveTime(0.5,new XY(0,1)),
                new DriveTime(0.5,new XY(-1,0)),
                new DriveTime(0.5,new XY(0,-1))
        );
    }
}
