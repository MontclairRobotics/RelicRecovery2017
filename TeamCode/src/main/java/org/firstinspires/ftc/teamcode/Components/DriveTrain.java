package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Joshua Rapoport on 11/20/17.
 */

public class DriveTrain {
    public static final double TURN_ERROR = 0.1;

    DcMotor frontLeft, frontRight, backLeft, backRight;

    public static DriveTrain instance;

    public DriveTrain(HardwareMap map) {
        frontRight = map.get(DcMotor.class, "right_front");
        backRight = map.get(DcMotor.class, "right_back");
        backLeft = map.get(DcMotor.class, "left_back");
        frontLeft = map.get(DcMotor.class, "left_front");

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        instance = this;
    }

    public static DriveTrain getInstance(){
        return instance;
    }

    public void stop() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    public void setMode(DcMotor.RunMode mode){
        frontLeft.setMode(mode);
        frontRight.setMode(mode);
        backLeft.setMode(mode);
        backRight.setMode(mode);
    }

    public void setPower(double rightPower, double leftPower){
        frontRight.setPower(rightPower);
        backRight.setPower(rightPower);

        frontLeft.setPower(leftPower);
        backLeft.setPower(leftPower);
    }

    public void setPosition(int rightPosision, int leftPosition){
        frontRight.setTargetPosition(rightPosision);
        backRight.setTargetPosition(rightPosision);

        frontLeft.setTargetPosition(leftPosition);
        backLeft.setTargetPosition(rightPosision);
    }

    public void resetEncoders(){
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public int getAvgPos(){
        int total = 0;
        total += frontLeft.getCurrentPosition();
        total += backLeft.getCurrentPosition();
        total += frontRight.getCurrentPosition();
        total += backRight.getCurrentPosition();
        return total / 4;
    }

    public void driveMechanum(Gamepad g) {
        double pow = 1.0;

        if (g.left_bumper) {
            pow = 0.5;
        }

        double x = g.left_stick_x * pow;
        double y = -g.left_stick_y * pow;
        double turn = g.right_stick_x * pow;


        frontRight.setPower(x - y + turn);
        backRight.setPower(-x - y + turn);
        backLeft.setPower(-x + y + turn);
        frontLeft.setPower(x + y + turn);
    }
}