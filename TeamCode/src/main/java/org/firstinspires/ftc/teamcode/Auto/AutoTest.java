package org.firstinspires.ftc.teamcode.Auto;

import org.firstinspires.ftc.teamcode.Auto.StateMachine.States.Drive;
import org.firstinspires.ftc.teamcode.Auto.StateMachine.StateMachine;
import org.firstinspires.ftc.teamcode.Auto.StateMachine.StateMachineAuto;

/**
 * Created by Will on 11/13/17.
 */

public class AutoTest extends StateMachineAuto {

    @Override
    public void init(){
        setup();
        auto = new StateMachine("Test",
                new Drive(10, 1.0f));
    }
}
