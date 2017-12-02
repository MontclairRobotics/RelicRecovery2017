package org.firstinspires.ftc.teamcode.Auto;

import org.montclairrobotics.sprocket.geometry.XY;

/**
 * Created by Montclair Robotics on 11/13/17.
 * @Author:Will
 * */

//possible 85 pts auto
public class DefaultAuto extends DefaultAutoMode {
    @Override
    public void init() {
        autoInit();
    }

    @Override
    public void loop() {
        switch (state){

            case 0: // raise glyph
                hardware.lift.closeAll();
                nextState(raiseGlyph());
                break;

            case 1: //get jewel
                hardware.lift.closeAll();
                nextState(getJewel());
                break;

            case 2: //drive forward or backward 24 + 2(to get off balancing stone)
                hardware.lift.closeAll();
                switch (allianceColor){
                    case RED:
                        nextState(autoDrive(new XY(0,26),1));
                        break;

                    case BLUE:
                        nextState(autoDrive(new XY(0,-26),1));
                        break;
                }
                break;

            case 3: // turn based on color
                hardware.lift.closeAll();
                nextState(driveTurn());
                break;

            case 4: // drive to safezone
                hardware.lift.closeAll();
                nextState(safeZoneDrive());
                break;

            case 5: // turn at cryptobox
                hardware.lift.closeAll();
                nextState(cryptoBoxTurn());
                break;

            case 6: //input glyph
                hardware.lift.closeAll();
                nextState(autoDrive(new XY(0,6),1));
                break;

            case 7: // lower glyph
                hardware.lift.closeAll();
                nextState(lowerGlyph());
                break;

            case 8: //release glyph
                hardware.lift.openAll();
                nextState(pause(1));
                break;

            case 9: //back away
                nextState(autoDrive(new XY(0,-4),1));
                break;

            case 10   : //Telemetry
                telemetry.addData("INFO",LSA);
                break;

        }
    }
}
