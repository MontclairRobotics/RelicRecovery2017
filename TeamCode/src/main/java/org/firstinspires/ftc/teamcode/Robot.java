package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.montclairrobotics.sprocket.core.Button;
import org.montclairrobotics.sprocket.core.Sprocket;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.drive.DriveModule;
import org.montclairrobotics.sprocket.drive.DriveTrain;
//import org.montclairrobotics.sprocket.drive.MecanumMapper;
import org.montclairrobotics.sprocket.drive.steps.Deadzone;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.ftc.FTCButton;
import org.montclairrobotics.sprocket.ftc.FTCJoystick;
import org.montclairrobotics.sprocket.ftc.FTCMotor;
import org.montclairrobotics.sprocket.ftc.FTCRobot;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Polar;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.pipeline.Pipeline;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;

import java.util.ArrayList;


public class Robot extends FTCRobot{

    private Orientation angles; // An angle object to store the gyro angles
    private BNO055IMU imu; // Gyroscope


    @Override
    public void setup() {
        imu = hardwareMap.get(BNO055IMU.class, "gyro");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters(); // Create a new parameter object for the gyro
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES; // set the angle unit parameter to
        parameters.calibrationDataFile = "gyroData.json"; // specify the gyro calibration file, see @GyroCalibration
        parameters.loggingEnabled      = true; // enable logging
        parameters.loggingTag          = "IMU"; // set the logging tag

        DriveModule[] modules = new DriveModule[4];
        modules[0] = new DriveModule(new XY(1,1), new Polar(45, new Degrees(45)), new FTCMotor("right_front"));
        modules[1] = new DriveModule(new XY(1,-1), new Polar(45, new Degrees(45)), new FTCMotor("right_back"));
        modules[2] = new DriveModule(new XY(-1,1), new Polar(45, new Degrees(45)), new FTCMotor("left_front"));
        modules[3] = new DriveModule(new XY(-1,-1), new Polar(45, new Degrees(45)), new FTCMotor("left_front"));
        DriveTrain driveTrain = new DriveTrain(modules);

        driveTrain.setDefaultInput(new MecanumInput(
                new FTCJoystick(gamepad1, FTCJoystick.STICK.RIGHT),
                new FTCJoystick(gamepad1, FTCJoystick.STICK.LEFT),
                new FTCButton(gamepad1, FTCButton.BUTTON.right_bumper),
                new FTCButton(gamepad1, FTCButton.BUTTON.left_bumper)));

        AutoBalance autoBalance = new AutoBalance(
                new PID(),
                new PID(),
                new Input<Double>() {
                    @Override
                    public Double get() {
                        return (double)angles.thirdAngle;
                    }
                },
                new Input<Double>() {
                    @Override
                    public Double get() {
                        return (double)angles.secondAngle;
                    }
                }
        );

        ArrayList<Step<DTTarget>> steps = new ArrayList<>();
        steps.add(new Deadzone(0.1, 0.1));
        steps.add(new GyroCorrection(
                new Input<Double>() {
                    @Override
                    public Double get() {
                        return (double) angles.firstAngle;
                    }
                },
                new PID(0, 0, 0), 0, 360));
        steps.add(autoBalance);
        driveTrain.setPipeline(new Pipeline<>(steps));

        // TODO: 10/27/2017 Mecanum Mapper
        //driveTrain.setMapper(new MecanumMapper());

        new Button(new FTCButton(GAMEPAD.B, FTCButton.BUTTON.y)).setAction(autoBalance);
    }

    @Override
    public void enable(Sprocket.MODE mode) {

    }

    @Override
    public void disable() {

    }

    @Override
    public void update() {
        angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES); // update the
    }

    @Override
    public void disabledUpdate() {

    }

    @Override
    public void debugs() {

    }
}
