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
        auto = new StateMachine("Test", -1,  telemetry,
                new Drive(10, 1.0f));
    }
}
