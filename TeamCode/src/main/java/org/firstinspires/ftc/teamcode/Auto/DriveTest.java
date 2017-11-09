package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Robot;
import org.montclairrobotics.sprocket.actions.StateMachine;
import org.montclairrobotics.sprocket.auto.*;
import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.auto.states.Delay;
import org.montclairrobotics.sprocket.auto.states.DriveTime;
import org.montclairrobotics.sprocket.utils.Debug;

/**
 * Created by thegb on 11/8/2017.
 */

@Autonomous(name="Drive Test", group="test")
public class DriveTest extends Robot{
    @Override
    public void autoSetup()
    {

    }
    public void autoInit(){
        Debug.msg("Starting","at least be here");
        action = new AutoMode("Drive Test",
                new Delay(5)
        );
    }

}
