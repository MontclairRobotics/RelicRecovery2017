package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Garrett on 1/16/2017.
 * Extended by Will Chu on 1/20/2017
 */

//Test
@Autonomous(name="Auto Drive Shoot 2", group="147")
public class AutoDriveShoot2 extends AutoMode {

    @Override
    public void init() {
        setState(0);
        autoInit();
    }


    @Override
    public void loop() {
        switch (state){

            case 0: //intake down
                intake.intakeDownSlow();
                nextState(intake.isCloseTo(intake.intakeDownPos));
                break;

            case 1: // Shoot Particle
                nextState(shoot() || pause(2));
                pause(pauseTime);
                break;

            case 2: // intake Up
                intake.intakeUpSlow();
                nextState(intake.isCloseTo(intake.intakeUpPos));
                pause(pauseTime);
                break;

            case 3: //intake half
                intake.intakeHalf();
                nextState(intake.isCloseTo(intake.intakeHalfPos));
                pause(pauseTime);
                break;
            case 4:
                nextState(pause(3));
                break;
            case 5: // Shoot
                nextState(shoot() || pause(2));
                pause(pauseTime);
                break;
            case 6:
                intake.intakeUp();
                nextState(intake.isCloseTo(intake.intakeUpPos));
                break;
            case 7:
                nextState(drive(2 * SINGLE_BLOCK_DISTANCE));
                break;
            case 8:
                nextState(turn(170));
                break;
            case 9:// drive
                nextState(drive(-HALF_BLOCK_DISTANCE));
                break;

            case 10: //telemetry
                telemetry.addData("INFO", "Last State Achieved");
                break;
        }
        updateTelemetry(telemetry);
    }
}
