package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Components.GlyphIntake2;

/**
 * Created by MHS Robotics on 11/12/2017.
 */

@TeleOp(name="Teleop: PLEASE DONT DELETE THIS WILL")
public class NOTWILLTELEOP extends OpMode {
    DcMotor frontRight,backRight,frontLeft,backLeft;
    Servo[] servos;

    GlyphIntake2 intake;
    DcMotor liftA,liftB;
    @Override
    public void init() {
        frontRight        = hardwareMap.get(DcMotor.class, "right_front");
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backRight         = hardwareMap.get(DcMotor.class, "right_back");
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backLeft          = hardwareMap.get(DcMotor.class, "left_back");
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        frontLeft         = hardwareMap.get(DcMotor.class, "left_front");
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        liftA = hardwareMap.get(DcMotor.class,"lift_left");
        liftB = hardwareMap.get(DcMotor.class,"lift_right");

        servos = new Servo[4];
        servos[0] = hardwareMap.get(Servo.class, "intake_right_top");
        servos[1] = hardwareMap.get(Servo.class, "intake_left_top");
        servos[2] = hardwareMap.get(Servo.class, "intake_right_bottom");
        servos[3] = hardwareMap.get(Servo.class, "intake_left_bottom");

        intake=new GlyphIntake2(servos);
    }

    @Override
    public void loop() {
        double pow=1;
        if(gamepad1.left_bumper)
        {
            pow=0.5;
        }
        double x=gamepad1.left_stick_x*pow;
        double y=-gamepad1.left_stick_y*pow;
        double turn=gamepad1.right_stick_x*pow;
        frontRight.setPower(x-y+turn);
        backRight.setPower(-x-y+turn);
        backLeft.setPower(-x+y+turn);
        frontLeft.setPower(x+y+turn);

        if(gamepad2.a)
        {
            intake.openBottom();
        }
        else
        {
            intake.closeBottom();
        }
        if(gamepad2.b)
        {
            intake.openTop();
        }
        else
        {
            intake.closeTop();
        }

        liftA.setPower(gamepad2.left_stick_y);
        liftB.setPower(-gamepad2.left_stick_y);
    }
}
