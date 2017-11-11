package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Auto.Enums.AllianceColor;
import org.firstinspires.ftc.teamcode.Auto.Enums.StartPosition;
import org.montclairrobotics.sprocket.geometry.XY;

/**
 * Created by MHS Robotics on 11/10/2017.
 */
@Autonomous(name = "Glyph: Red Close")
public class GlyphRedClose extends DefultAutoMode {

    @Override
    public void init() {
        autoInit();
        allianceColor = AllianceColor.RED;
        startPosition = StartPosition.RED_CLOSE;
    }

    @Override
    public void loop() {
        switch (state){

            case 0:
                hardware.lift.closeAll();
                nextState(pause(5));
                break;

            case 1:
                hardware.lift.closeAll();
                nextState(setGlyphLiftPos(2,0.75));
                break;

            case 2:
                hardware.lift.closeAll();
                nextState(driveToSafeZone());
                break;

            case 3:
                hardware.lift.closeAll();
                nextState(autoDrive(new XY(0,3),1));
                break;

            case 4:
                hardware.lift.closeAll();
                nextState(setGlyphLiftPos(-2,0.75));
                break;

            case 5:
                hardware.lift.openAll();
                nextState(pause(5));

            case 6:
                telemetry.addData("INFO", LSA);
                break;
        }
    }
}
