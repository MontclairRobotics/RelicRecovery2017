package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.GyroLock;
import org.firstinspires.ftc.teamcode.PID;

/**
 * Created by Joshua Rapoport on 11/20/17.
 */

public class DriveTrain {
    public static final double TURN_ERROR = 0.1;

    GyroLock lock;
    DcMotor frontLeft, frontRight, backLeft, backRight;

    public DriveTrain(HardwareMap map) {
        lock = new GyroLock(new PID(0.01, 0, 0.005));

        frontRight = map.get(DcMotor.class, "right_front");
        backRight = map.get(DcMotor.class, "right_back");
        backLeft = map.get(DcMotor.class, "left_back");
        frontLeft = map.get(DcMotor.class, "left_front");

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void stop() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    /**
     * A single loop of teleop mode.
     * @param g the controller for the drive train.
     */
    public void driveMechanum(Gamepad g) {
        double pow = 1.0;

        if (g.left_bumper) {
            pow = 0.5;
        }

        double x = g.left_stick_x * pow;
        double y = g.left_stick_y * pow;
        double turn = g.right_stick_x * pow;

        if (Math.abs(turn) < TURN_ERROR) {
            lock.reactivate();
            lock.update();
            turn = lock.correction();
        } else {
            lock.deactivate();
        }

        frontRight.setPower(x + y + turn);
        backRight.setPower(-x + y + turn);
        backLeft.setPower(-x - y + turn);
        frontLeft.setPower(x - y + turn);
    }

    /**
     * A single loop of an auto mode
     * @param x going along x-axis, from -1 to 1.
     * @param y going along x-axis, from -1 to 1.
     * @param turn rotating clockwise, from -1 to 1.
     */
    public void driveMechanum(double x, double y, double turn) {
        if (turn == 0) {
            lock.reactivate();
            lock.update();
            turn = lock.correction();
        } else {
            lock.deactivate();
        }

        frontRight.setPower(x + y + turn);
        backRight.setPower(-x + y + turn);
        backLeft.setPower(-x - y + turn);
        frontLeft.setPower(x - y + turn);
    }
}
