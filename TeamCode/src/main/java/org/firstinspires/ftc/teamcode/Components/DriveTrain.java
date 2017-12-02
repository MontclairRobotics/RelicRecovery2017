package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.GyroLock;
import org.firstinspires.ftc.teamcode.PID;

/**
 * @author Joshua Rapoport
 * @version 11/27/17.
 */

public class DriveTrain {
    public static final double TURN_ERROR = 0.05;

    GyroLock lock;
//    GyroBalance balance;

    private double power;
    DcMotor frontLeft, frontRight, backLeft, backRight;

    public DriveTrain(HardwareMap map) {
        this.lock = new GyroLock(new PID(0.01, 0, 0.005)); // TODO: Calibrate GyroLock

//        this.balance = new GyroBalance(new PID(0, 0, 0), new PID(0, 0, 0)); // TODO: Calibrate GyroBalance

        this.power = 0;

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

    public void halfPower(boolean h) {
        if (h) {
            power = 0.5;
        } else {
            power = 1.0;
        }
    }

    /**
     * A single loop.
     * @param x going along x-axis, from -1 to 1.
     * @param y going along x-axis, from -1 to 1.
     * @param turn rotating clockwise, from -1 to 1.
     */
    public void driveMechanum(double x, double y, double turn) {
        if (Math.abs(turn) < TURN_ERROR) {
            lock.reactivate();
            lock.update();
            turn = lock.correction();
        } else {
            lock.deactivate();
        }

        frontRight.setPower(power * (x + y + turn));
        backRight.setPower(power * (-x + y + turn));
        backLeft.setPower(power * (-x - y + turn));
        frontLeft.setPower(power * (x - y + turn));
    }

//    public void autoBalance() {
//        balance.update();
//        driveMechanum(balance.correctionX(), balance.correctionY(), 0);
//    }
}
