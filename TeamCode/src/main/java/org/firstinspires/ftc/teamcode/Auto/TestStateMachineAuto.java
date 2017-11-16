package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Auto.StateMachine.StateMachine;
import org.firstinspires.ftc.teamcode.Auto.StateMachine.TestState;

/**
 * Created by thegb on 11/15/2017.
 */

@Autonomous(name = "Test State Auto", group = "test")
public class TestStateMachineAuto extends StateMachineAuto{
    @Override
    public void init() {
        auto = new StateMachine("Test Auto", -1, telemetry,
                new TestState(),
                new TestState(),
                new TestState(-1)
        );

    }

}
