package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Auto.Enums.Color;

/**
 * Created by Montclair Robotics on 11/13/17.
 * @Author:Will
 * */
@Autonomous(name = "Test: Jewel ID")
public class TestJewelID extends DefaultAutoMode {

    @Override
    public void init() {
        autoInit();
        color = Color.RED;
        telemetry.addData("Alliance Color", color);
    }

    @Override
    public void loop() {
        switch (state) {
            case 0:
                hardware.jewelArm.setPosition(JEWEL_ARM_DOWN_POS);
                nextState(pause(1));
                break;

            case 1:
                nextState(getJewel());
                break;

            case 2:
                telemetry.addData("INFO",LSA);
        }
    }
}
