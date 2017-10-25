package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by thegb on 5/20/2017.
 */

@Autonomous(name="Auto Shoot 2", group = "147")
public class AutoShoot2 extends AutoMode {
    @Override
    public void init() {
        autoInit();
        setState(0);
    }

    @Override
    public void loop() {
        switch(state){
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

        }
    }
}
