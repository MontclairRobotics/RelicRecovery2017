package org.firstinspires.ftc.teamcode.Auto.StateMachine;

import org.firstinspires.ftc.teamcode.Auto.StateMachine.States.Drive;

/**
 * Created by Montclair Robotics on 11/22/2017.
 */

public class StateMachineTest extends StateMachineAuto{
    @Override
    public void init(){
        setup();
        auto = new StateMachine("State Machine Test",
                new Drive(10, .5f));
    }
}
