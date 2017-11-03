package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by thegb on 11/1/2017.
 */


@TeleOp(name="DriveTrainTest")
public class DrivetrainTest extends OpMode{

    // Define motors
    DcMotor rightFront;
    DcMotor rightBack;
    DcMotor leftBack;
    DcMotor leftFront;


    /**
     * User defined init method
     * <p>
     * This method will be called once when the INIT button is pressed.
     */
    @Override
    public void init() {
        rightFront = hardwareMap.get(DcMotor.class, "right_front");
        rightBack  = hardwareMap.get(DcMotor.class, "right_back");
        leftBack   = hardwareMap.get(DcMotor.class, "left_back");
        leftFront  = hardwareMap.get(DcMotor.class, "left_front");
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * User defined loop method
     * <p>
     * This method will be called repeatedly in a loop while this op mode is running
     */
    @Override
    public void loop() {
        if(gamepad1.right_trigger > .5) {
            if (gamepad1.a) {
                telemetry.addData("Motor", "rf");
                rightFront.setPower(1);
            } else {
                rightFront.setPower(0);
            }
            if (gamepad1.b) {
                telemetry.addData("Motor", "rb");
                rightBack.setPower(1);
            } else {
                rightBack.setPower(0);
            }
            if (gamepad1.x) {
                telemetry.addData("Motor", "lf");
                leftFront.setPower(1);
            } else {
                leftFront.setPower(0);
            }
            if (gamepad1.y) {
                telemetry.addData("Motor", "lb");
                leftBack.setPower(1);
            } else {
                leftBack.setPower(0);
            }
        }
        if(gamepad1.left_trigger > .5) {
            if (gamepad1.dpad_up) {
                rightFront.setPower(1);
                rightBack.setPower(1);
                leftFront.setPower(1);
                leftBack.setPower(1);
            }else if(gamepad1.dpad_right){
                rightFront.setPower(-1);
                rightBack.setPower(1);
                leftFront.setPower(1);
                leftBack.setPower(-1);
            }else if(gamepad1.dpad_down){
                rightFront.setPower(1);
                rightBack.setPower(1);
                leftFront.setPower(1);
                leftBack.setPower(1);
            }else if(gamepad1.dpad_left){
                rightFront.setPower(1);
                rightBack.setPower(-1);
                leftFront.setPower(-1);
                leftBack.setPower(1);
            }else{
                rightFront.setPower(0);
                rightBack.setPower(0);
                leftFront.setPower(0);
                leftBack.setPower(0);
            }

        }
        rightFront.setPower(gamepad1.right_stick_y);
        leftFront.setPower(gamepad1.left_stick_y);
        rightBack.setPower(gamepad2.right_stick_y);
        leftBack.setPower(gamepad2.left_stick_y);
    }
}
