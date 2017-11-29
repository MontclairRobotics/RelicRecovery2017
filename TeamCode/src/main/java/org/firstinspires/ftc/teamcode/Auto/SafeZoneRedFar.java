package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.montclairrobotics.sprocket.geometry.XY;

/**
 * Created by MHS Robotics on 11/10/2017.
 */
@Autonomous(name = "USE THIS MATCH 19")
@Disabled
public class SafeZoneRedFar extends DefaultAutoMode {

    @Override
    public void init() {
        autoInit();
    }

    @Override
    public void loop() {
        switch (state){

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
                nextState(autoDrive(new XY(0,15),0.5));
                break;

            case 3:
                hardware.lift.closeAll();
                nextState(autoTurn(90,1));
                break;

            case 4:
                hardware.lift.closeAll();
                nextState(autoDrive(new XY(0,36),0.5));
                break;

            case 5:
                hardware.lift.closeAll();
                nextState(setGlyphLiftPos(-2,0.75));
                break;

            case 6:
                hardware.lift.openAll();
                nextState(pause(5));
                break;

            case 7:
                nextState(autoDrive(new XY(0,-2),0.5));
                break;


            case 8:
                telemetry.addData("INFO", LSA);
                break;
        }
    }
}
