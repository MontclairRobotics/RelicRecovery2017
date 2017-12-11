package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Components.DriveTrain;
import org.firstinspires.ftc.teamcode.Components.GlyphIntake2;
import org.firstinspires.ftc.teamcode.Gyro;
import org.montclairrobotics.sprocket.geometry.XY;

/**
 * Created by Montclair Robotics on 11/13/17.
 * @author Will
 * */


public class DefaultHardwareMap {
    public DriveTrain driveTrain = null;
    public DcMotor glyphLeft   = null;
    public DcMotor glyphRight  = null;

    public DefaultFTCDriveModule[] driveModules;

    public Servo   jewelArm    = null;

    public static int RIGHT_TOP=0;
    public static int LEFT_TOP=1;
    public static int RIGHT_BOTTOM=2;
    public static int LEFT_BOTTOM=3;

    GlyphIntake2 lift;
    Gyro gyro;
    ColorSensor colorSensor;
    DigitalChannel limitSwitch;

    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    public void init(HardwareMap ahwMap){
        hwMap = ahwMap;
        gyro = new Gyro(hwMap);
        driveTrain = new DriveTrain(ahwMap);
        driveTrain.configureAuto();

        glyphLeft   = hwMap.dcMotor.get("lift_left");
        glyphLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        glyphRight  = hwMap.dcMotor.get("lift_right");

        driveModules = new DefaultFTCDriveModule[4];
        driveModules[0] = new DefaultFTCDriveModule(driveTrain.frontLeft, new XY(-1, 1), new XY(-1, 1));
        driveModules[1] = new DefaultFTCDriveModule(driveTrain.frontRight, new XY(1, 1), new XY(1, 1));
        driveModules[2] = new DefaultFTCDriveModule(driveTrain.backLeft, new XY(-1, -1), new XY(1, 1));
        driveModules[3] = new DefaultFTCDriveModule(driveTrain.backRight, new XY(1, -1), new XY(-1, 1));

        jewelArm = hwMap.servo.get("jewel_arm");

        lift = new GlyphIntake2(ahwMap);
        colorSensor = hwMap.get(ColorSensor.class, "sensor_color");
        limitSwitch = hwMap.get(DigitalChannel.class, "limit_switch_1");

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
