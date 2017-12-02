package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.montclairrobotics.sprocket.geometry.XY;

/**
 * Created by Montclair Robotics on 11/30/2017.
 */
@Autonomous(name = "Production: Safe Zone")
public class ProductionSafeZone extends DefaultAutoMode {
    @Override
    public void init() {
        autoInit();
    }

    @Override
    public void loop() {
        switch (state){
            case 0:
                hardware.lift.closeAll();
                nextState(raiseGlyph());
                break;

            case 1:
                hardware.lift.closeAll();
                nextState(autoDrive(new XY(0,26),1));
                break;

            case 2:
                hardware.lift.closeAll();
                nextState(lowerGlyph());
                break;

            case 3:
                hardware.lift.openAll();
                nextState(pause(2));
                break;

            case 4:
                telemetry.addData("INFO",LSA);
                break;

        }
    }
}
