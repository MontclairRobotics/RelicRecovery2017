package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Robot;
import org.montclairrobotics.sprocket.actions.StateMachine;
import org.montclairrobotics.sprocket.auto.*;
import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.auto.states.DriveTime;

/**
 * Created by thegb on 11/8/2017.
 */

@Autonomous(name="Drive Test", group="test")
public class DriveTest extends Robot{
    @Override
    public void autoSetup(){
        action = new AutoMode("Drive Test",
                new DriveTime(3, .5)
        );
    }

}
