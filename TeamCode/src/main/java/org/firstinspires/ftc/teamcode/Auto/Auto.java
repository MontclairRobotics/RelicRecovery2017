package org.firstinspires.ftc.teamcode.Auto;

import org.montclairrobotics.sprocket.geometry.XY;

/**
 * Created by Montclair Robotics on 11/13/17.
 * @Author:Will
 * */

//85 pts auto
public class Auto extends DefaultAutoMode {
    @Override
    public void init() {
        autoInit();
    }

    @Override
    public void loop() {
        switch (state){

            case 0: //grab glyph, lower arm and get pictogram
                hardware.lift.closeAll();
                hardware.jewelArm.setPosition(JEWEL_ARM_DOWN_POS);
                nextState(getPictogram());
                break;

            case 1: // raise glyph
                nextState(setGlyphLiftPos(5,1));
                break;

            case 2: //get jewel
                nextState(getJewel());
                break;


            case 3: // drive forward 24 + 2 (to get off balancing stone)
                nextState(autoDrive(new XY(0,26),1));
                break;

            case 4: //turn based on position
                nextState(driveTurn());
                break;

            case 5: //drive forward 12
                nextState(autoDrive(new XY(0,12),1));
                break;

            case 6: //2nd turn based on position
                nextState(cryptoBoxTurn());
                break;

            case 7: // move to the correct cryptobox slot
                nextState(pictogramDrive(pictogram));
                break;

            case 8: // drive into crypto box slot slowly
                nextState(autoDrive(new XY(0,12),0.25));
                break;

            case 9: // lower glyph
                nextState(setGlyphLiftPos(-5,1));
                break;

            case 10: //release glyph
                hardware.lift.openAll();
                nextState(pause(1));
                break;

            case 11: // back away from glyph
                nextState(autoDrive(new XY(0,-2),1));
                break;

            case 12: // telemetry
                telemetry.addData("INFO",LSA);
                break;

        }
    }
}
