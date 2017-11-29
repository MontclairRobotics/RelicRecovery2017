package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Auto.Enums.AllianceColor;
import org.montclairrobotics.sprocket.geometry.XY;

/**
 * Created by MHS Robotics on 11/12/2017.
 */

public class ExampleJewelRedFar extends DefaultAutoMode {

    @Override
    public void init() {
        autoInit();
        allianceColor = AllianceColor.RED;
    }

    @Override
    public void loop() {
        switch (state) {

            case 0:
                hardware.lift.closeAll();
                nextState(pause(3));
                break;

            case 1:
                hardware.lift.closeAll();
                nextState(setGlyphLiftPos(5,0.75));
                break;

            case 2:
                hardware.lift.closeAll();
                nextState(true,80);
                break;

            case 3:
                hardware.lift.closeAll();
                nextState(autoDrive(new XY(0,36),0.5));
                break;

            case 4:
                hardware.lift.closeAll();
                nextState(autoTurn(-90,1));
                break;

            case 5:
                hardware.lift.closeAll();
                nextState(autoDrive(new XY(0,12),1));
                break;

            case 6:
                hardware.lift.closeAll();
                nextState(setGlyphLiftPos(-2,0.75));
                break;

            case 7:
                hardware.lift.openAll();
                nextState(pause(5));

            case 8:
                telemetry.addData("INFO", LSA);
                break;

            //Get jewel
            case 80:
                hardware.lift.closeAll();
                hardware.jewelArm.setPosition(JEWEL_ARM_DOWN_POS);
                nextState(pause(5));
                break;

            case 81:
                hardware.lift.closeAll();
                nextState(getJewelColor());
                break;

            case 82:
                hardware.lift.closeAll();
                if (allianceColor != color) {
                    nextState(true,90);
                } else{
                    nextState(true,100);
                }
                break;


            case 83:
                hardware.lift.closeAll();
                hardware.jewelArm.setPosition(JEWEL_ARM_UP_POS);
                nextState(pause(5),3);
                break;

            //allianceColor == color
            case 90:
                hardware.lift.closeAll();
                nextState(autoTurn(-90,1));
                break;

            case 91:
                hardware.lift.closeAll();
                nextState(autoTurn(90,1));
                break;

            case 92:
                hardware.lift.closeAll();
                nextState(true,3);
                break;

            //allianceColor != color
            case 100:
                hardware.lift.closeAll();
                nextState(autoTurn(90,1));
                break;

            case 101:
                hardware.lift.closeAll();
                nextState(autoTurn(-90,1));
                break;

            case 102:
                hardware.lift.closeAll();
                nextState(true,3);
                break;
        }
    }
}
