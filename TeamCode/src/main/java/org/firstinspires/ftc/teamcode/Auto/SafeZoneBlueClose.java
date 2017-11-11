package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.montclairrobotics.sprocket.geometry.XY;

/**
 * Created by MHS Robotics on 11/10/2017.
 */
@Autonomous(name = "Blue Close")
public class SafeZoneBlueClose extends DefultAutoMode {

    @Override
    public void init() {
        autoInit();
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
                nextState(autoDrive(new XY(12,-36),0.5));
                break;

            case 3:
                hardware.lift.closeAll();
                nextState(setGlyphLiftPos(-2,0.75));
                break;

            case 4:
                hardware.lift.openAll();
                nextState(pause(5));

            case 5:
                telemetry.addData("INFO", LSA);
                break;
        }
    }
}
