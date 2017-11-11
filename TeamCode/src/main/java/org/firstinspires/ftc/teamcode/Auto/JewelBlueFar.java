package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Auto.Enums.AllianceColor;
import org.firstinspires.ftc.teamcode.Auto.Enums.StartPosition;
import org.montclairrobotics.sprocket.geometry.XY;

/**
 * Created by MHS Robotics on 11/10/2017.
 */
@Autonomous(name = "Jewel: Blue Far")
public class JewelBlueFar extends DefultAutoMode {

    @Override
    public void init() {
        autoInit();
        allianceColor = AllianceColor.BLUE;
        startPosition = StartPosition.BLUE_FAR;
    }

    @Override
    public void loop() {
        switch (state){

            case 0:
                hardware.lift.closeAll();
                hardware.jewelArm.setPosition(JEWEL_ARM_DOWN_POS);
                nextState(setGlyphLiftPos(2,0.75) && pause(5));
                break;

            case 1:
                hardware.lift.closeAll();
                nextState(getJewelColor());
                break;

            case 2:
                hardware.lift.closeAll();
                nextState(jewelReact());
                break;

            case 3:
                hardware.lift.closeAll();
                hardware.jewelArm.setPosition(JEWEL_ARM_UP_POS);
                nextState(pause(5));
                break;

            case 4:
                hardware.lift.closeAll();
                nextState(driveToSafeZone());
                break;

            case 5:
                hardware.lift.closeAll();
                nextState(setGlyphLiftPos(-2,0.75));
                break;

            case 6:
                hardware.lift.openAll();
                nextState(pause(5));

            case 7:
                telemetry.addData("INFO", LSA);
                break;
        }
    }
}
