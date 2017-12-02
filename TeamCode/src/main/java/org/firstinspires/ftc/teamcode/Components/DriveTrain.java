package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.GyroLock;
import org.firstinspires.ftc.teamcode.Name;
import org.firstinspires.ftc.teamcode.PID;

/**
 * Created by Montclair Robotics on 11/27/17
 * @author Joshua Rapoport
 * @version 12/2/17.
 */

public class DriveTrain {
    public static final double TURN_ERROR = 0.05;

    GyroLock lock;
//    GyroBalance balance;

    private double power;
    DcMotor frontLeft, frontRight, backLeft, backRight;

    public DriveTrain(HardwareMap map) {
        this.lock = new GyroLock(new PID(0.04, 0, 0.02)); // TODO: Calibrate GyroLock

//        this.balance = new GyroBalance(new PID(0, 0, 0), new PID(0, 0, 0)); // TODO: Calibrate GyroBalance

        this.power = 0;

        frontRight = map.get(DcMotor.class, Name.DRIVETRAIN_FR);
        backRight = map.get(DcMotor.class, Name.DRIVETRAIN_BR);
        backLeft = map.get(DcMotor.class, Name.DRIVETRAIN_BL);
        frontLeft = map.get(DcMotor.class, Name.DRIVETRAIN_FL);

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void stop() {
        driveMechanum(0, 0, 0);
    }

    public void halfPower(boolean h) {
        power = 0.8;

        if (h) {
            power = 0.4;
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
            if (lock.init()) {
                lock.setTarget();
            }

            lock.setInput();
            turn = lock.correction();
        } else {
            lock.stop();
        }

        frontRight.setPower(power * (x + y + turn));
        backRight.setPower(power * (-x + y + turn));
        backLeft.setPower(power * (-x - y + turn));
        frontLeft.setPower(power * (x - y + turn));
    }
}
