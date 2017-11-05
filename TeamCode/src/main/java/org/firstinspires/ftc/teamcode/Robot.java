package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.montclairrobotics.sprocket.control.BasicInput;
import org.montclairrobotics.sprocket.control.JoystickXAxis;
import org.montclairrobotics.sprocket.core.Button;
import org.montclairrobotics.sprocket.core.Sprocket;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.drive.DriveModule;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.drive.UniversalMapper;
import org.montclairrobotics.sprocket.drive.steps.Deadzone;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.drive.utils.GyroLock;
import org.montclairrobotics.sprocket.ftc.FTCButton;
import org.montclairrobotics.sprocket.ftc.FTCJoystick;
import org.montclairrobotics.sprocket.ftc.FTCMotor;
import org.montclairrobotics.sprocket.ftc.FTCRobot;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Polar;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.pipeline.Pipeline;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;

import java.util.ArrayList;

//import org.montclairrobotics.sprocket.drive.MecanumMapper;

@TeleOp(name="Sprocket Teleop", group="147")
public class Robot extends FTCRobot{

    Orientation angles; // An angle object to store the gyro angles
    BNO055IMU imu; // Gyroscope
    AngularVelocity angleRates;

    FTCMotor  frontRight, backRight, frontLeft, backLeft;



    @Override
    public void setup() {
        frontRight = new FTCMotor("right_front");
        backRight  = new FTCMotor("right_back");
        backLeft   = new FTCMotor("left_back");
        frontLeft  = new FTCMotor("left_front");
        imu = hardwareMap.get(BNO055IMU.class, "gyro");


        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters(); // Create a new parameter object for the gyro
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES; // set the angle unit parameter to
        parameters.calibrationDataFile = "gyroData.json"; // specify the gyro calibration file, see @GyroCalibration
        parameters.loggingEnabled      = true; // enable logging
        parameters.loggingTag          = "IMU"; // set the logging tag
        imu.initialize(parameters);
        DriveModule[] modules = new DriveModule[4];

        //Mecanum
        modules[0] = new DriveModule(new XY(1,1),  new XY(1,-1),  frontRight);
        modules[1] = new DriveModule(new XY(1,-1),  new XY(-1,-1), backRight );
        modules[2] = new DriveModule(new XY(-1,-1),  new XY(-1,1), backLeft  );
        modules[3] = new DriveModule(new XY(-1,1), new XY(1,1), frontLeft );


        DriveTrain driveTrain = new DriveTrain(modules);
        angles = imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);



        driveTrain.setDefaultInput(new BasicInput(new FTCJoystick(gamepad1, FTCJoystick.STICK.LEFT), new JoystickXAxis(new FTCJoystick(gamepad1, FTCJoystick.STICK.RIGHT))));

        /*
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
        );*/

        ArrayList<Step<DTTarget>> steps = new ArrayList<>();
        steps.add(new Deadzone(0.1, 0.1));

        GyroCorrection gyroCorrection = new GyroCorrection(
                new Input<Double>() {
                    @Override
                    public Double get() {
                        return (double) -angles.thirdAngle;
                    }
                },
                new PID(.5, 0, 0).setMinMax(-180,180,-.5,.5), 1000, 1);
        steps.add(gyroCorrection);
        //steps.add(autoBalance);
        driveTrain.setPipeline(new Pipeline<>(steps));

        driveTrain.setMapper(new UniversalMapper());


        GyroLock lock = new GyroLock(gyroCorrection);
        //new Button(new FTCButton(GAMEPAD.A, FTCButton.BUTTON.y)).setAction(autoBalance);
        new Button(new FTCButton(GAMEPAD.A, FTCButton.BUTTON.a)).setAction(lock);
    }

    @Override
    public void enableMode(Sprocket.MODE mode) {
    }

    @Override
    public void teleopInit() {

    }

    @Override
    public void testInit() {

    }

    @Override
    public void disable() {

    }

    @Override
    public void update() {
        angles = imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);

        angleRates = imu.getAngularVelocity();

        Debug.msg("First angle Y", AngleUnit.DEGREES.fromUnit(AngleUnit.DEGREES, angles.firstAngle));// X
        Debug.msg("Second angle X", AngleUnit.DEGREES.fromUnit(AngleUnit.DEGREES, angles.secondAngle)); // Y
        Debug.msg("Third amgle Z", AngleUnit.DEGREES.fromUnit(AngleUnit.DEGREES, angles.thirdAngle));// Negative heading (Z)

        Debug.msg("Y rotation rate", angleRates.yRotationRate);
        Debug.msg("Z rotation rate", angleRates.zRotationRate);
        Debug.msg("X rotation rate", angleRates.xRotationRate);
    }

    @Override
    public void disabledUpdate() {

    }

    @Override
    public void debugs() {

    }
}
