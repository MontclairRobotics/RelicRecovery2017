package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Components.GlyphIntake2;
import org.montclairrobotics.sprocket.geometry.XY;

/**
 * Created by Montclair Robotics on 11/13/17.
 * @Author:Will
 * */


public class DefaultHardwareMap {

    public DcMotor frontLeft   = null;
    public DcMotor frontRight  = null;
    public DcMotor backLeft    = null;
    public DcMotor backRight   = null;
    public DcMotor glyphLeft   = null;
    public DcMotor glyphRight  = null;

    public DefaultFTCDriveModule[] driveModules;

    public Servo   jewelArm    = null;
    public Servo[] servos      = null;

    public static int RIGHT_TOP=0;
    public static int LEFT_TOP=1;
    public static int RIGHT_BOTTOM=2;
    public static int LEFT_BOTTOM=3;

    GlyphIntake2 lift;
    ColorSensor colorSensor;
    DigitalChannel limitSwitch;

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

        driveModules = new DefaultFTCDriveModule[4];
        driveModules[0] = new DefaultFTCDriveModule(frontLeft, new XY(-1, 1), new XY(-1, 1));
        driveModules[1] = new DefaultFTCDriveModule(frontRight, new XY(1, 1), new XY(1, 1));
        driveModules[2] = new DefaultFTCDriveModule(backLeft, new XY(-1, -1), new XY(1, 1));
        driveModules[3] = new DefaultFTCDriveModule(backRight, new XY(1, -1), new XY(-1, 1));

        jewelArm    = hwMap.servo.get("jewel_arm");

        servos = new Servo[4];
        servos[0] = ahwMap.get(Servo.class, "intake_right_top");
        servos[1] = ahwMap.get(Servo.class, "intake_left_top");
        servos[2] = ahwMap.get(Servo.class, "intake_right_bottom");
        servos[3] = ahwMap.get(Servo.class, "intake_left_bottom");

        lift=new GlyphIntake2(servos);
        colorSensor = hwMap.get(ColorSensor.class, "sensor_color");
        colorSensor.enableLed(true);
        limitSwitch = hwMap.get(DigitalChannel.class, "limit_switch_1");



        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        glyphLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        glyphRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public double getTicks() {
        double sum = 0.0;
        for(DefaultFTCDriveModule module : driveModules) {
            sum += module.getMotor().getCurrentPosition();
        }
        return sum / driveModules.length;
    }

    public void resetDriveEncoders() {
        for(DefaultFTCDriveModule module : driveModules) {
            module.getMotor().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            module.getMotor().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public double getLiftTicks(){
        return glyphRight.getCurrentPosition();
    }

    public void resetLiftEncoders(){
        glyphLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        glyphLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        glyphRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        glyphRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

}
