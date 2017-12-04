package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.teamcode.PID;
import org.firstinspires.ftc.teamcode.Gyro;
import org.firstinspires.ftc.teamcode.GyroLock;

/**
 * Created by Joshua Rapoport on 11/20/17.
 * @author Joshua Rapoport
 * @version 12/4/17
 * @see GyroLock
 */

public class DriveTrain {
    public GyroLock lock;
    DcMotor frontLeft, frontRight, backLeft, backRight;

    double x, y, turn;
    double pow;

    public DriveTrain(HardwareMap map, Gyro gyro) {
        lock = new GyroLock(new PID(.06,0,0).setInRange(-180, 180).setOutRange(-1, 1), gyro);

        frontRight = map.get(DcMotor.class, "right_front");
        backRight = map.get(DcMotor.class, "right_back");
        backLeft = map.get(DcMotor.class, "left_back");
        frontLeft = map.get(DcMotor.class, "left_front");

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        x = y = turn = pow = 0.0;
    }

    public void stop() {
        driveMecanum(0, 0, 0);
    }

    public void driveMecanum(Gamepad g) {
        if (g.left_bumper) {
            pow = 0.5;
        } else {
            pow = 1.0;
        }

        double x = g.left_stick_x * pow;
        double y = g.left_stick_y * pow;
        double turn = g.right_stick_x * pow;

        if (g.a) {
            turn = lock.correction();
        } else {
            lock.correction();
        }

        lock.lastEnabled = g.a;

        driveMecanum(x, y, turn);
    }

    public void driveMecanum(double x, double y, double turn) {
        double max = Math.abs(x) + Math.abs(y) + Math.abs(turn);

        if (max <= 1.0) {
            frontRight.setPower(x + y + turn);
            backRight.setPower(-x + y + turn);
            backLeft.setPower(-x - y + turn);
            frontLeft.setPower(x - y + turn);
        } else {
            frontRight.setPower((x + y + turn) / max);
            backRight.setPower((-x + y + turn) / max);
            backLeft.setPower((-x - y + turn) / max);
            frontLeft.setPower((x - y + turn) / max);
        }
    }
}
