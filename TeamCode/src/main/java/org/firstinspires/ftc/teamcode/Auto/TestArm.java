package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by MHS Robotics on 11/9/2017.
 */
@Autonomous(name = "Arm")
public class TestArm extends DefultAutoMode {
    @Override
    public void init() {
        autoInit();
    }

    @Override
    public void loop() {
        switch (state){
            case 0:
                hardware.jewelArm.setPosition(JEWEL_ARM_DOWN_POS);
                pause(3);
                break;

            case 1:
                hardware.jewelArm.setPosition(JEWEL_ARM_UP_POS);
                pause(3);
                break;

            case 2:
                telemetry.addData("INFO", LSA);
                break;

        }
    }
}
