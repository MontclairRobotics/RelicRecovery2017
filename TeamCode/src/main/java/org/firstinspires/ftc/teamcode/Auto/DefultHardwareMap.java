package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.montclairrobotics.sprocket.geometry.XY;

/**
 * Created by MHS Robotics on 11/9/2017.
 */

public class DefultHardwareMap {

    public DcMotor frontLeft   = null;
    public DcMotor frontRight  = null;
    public DcMotor backLeft    = null;
    public DcMotor backRight   = null;
    public DcMotor glyphLeft   = null;
    public DcMotor glyphRight  = null;

    public DefultFTCDriveModule[] driveModules;

    public Servo   jewelArm    = null;
    public Servo   leftTop     = null;
    public Servo   rightTop    = null;
    public Servo   leftBottom  = null;
    public Servo   rightBottom = null;
    public ColorSensor colorSensor = null;

    DefultGlyphIntake lift;

    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    public void init(HardwareMap ahwMap){
        hwMap = ahwMap;

        frontLeft   = hwMap.dcMotor.get("left_front");
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight  = hwMap.dcMotor.get("right_front");
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft    = hwMap.dcMotor.get("left_back");
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight   = hwMap.dcMotor.get("right_back");
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        glyphLeft   = hwMap.dcMotor.get("lift_left");
        glyphLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        glyphRight  = hwMap.dcMotor.get("lift_right");

        driveModules = new DefultFTCDriveModule[4];
        driveModules[0] = new DefultFTCDriveModule(frontLeft, new XY(-1, 1), new XY(-1, 1));
        driveModules[1] = new DefultFTCDriveModule(frontRight, new XY(1, 1), new XY(1, 1));
        driveModules[2] = new DefultFTCDriveModule(backLeft, new XY(-1, -1), new XY(1, 1));
        driveModules[3] = new DefultFTCDriveModule(backRight, new XY(1, -1), new XY(-1, 1));

        jewelArm    = hwMap.servo.get("jewel_arm");
        leftTop     = hwMap.servo.get("intake_left_top");
        rightTop    = hwMap.servo.get("intake_right_top");
        leftBottom  = hwMap.servo.get("intake_left_bottom");
        rightBottom = hwMap.servo.get("intake_right_bottom");

        lift = new DefultGlyphIntake(rightBottom, leftTop, rightTop, leftBottom);


        colorSensor = hwMap.colorSensor.get("sensorColor");



        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        glyphLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        glyphRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public double getTicks() {
        double sum = 0.0;
        for(DefultFTCDriveModule module : driveModules) {
            sum += module.getMotor().getCurrentPosition();
        }
        return sum / driveModules.length;
    }

    public void resetDriveEncoders() {
        for(DefultFTCDriveModule module : driveModules) {
            module.getMotor().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            module.getMotor().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public double getLiftTicks(){
        return (glyphLeft.getCurrentPosition()+glyphRight.getCurrentPosition())/2;
    }

    public void resetLiftEncoders(){
        glyphLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        glyphLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        glyphRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        glyphRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

}
