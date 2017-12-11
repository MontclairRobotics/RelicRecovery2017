package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.GyroLock;

/**
 * Created by Joshua Rapoport on 11/20/17.
 * @author Joshua Rapoport
 * @version 12/11/17
 */

public class DriveTrain {
    public GyroLock lock;
    public DcMotor frontLeft, frontRight, backLeft, backRight;

    double x, y, turn;
    double pow;

    public DriveTrain(HardwareMap map) {
        lock = new GyroLock(.4,0,0);

        frontRight = map.get(DcMotor.class, "right_front");
        backRight = map.get(DcMotor.class, "right_back");
        backLeft = map.get(DcMotor.class, "left_back");
        frontLeft = map.get(DcMotor.class, "left_front");

        x = y = turn = pow = 0.0;
    }

    public void configureAuto() {
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void configureTeleop() {
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void stop() {
        driveMecanum(0, 0, 0);
    }

    public void driveMecanum(Gamepad g) {
        if (g.left_bumper) {
            pow = 0.8;
        } else {
            pow = 0.35;
        }

        double x = g.left_stick_x * pow;
        double y = g.left_stick_y * pow;
        double turn = g.right_stick_x * pow;

        if (g.right_stick_x < 0.1 && g.a) {
            turn = lock.correction() * pow;
        } else {
            lock.correction();
        }

        lock.lastEnabled = g.a;

        driveMecanum(x, y, turn);
    }

    public void driveMecanum(double x, double y, double turn) {
        double max =  Math.abs(x) + Math.abs(y) + turn;

        if (max <= 1.0) { max = 1.0; }

        frontRight.setPower((x + y + turn) / max);
        backRight.setPower((-x + y + turn) / max);
        backLeft.setPower((-x - y + turn) / max);
        frontLeft.setPower((x - y + turn) / max);
    }
}
