package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.teamcode.Components.DriveTrain;

/**
 * Created by Montclair Robotics on 11/13/17.
 * @author Garrett
 * */
@TeleOp(name="Teleop: Gyro Enabled", group = "Josh")

public class CompTeleopWithGyro extends OpMode {
    public DriveTrain driveTrain;

    Gyro gyro;

//    GlyphIntake2 intake;
    DcMotor liftA, liftB;
    DigitalChannel limitSwitch;

    @Override
    public void init() {
        gyro = new Gyro(hardwareMap);
        driveTrain = new DriveTrain(hardwareMap);
        driveTrain.configureTeleop();

        liftA = hardwareMap.get(DcMotor.class,"lift_left");
        liftB = hardwareMap.get(DcMotor.class,"lift_right");

//        intake = new GlyphIntake2(hardwareMap);
        limitSwitch = hardwareMap.get(DigitalChannel.class, "limit_switch_1");
    }

    @Override
    public void loop() {
        gyro.loop();
        driveTrain.driveMecanum(gamepad1);

//        if (gamepad2.a || gamepad2.x)
//            intake.openBottom();
//        else
//            intake.closeBottom();
//
//        if (gamepad2.b || gamepad2.x)
//            intake.openTop();
//        else
//            intake.closeTop();

        liftA.setPower(gamepad2.left_stick_y);
        liftB.setPower(-gamepad2.left_stick_y);

        telemetry.addData("Gyroscope", gyro);
        telemetry.update();
    }
}
