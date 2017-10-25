package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by thegb on 5/20/2017.
 */

@Autonomous(name = "EXTREME 2 CAP BALL RED", group = "147")
public class Extreme2CapBallRed extends AutoMode{
    @Override
    public void init() {
        setState(0);
        autoInit();
    }


    @Override
    public void loop() {
        switch (state){
            case 0:
                intake.intakeDown();
                nextState(intake.isCloseTo(intake.intakeDownPos));
                break;
            case 1:
                nextState(pause(3));
                break;
            case 2:
                nextState(shoot() || pause(3));
                break;
            case 3:
                intake.intakeUp();
                nextState(intake.isCloseTo(intake.intakeUpPos));
                break;
            case 4:
                intake.intakeHalf();
                nextState(intake.isCloseTo(intake.intakeHalfPos));
                break;
            case 5:
                nextState(pause(2));
                break;
            case 6:
                nextState(shoot() || pause(3));
                break;
            case 7:
                intake.setPos(intake.intakeHalfPos + 400);
                nextState(intake.isCloseTo(intake.intakeHalfPos + 400));
                break;
            case 8:
                nextState(drive(2 * SINGLE_BLOCK_DISTANCE));
                break;

            case 9:
                nextState(turn(-60));
                break;

            case 10:
                nextState(drive(48 * DEGREES_PER_INCH));
                break;
            case 11:
                nextState(turn(180));
                break;
            default:
                telemetry.addData("DEFAULT", "Idk its probs done");
                break;
        }
        updateTelemetry(telemetry);
    }
}
