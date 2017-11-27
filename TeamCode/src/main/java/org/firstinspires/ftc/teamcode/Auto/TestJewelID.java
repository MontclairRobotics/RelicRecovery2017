package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Auto.Enums.AllianceColor;
import org.montclairrobotics.sprocket.actions.StateMachine;
import org.montclairrobotics.sprocket.auto.states.DriveTime;
import org.montclairrobotics.sprocket.geometry.XY;

/**
 * Created by MHS Robotics on 11/12/2017.
 */
@Autonomous(name = "Test: Jewel ID")
public class TestJewelID extends DefaultAutoMode {

    @Override
    public void init() {
        autoInit();
        allianceColor = AllianceColor.RED;
    }

    @Override
    public void loop() {
        switch (state) {
            case 0:
                hardware.jewelArm.setPosition(JEWEL_ARM_DOWN_POS);
                nextState(pause(5));
                break;

            case 1:
                nextState(getJewelColor());
                break;

            case 2:
                if (allianceColor != color) {
                    nextState(true,90);
                } else{
                    nextState(true,100);
                }
                break;


            case 3:
                hardware.jewelArm.setPosition(JEWEL_ARM_UP_POS);
                nextState(pause(5),4);
                break;

            case 4:
                telemetry.addData("INFO",LSA);
                break;

            //allianceColor == color
            case 90:

                nextState(autoTurn(-30,1),91);
                break;

            case 91:
                nextState(true);
                break;

            case 92:
                nextState(true,3);
                break;

            //allianceColor != color
            case 100:
                nextState(autoTurn(30,1),101);
                break;

            case 101:
                nextState(true);
                break;

            case 102:
                nextState(true,3);
                break;
        }
    }
}
