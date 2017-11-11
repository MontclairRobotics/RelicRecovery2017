package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by MHS Robotics on 11/9/2017.
 */

@Autonomous(name = "Info: Zero All Servos")
public class InfoServoZero extends DefultAutoMode {

    Servo servo;

    @Override
    public void init() {
        autoInit();
        servo = hardwareMap.get(Servo.class, "jewel_arm");
    }

    @Override
    public void loop() {
        super.loop();
        servo.setPosition(0);
        hardware.leftTop.setPosition(0);
        hardware.leftBottom.setPosition(0);
        hardware.rightTop.setPosition(0);
        hardware.rightBottom.setPosition(0);


    }
}
