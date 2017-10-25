package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Montclair Robotics on 1/24/2017.
 */

// Test
@Autonomous(name="Test: Auto", group = "147")
public class AutoTest extends AutoMode {
    @Override
    public void init(){
        autoInit();
        setState(0);
        intake.intakeHalf();
    }

    @Override
    public void loop(){
        switch (state){
            case 0: // drive
                nextState(drive(SINGLE_BLOCK_DISTANCE));
                break;

            case 1://shoot
                nextState(shoot());
                break;

            case 2: //intake
                nextState(intake());
                telemetry.addData("Intake State", intakeState);
                break;

            case 3://turn right 45 Deg
                nextState(turn(Right90));
                break;

            case 4: // turn left 45 deg
                nextState(turn(Left90));
                break;

            case 5: //drive backwards
                nextState(drive(-SINGLE_BLOCK_DISTANCE));
                break;

            case 6: //telemetry
                telemetry.addData("INFO", "Last State Achieved");
                break;

        }
        updateTelemetry(telemetry);
    }
}
