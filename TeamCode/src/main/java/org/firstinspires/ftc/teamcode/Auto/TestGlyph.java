package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

/**
 * Created by MHS Robotics on 11/9/2017.
 */
@Autonomous(name = "Test: Glyph Intake and Lift")
@Disabled
public class TestGlyph extends DefaultAutoMode {

    @Override
    public void init() {
        autoInit();
    }

    @Override
    public void loop() {
        switch (state){
            case 0:
                hardware.lift.closeAll();
                nextState(pause(1));
                break;

            case 1:
                hardware.lift.closeAll();
                nextState(setGlyphLiftPos(5,0.75));
                break;

            case 2:
                hardware.lift.closeAll();
                nextState(setGlyphLiftPos(-5,0.75));
                break;

            case 3:
                hardware.lift.openAll();
                nextState(pause(1));
                break;

            case 4:
                telemetry.addData("INFO",LSA);
                break;
        }
    }
}
