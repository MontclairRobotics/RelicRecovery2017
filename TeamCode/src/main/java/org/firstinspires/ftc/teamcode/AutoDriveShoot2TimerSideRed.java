package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Will on 4/27/2017.
 */

//TODO: Testing Required
@Autonomous(name="Auto Drive Shoot 2 (Red Timer Side)", group="147")
public class AutoDriveShoot2TimerSideRed extends AutoMode {

    @Override
    public void init() {
        setState(0);
        autoInit();
    }

    @Override
    public void loop() {
        switch (state){

            case 0: // Drive Forward 1.5 Tiles
                nextState(drive(SINGLE_BLOCK_DISTANCE));
                break;

            case 1: //turn 45 left
                nextState(turn(Left45));
                break;

            case 2: // Intake Down and wheels deployed
                intake.intakeDownSlow();
                nextState(intake.isCloseTo(intake.intakeDownPos) &&  beacon());
                break;

            case 3: // Shoot
                nextState(shoot() || pause(2));
                break;

            case 4: //Intake Up
                intake.intakeUp();
                nextState(intake.isCloseTo(intake.intakeUpPos));
                break;

            case 5: // Intake Half
                intake.intakeHalf();
                nextState(intake.isCloseTo(intake.intakeHalfPos));
                break;

            case 6:
                nextState(pause(3));
                break;
            case 7:// Shoot
                nextState(shoot() || pause(2));
                break;
            case 8:
                intake.intakeUpSlow();
                nextState(intake.isCloseTo(intake.intakeUpPos));
                break;
            case 9: //drive
                nextState(drive(-DISTANCE_AFTER_TURN));
                break;
            case 10: //telemetry
                telemetry.addData("INFO", "Last State Achieved");
                break;
        }
        updateTelemetry(telemetry);
    }
}
