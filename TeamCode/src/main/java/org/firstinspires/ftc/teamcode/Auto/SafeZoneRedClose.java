package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.montclairrobotics.sprocket.geometry.XY;

/**
 * Created by MHS Robotics on 11/10/2017.
 */
@Autonomous(name = "Safe Zone: Red Close")
public class SafeZoneRedClose extends DefaultAutoMode {

    @Override
    public void init() {
        autoInit();
    }

    @Override
    public void loop() {
        switch (state){

            case 0:
                hardware.lift.closeAll();
                nextState(pause(3),1);
                break;

            case 1:
                hardware.lift.closeAll();
                nextState(setGlyphLiftPos(5,0.75),2);
                break;

            case 2:
                hardware.lift.closeAll();
                nextState(autoDrive(new XY(0,36),0.5),3);
                break;

            case 3:
                hardware.lift.closeAll();
                nextState(autoTurn(90,1),4);
                break;

            case 4:
                hardware.lift.closeAll();
                nextState(autoDrive(new XY(0,12),1),5);
                break;

            case 5:
                hardware.lift.closeAll();
                nextState(setGlyphLiftPos(-5,0.75),6);
                break;

            case 6:
                hardware.lift.openAll();
                nextState(pause(5),7);

            case 7:
                telemetry.addData("INFO", LSA);
                break;
        }
    }
}
