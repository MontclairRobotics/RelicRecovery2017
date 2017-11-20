package org.firstinspires.ftc.teamcode.Auto;

import org.firstinspires.ftc.teamcode.Auto.StateMachine.Drive;
import org.firstinspires.ftc.teamcode.Auto.StateMachine.StateMachine;
import org.firstinspires.ftc.teamcode.Auto.StateMachine.StateMachineAuto;
import org.firstinspires.ftc.teamcode.Components.DriveTrain;

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
