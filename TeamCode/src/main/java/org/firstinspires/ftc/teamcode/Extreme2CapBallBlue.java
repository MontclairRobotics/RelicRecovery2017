package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by thegb on 5/20/2017.
 */


@Autonomous(name = "EXTREME 2 CAP BALL BLUE", group = "147")
public class Extreme2CapBallBlue extends AutoMode{
    @Override
    public void init() {
        setState(0);
        autoInit();
    }


    @Override
    public void loop() {
        switch (state){
            case 0:
                nextState(drive(SINGLE_BLOCK_DISTANCE));
                break;
            case 1:
                nextState(pause(1));
                break;
            case 2:
                nextState(turn(60));
                break;
            case 3:
                nextState(drive(12 * DEGREES_PER_INCH));
                break;
            case 4:
                intake.intakeDown();
                nextState(intake.isCloseTo(intake.intakeDownPos));
                break;
            case 5:
                nextState(shoot() || pause(3));
                break;
            case 6:
                intake.intakeUpSlow();
                nextState(intake.isCloseTo(intake.intakeHalfPos));
                break;
            case 7:
                intake.intakeHalf();
                intake.isCloseTo(intake.intakeHalfPos);
                break;
            case 8:
                nextState(drive(48 * DEGREES_PER_INCH));
                break;
            case 9:
                nextState(turn(180));
                break;
            default:
                telemetry.addData("DEFAULT", "Idk its probs done");
                break;
        }
        updateTelemetry(telemetry);
    }
}
