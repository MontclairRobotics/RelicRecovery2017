package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Components.GlyphIntake;
import org.firstinspires.ftc.teamcode.Components.GlyphLift;
import org.montclairrobotics.sprocket.actions.Action;
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
import org.montclairrobotics.sprocket.drive.steps.Sensitivity;
import org.montclairrobotics.sprocket.drive.utils.GyroLock;
import org.montclairrobotics.sprocket.ftc.FTCButton;
import org.montclairrobotics.sprocket.ftc.FTCJoystick;
import org.montclairrobotics.sprocket.ftc.FTCMotor;
import org.montclairrobotics.sprocket.ftc.FTCRobot;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Polar;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.loop.Updater;
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
    //BNO055IMU imu; // Gyroscope
    AngularVelocity angleRates;

    FTCMotor  frontRight, backRight, frontLeft, backLeft;
    Servo intakeRightTop, intakeLeftTop, intakeRightBottom, intakeLeftBottom;
    GlyphLift lift;
    GlyphIntake intake;
    DigitalChannel limitSwitch;


    @Override
    public void setup() {
        frontRight        = new FTCMotor("right_front");
        backRight         = new FTCMotor("right_back");
        backLeft          = new FTCMotor("left_back");
        frontLeft         = new FTCMotor("left_front");
        limitSwitch       = hardwareMap.get(DigitalChannel.class, "limit_switch_1");
        intakeRightTop    = hardwareMap.get(Servo.class,          "servo_right_top");
        intakeLeftTop     = hardwareMap.get(Servo.class,          "servo_left_top");
        intakeRightBottom = hardwareMap.get(Servo.class,          "servo_right_bottom");
        intakeLeftBottom  = hardwareMap.get(Servo.class,          "servo_left_bottom");

        lift = new GlyphLift(new FTCMotor("lift_left"), new FTCMotor("lift_right"));
        //intake = new GlyphIntake(intakeRightTop, intakeLeftTop, intakeRightBottom, intakeLeftBottom);
        limitSwitch.setMode(DigitalChannel.Mode.INPUT);

        //imu = hardwareMap.get(BNO055IMU.class, "gyro");


        /*BNO055IMU.Parameters parameters = new BNO055IMU.Parameters(); // Create a new parameter object for the gyro
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES; // set the angle unit parameter to
        parameters.calibrationDataFile = "gyroData.json"; // specify the gyro calibration file, see @GyroCalibration
        parameters.loggingEnabled      = true; // enable logging
        parameters.loggingTag          = "IMU"; // set the logging tag
        //imu.initialize(parameters);
        //imu.write8(BNO055IMU.Register.AXIS_MAP_CONFIG,6);*/
        final DriveModule[] modules = new DriveModule[4];

        //Mecanum
        modules[0] = new DriveModule(new XY(1,1),    new XY(1,-1),  frontRight);
        modules[1] = new DriveModule(new XY(1,-1),   new XY(-1,-1), backRight );
        modules[2] = new DriveModule(new XY(-1,-1),  new XY(-1,1),  backLeft  );
        modules[3] = new DriveModule(new XY(-1,1),   new XY(1,1),   frontLeft );


        DriveTrain driveTrain = new DriveTrain(modules);
        //angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);


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
        steps.add(new Sensitivity(0.5,0.5));
        /*
        GyroCorrection gyroCorrection = new GyroCorrection(
                new Input<Double>() {
                    @Override
                    public Double get() {
                        return (double) -angles.thirdAngle;
                    }
                },

                new PID(.45, 0, 0).setMinMax(-180,180,-.5,.5), 1000, 1);

        steps.add(gyroCorrection);*/
        //steps.add(autoBalance);
        driveTrain.setPipeline(new Pipeline<>(steps));

        driveTrain.setMapper(new UniversalMapper());
        driveTrain.setDefaultInput(new BasicInput(new FTCJoystick(GAMEPAD.A, FTCJoystick.STICK.LEFT),
                new JoystickXAxis(new FTCJoystick(GAMEPAD.A, FTCJoystick.STICK.RIGHT))));


        //GyroLock lock = new GyroLock(gyroCorrection);
        //new Button(new FTCButton(GAMEPAD.A, FTCButton.BUTTON.y)).setAction(autoBalance);

        //Gyro Lock
        //new Button(new FTCButton(GAMEPAD.A, FTCButton.BUTTON.a)).setAction(lock);

        //Lift Up
        new Button(new FTCButton(GAMEPAD.B, FTCButton.BUTTON.dpad_up)).setAction(new Action() {
            @Override
            public void start() {
                lift.set(1);
            }
            @Override
            public void enabled() {

            }
            @Override
            public void stop() {
               lift.set(0);
            }
            @Override
            public void disabled() {

            }
        });

        //Lift Down
        new Button(new FTCButton(GAMEPAD.B, FTCButton.BUTTON.dpad_down)).setAction(new Action(){
            @Override
            public void start() {
               lift.set(-1);
            }
            @Override
            public void enabled() {

            }
            @Override
            public void stop() {
                lift.set(0);
            }
            @Override
            public void disabled() {

            }
        });
        /*
        //intake open
        new Button(new FTCButton(GAMEPAD.B, FTCButton.BUTTON.y)).setAction(new Action() {
            @Override
            public void start() {

                if(gamepad2.left_trigger < .5){
                    intake.setPosTop(3);
                }else if(gamepad2.right_trigger < .5){
                    intake.setPosBottom(3);
                }else{
                    intake.setPos(3);
                }
            }

            @Override
            public void enabled() {

            }

            @Override
            public void stop() {
            }

            @Override
            public void disabled() {

            }
        });

        //intake close
        new Button(new FTCButton(GAMEPAD.B, FTCButton.BUTTON.a)).setAction(new Action(){
            @Override
            public void start() {

                if(gamepad2.left_trigger < .5){
                    intake.setPosTop(0);
                }else if(gamepad2.right_trigger < .5){
                    intake.setPosBottom(0);
                }else{
                    intake.setPos(0);
                }
            }

            @Override
            public void enabled() {

            }

            @Override
            public void stop() {
            }

            @Override
            public void disabled() {

            }
        });
        */
        new Button(new FTCButton(GAMEPAD.B, FTCButton.BUTTON.a)).setAction(new Action() {
            @Override
            public void start() {

            }

            @Override
            public void enabled() {
                intakeRightTop.setPosition(intakeRightTop.getPosition() + 5);
            }

            @Override
            public void stop() {

            }

            @Override
            public void disabled() {

            }
        });
        new Button(new FTCButton(GAMEPAD.B, FTCButton.BUTTON.b)).setAction(new Action() {
            @Override
            public void start() {

            }

            @Override
            public void enabled() {
                intakeRightTop.setPosition(intakeRightTop.getPosition() - 5);
            }

            @Override
            public void stop() {

            }

            @Override
            public void disabled() {

            }
        });
    }

    @Override
    public void enableMode(Sprocket.MODE mode) {
    }

    @Override
    public void teleopInit() {

    }

    @Override
    public void autoInit() {

    }

    @Override
    public void testInit() {

    }

    @Override
    public void disable() {

    }

    @Override
    public void update() {
        //angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        //angleRates = imu.getAngularVelocity();

        /*Debug.msg("First angle Z", angles.firstAngle);// X
        Debug.msg("Second angle Y", angles.secondAngle); // Y
        Debug.msg("Third angle X",  angles.thirdAngle);// Negative heading (Z)

         Debug.msg("Z rotation rate", angleRates.zRotationRate);
        Debug.msg("X rotation rate", angleRates.xRotationRate);*/
        Debug.msg("Servo",intakeRightTop.getPosition());
        Debug.msg("Switch Value:", limitSwitch.getState());
    }

    @Override
    public void disabledUpdate() {

    }

    @Override
    public void debugs() {

    }
}
