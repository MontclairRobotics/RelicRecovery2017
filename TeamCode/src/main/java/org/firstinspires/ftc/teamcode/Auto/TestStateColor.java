package org.firstinspires.ftc.teamcode.Auto;

import org.firstinspires.ftc.teamcode.Auto.StateMachine.StateMachine;
import org.firstinspires.ftc.teamcode.Auto.StateMachine.StateMachineAuto;
import org.firstinspires.ftc.teamcode.Auto.StateMachine.States.SetJewelArm;

/**
 * Created by Montclair Robotics on 11/22/2017.
 */

public class TestStateColor extends StateMachineAuto {
    @Override
    public void init(){
        auto = new StateMachine("Color State Test",
                new SetJewelArm(SetJewelArm.UP_POSITION));
    }
}
