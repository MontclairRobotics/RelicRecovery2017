package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by MHS Robotics on 11/9/2017.
 */

@TeleOp(name = "Info: Glyph Test")
public class TestGlyphInfo extends AutoFunctions {

    Gamepad controller;
    Servo[] servos;
    int index;
    boolean wasPressed;


    @Override
    public void init() {
        controller = this.gamepad1;
        servos = new Servo[5];
        servos[0] = hardwareMap.get(Servo.class, "intake_right_top");
        servos[1] = hardwareMap.get(Servo.class, "intake_left_top");
        servos[2] = hardwareMap.get(Servo.class, "intake_right_bottom");
        servos[3] = hardwareMap.get(Servo.class, "intake_left_bottom");
        servos[4] = hardwareMap.get(Servo.class, "jewel_arm");
        index = 0;
        wasPressed = false;

        autoInit();
    }

    @Override
    public void loop() {
        if(!wasPressed && (controller.dpad_left || controller.dpad_right)) {
            wasPressed = true;
            if(controller.dpad_left) {
                index--;
                if(index == -1) {
                    index = servos.length - 1;
                }
            } else if(controller.dpad_right) {
                index++;
                if(index == servos.length) {
                    index = 0;
                }
            }
        } else if(wasPressed && !controller.dpad_left && !controller.dpad_right) {
            wasPressed = false;
        }

        telemetry.addData("index", index);

        if(controller.dpad_up) {
            servos[index].setPosition(servos[index].getPosition()+.005);
        }

        if(controller.dpad_down) {
            servos[index].setPosition(servos[index].getPosition()-.005);
        }

        if(controller.a) {
            if(controller.left_trigger > 0.5) {
                hardware.lift.openBottom();
            } else if(controller.right_trigger > 0.5) {
                hardware.lift.openTop();
            } else {
                hardware.lift.openAll();
            }
        }

        if(controller.b) {
            if(controller.left_trigger > 0.5) {
                hardware.lift.closeBottom();
            } else if(controller.right_trigger > 0.5) {
                hardware.lift.closeTop();
            } else {
                hardware.lift.closeAll();
            }
        }

        telemetry.addData("pos", servos[index].getPosition());
    }
}