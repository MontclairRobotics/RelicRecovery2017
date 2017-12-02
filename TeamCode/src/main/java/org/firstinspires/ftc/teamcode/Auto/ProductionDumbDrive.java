package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Enums.AllianceColor;
import org.firstinspires.ftc.teamcode.Enums.StartPosition;
import org.montclairrobotics.sprocket.geometry.XY;

/**
 * Created by Montclair Robotics on 11/13/17.
 * @Author:Will
 * */


@Autonomous(name = "Production: DumbDrive")
public class ProductionDumbDrive extends DefaultAutoMode {

    public void init() {
        autoInit();
    }
    @Override
    public void loop() {
        switch (state){
            case 0:
                hardware.lift.closeAll();
                nextState(pause(0.5));
                break;
            case 1: // raise glyph
                hardware.lift.closeAll();

                nextState(raiseGlyph());
                break;

            case 2: //input glyph
                hardware.lift.closeAll();
                autoDrive(new XY(0,100),0.5);
                nextState(pause(5));
                break;

            case 3: // lower glyph
                hardware.lift.closeAll();
                nextState(lowerGlyph());
                break;

            case 4: //release glyph
                hardware.lift.openAll();
                nextState(pause(1));
                break;

            case 5: //back away
                nextState(autoDrive(new XY(0,-2),1));
                break;

            case 6   : //Telemetry
                telemetry.addData("INFO",LSA);
                break;
        }
    }
}
