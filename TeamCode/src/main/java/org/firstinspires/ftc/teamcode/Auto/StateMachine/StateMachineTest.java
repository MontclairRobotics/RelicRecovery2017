package org.firstinspires.ftc.teamcode.Auto.StateMachine;

import org.firstinspires.ftc.teamcode.Auto.StateMachine.States.Drive;
import org.firstinspires.ftc.teamcode.Auto.StateMachine.States.Turn;

/**
 * Created by Montclair Robotics on 11/22/2017.
 */

public class StateMachineTest extends StateMachineAuto{
    @Override
    public void init(){
        setup();
        auto = new StateMachine("State Machine Test",
                new Turn(90, .5f),
                new Drive(10, .5f));
    }
}
