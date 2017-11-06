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
    BNO055IMU gyro; // Gyroscope
    AngularVelocity angleRates;

    FTCMotor frontRight, backRight, frontLeft, backLeft;
    
    @Override
    public void setup() {
        // Set Motors
        frontRight = new FTCMotor("right_front");
        backRight  = new FTCMotor("right_back");
        backLeft   = new FTCMotor("left_back");
        frontLeft  = new FTCMotor("left_front");
        
        // Mechanum "Mapper"
        DriveModule[] modules = new DriveModule[4];
        modules[0] = new DriveModule(new XY(1,1),   new XY(1,-1),  frontRight);
        modules[1] = new DriveModule(new XY(1,-1),  new XY(-1,-1), backRight);
        modules[2] = new DriveModule(new XY(-1,-1), new XY(-1,1),  backLeft);
        modules[3] = new DriveModule(new XY(-1,1),  new XY(1,1),   frontLeft);
        
        // Set Drive Train
        DriveTrain driveTrain = new DriveTrain(modules);
        // Configure Drive Train
        FTCJoystick joystickL = new FTCJoystick(gamepad1, FTCJoystick.STICK.LEFT);
        FTCJoystick joystickR = new FTCJoystick(gamepad1, FTCJoystick.STICK.RIGHT);
        driveTrain.setDefaultInput(new BasicInput(joystickL, new JoystickXAxis(joystickR)));
        
        // Gyroscope
        gyro = hardwareMap.get(BNO055IMU.class, "gyro");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters(); // Create a new parameter object for the gyro
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES; // set the angle unit parameter to
        parameters.calibrationDataFile = "gyroData.json"; // specify the gyro calibration file, see @GyroCalibration
        parameters.loggingEnabled      = true; // enable logging
        parameters.loggingTag          = "IMU"; // set the logging tag
        gyro.initialize(parameters);

        angles = gyro.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);

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
                new PID(0.5, 0, 0).setMinMax(-180, 180, -0.5, 0.5),
                1000, 
                1);
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
        angles = gyro.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);

        angleRates = gyro.getAngularVelocity();

        Debug.msg("First angle Y", angles.firstAngle);// Y
        Debug.msg("Second angle X", angles.secondAngle); // X
        Debug.msg("Third amgle Z", angles.thirdAngle);// Negative heading (Z)

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
