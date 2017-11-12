package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by MHS Robotics on 11/12/2017.
 */

@TeleOp(name="Servo tester")
public class ServoTester extends OpMode {

    Servo jewelArm;

    @Override
    public void init() {
        jewelArm = hardwareMap.servo.get("jewel_arm");
        jewelArm.setPosition(0);
    }

    @Override
    public void loop() {
        if(gamepad1.dpad_up) {
            jewelArm.setPosition(jewelArm.getPosition() + 0.005);
        } else if(gamepad1.dpad_down) {
            jewelArm.setPosition(jewelArm.getPosition() - 0.005);
        }

        telemetry.addData("Arm pos", jewelArm.getPosition());
    }
}
