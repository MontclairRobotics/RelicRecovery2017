package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Components.DriveTrain;
import org.firstinspires.ftc.teamcode.Components.GlyphIntake2;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;

/**
 * Created by Montclair Robotics on 11/13/17.
 * @Author:Garrett
 * */
@TeleOp(name="Teleop: PLEASE DON'T DELETE THIS WILL")
public class CompTeleop extends OpMode {
    public DriveTrain driveTrain;
    DcMotor frontRight, backRight, frontLeft, backLeft;
    Servo[] servos;
    Gyro gyro;
    GlyphIntake2 intake;
    DcMotor liftA, liftB;
    DigitalChannel limitSwitch;

    @Override
    public void init() {
        gyro = new Gyro(hardwareMap);
        this.driveTrain = new DriveTrain(hardwareMap, gyro);

        liftA = hardwareMap.get(DcMotor.class,"lift_left");
        liftB = hardwareMap.get(DcMotor.class,"lift_right");

        servos = new Servo[4];
        servos[0] = hardwareMap.get(Servo.class, "intake_right_top");
        servos[1] = hardwareMap.get(Servo.class, "intake_left_top");
        servos[2] = hardwareMap.get(Servo.class, "intake_right_bottom");
        servos[3] = hardwareMap.get(Servo.class, "intake_left_bottom");

        intake = new GlyphIntake2(servos);
        gyro = new Gyro(hardwareMap);
        limitSwitch = hardwareMap.get(DigitalChannel.class, "limit_switch_1");
    }

    @Override
    public void loop() {
        driveTrain.driveMechanum(gamepad1);

        if (gamepad2.a)
            intake.openBottom();
        else
            intake.closeBottom();

        if (gamepad2.b)
            intake.openTop();
        else
            intake.closeTop();

        liftA.setPower(gamepad2.left_stick_y);
        liftB.setPower(-gamepad2.left_stick_y);

        telemetry.addData("Orientation", gyro.x + "°");
        telemetry.addData("Limit Switch", limitSwitch);
    }
}
