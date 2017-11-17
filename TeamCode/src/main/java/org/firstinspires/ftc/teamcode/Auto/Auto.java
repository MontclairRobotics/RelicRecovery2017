package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Auto.Enums.AllianceColor;
import org.firstinspires.ftc.teamcode.Auto.Enums.StartPosition;
import org.montclairrobotics.sprocket.geometry.XY;

/**
 * Created by willc on 11/13/2017.
 */

public class Auto extends DefaultAutoMode {
    @Override
    public void init() {
        autoInit();
    }

    @Override
    public void loop() {
        switch (state){
            case 0:
                hardware.lift.closeAll();
                nextState(pause(2));
                break;

            case 1:
                nextState(setGlyphLiftPos(5,1));
                break;

            case 2:
                nextState(autoDrive(new XY(0,36),1));
                break;

            case 3:
                nextState(driveTurn());
                break;

            case 4:
                nextState(autoDrive(new XY(0,12),1));
                break;

            case 5:
                nextState(cryptoBoxTurn());
                break;

            case 6:
                nextState(setGlyphLiftPos(-5,1));
                break;

            case 7:
                hardware.lift.openAll();
                nextState(pause(2));
                break;

            case 8:
                nextState(autoDrive(new XY(0,-2),1));
                break;

            case 9:
                telemetry.addData("INFO",LSA);
                break;

        }
    }
}
