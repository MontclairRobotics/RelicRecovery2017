package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Montclair Robotics on 11/29/2017.
 */
@Autonomous(name = "Test: Arm")
public class TestArm extends DefaultAutoMode {

    @Override
    public void init() {
        autoInit();
    }

    @Override
    public void loop(){
        switch ((int)hardware.jewelArm.getPosition()){
            case (int) JEWEL_ARM_DOWN_POS:
                hardware.jewelArm.setPosition(JEWEL_ARM_UP_POS);
                break;

            case (int) JEWEL_ARM_UP_POS:
                hardware.jewelArm.setPosition(JEWEL_ARM_DOWN_POS);
                break;
        }
    }

}
