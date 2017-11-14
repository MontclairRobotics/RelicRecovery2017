package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Components.GlyphIntake2;
import org.firstinspires.ftc.teamcode.Components.GlyphLift;
import org.montclairrobotics.sprocket.actions.Action;
import org.montclairrobotics.sprocket.control.BasicInput;
import org.montclairrobotics.sprocket.control.JoystickXAxis;
import org.montclairrobotics.sprocket.control.JoystickYAxis;
import org.montclairrobotics.sprocket.core.Button;
import org.montclairrobotics.sprocket.core.Sprocket;
import org.montclairrobotics.sprocket.drive.ControlledMotor;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.drive.DriveModule;
import org.montclairrobotics.sprocket.drive.DriveTrain;
import org.montclairrobotics.sprocket.drive.NewMecanum;
import org.montclairrobotics.sprocket.drive.steps.Deadzone;
import org.montclairrobotics.sprocket.drive.steps.Sensitivity;
import org.montclairrobotics.sprocket.drive.steps.Squared;
import org.montclairrobotics.sprocket.ftc.FTCButton;
import org.montclairrobotics.sprocket.ftc.FTCJoystick;
import org.montclairrobotics.sprocket.ftc.FTCMotor;
import org.montclairrobotics.sprocket.ftc.FTCRobot;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.pipeline.Pipeline;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Debug;
import org.montclairrobotics.sprocket.utils.Input;

import java.util.ArrayList;

//import org.montclairrobotics.sprocket.drive.DefaultMecanumMapper;

@TeleOp(name="Sprocket Teleop", group="147")
@Disabled
public class Robot extends FTCRobot {
    Gyro gyro;

    FTCMotor  frontRight, backRight, frontLeft, backLeft;
    FTCMotor  liftLeft, liftRight;
    GlyphLift lift;
    GlyphIntake2 intake;
    DigitalChannel limitSwitch;
    Servo[] servos;

    public static int RIGHT_TOP=0;
    public static int LEFT_TOP = 1;
    public static int RIGHT_BOTTOM = 2;
    public static int LEFT_BOTTOM=3;

    @Override
    public void setup() {
        frontRight        = new FTCMotor("right_front");
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backRight         = new FTCMotor("right_back");
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backLeft          = new FTCMotor("left_back");
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        frontLeft         = new FTCMotor("left_front");
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        limitSwitch       = hardwareMap.get(DigitalChannel.class, "limit_switch_1");

        servos = new Servo[4];
        servos[0] = hardwareMap.get(Servo.class, "intake_right_top");
        servos[1] = hardwareMap.get(Servo.class, "intake_left_top");
        servos[2] = hardwareMap.get(Servo.class, "intake_right_bottom");
        servos[3] = hardwareMap.get(Servo.class, "intake_left_bottom");

        liftLeft = new FTCMotor("lift_left");
        liftRight = new FTCMotor("lift_right");
        lift = new GlyphLift(liftLeft, liftRight);
        intake=new GlyphIntake2(servos);
        limitSwitch.setMode(DigitalChannel.Mode.INPUT);

        gyro = new Gyro();
        final DriveModule[] modules = new DriveModule[4];

        //Mecanum
        modules[0] = new DriveModule(new XY(1, 1),    new XY(1,-1),  frontRight);
        modules[1] = new DriveModule(new XY(1, -1),   new XY(-1,-1), backRight );
        modules[2] = new DriveModule(new XY(-1, -1),  new XY(-1,1),  backLeft  );
        modules[3] = new DriveModule(new XY(-1, 1),   new XY(1,1),   frontLeft );


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
        final Sensitivity sensitivity=new Sensitivity(1,0.6);
        steps.add(sensitivity);
        steps.add(new Squared());
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

        final Input<Vector> fullJoystick=new FTCJoystick(GAMEPAD.A, FTCJoystick.STICK.LEFT);
        final Input<Vector> halfJoystick=new FTCJoystick(GAMEPAD.A, FTCJoystick.STICK.DPAD);

        driveTrain.setMapper(new NewMecanum());
        driveTrain.setDefaultInput(new BasicInput(new Input<Vector>(){
            @Override
            public Vector get() {
                return halfJoystick.get().scale(0.7).add(fullJoystick.get());
            }
        },
                new JoystickXAxis(new FTCJoystick(GAMEPAD.A, FTCJoystick.STICK.RIGHT))));


        //GyroLock lock = new GyroLock(gyroCorrection);
        //new Button(new FTCButton(GAMEPAD.A, FTCButton.BUTTON.y)).setAction(autoBalance);

        //Gyro Lock
        //new Button(new FTCButton(GAMEPAD.A, FTCButton.BUTTON.a)).setAction(lock);

        //Lift Up
//        new Button(new FTCButton(GAMEPAD.B, FTCButton.BUTTON.dpad_up)).setAction(new Action() {
//            @Override
//            public void start() {
//                lift.set(1);
//            }
//            @Override
//            public void enabled() {
//
//            }
//            @Override
//            public void stop() {
//               lift.set(0);
//            }
//            @Override
//            public void disabled() {
//
//            }
//        });
//
//        //Lift Down
//        new Button(new FTCButton(GAMEPAD.B, FTCButton.BUTTON.dpad_down)).setAction(new Action(){
//            @Override
//            public void start() {
//               lift.set(-1);
//            }
//            @Override
//            public void enabled() {
//
//            }
//            @Override
//            public void stop() {
//                lift.set(0);
//            }
//            @Override
//            public void disabled() {
//
//            }
//        });

        new ControlledMotor(liftLeft, new JoystickYAxis(new FTCJoystick(GAMEPAD.B, FTCJoystick.STICK.LEFT)).negate());
        new ControlledMotor(liftRight, new JoystickYAxis(new FTCJoystick(GAMEPAD.B, FTCJoystick.STICK.LEFT)).negate());

        //Top Servos
        new Button(new FTCButton(GAMEPAD.B, FTCButton.BUTTON.b)).setAction(new Action(){
            @Override
            public void start() {
            }

            @Override
            public void enabled() {
                intake.openTop();
            }

            @Override
            public void stop() {
            }

            @Override
            public void disabled() {
                intake.closeTop();
            }
        });
        //Bottom Servos
        new Button(new FTCButton(GAMEPAD.B, FTCButton.BUTTON.a)).setAction(new Action(){
            @Override
            public void start() {
            }

            @Override
            public void enabled() {
                intake.openBottom();
            }

            @Override
            public void stop() {
            }

            @Override
            public void disabled() {
                intake.closeBottom();
            }
        });

        //Half speed
        new Button(new FTCButton(GAMEPAD.A, FTCButton.BUTTON.left_bumper)).setAction(new Action(){
            @Override
            public void start() {
                sensitivity.dirScale=0.5;
                sensitivity.turnScale=0.3;
            }

            @Override
            public void enabled() {

            }

            @Override
            public void stop() {
                sensitivity.dirScale=1;
                sensitivity.turnScale=0.6;
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
        intake.openAll();
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
        Debug.msg("X-angle", gyro.x);
        Debug.msg("Y-angle", gyro.y);
        Debug.msg("Z-angle", gyro.z);

        //Debug.msg("Servo",intakeRightTop.getPosition());
        Debug.msg("Switch Value:", limitSwitch.getState());
    }

    @Override
    public void disabledUpdate() {

    }

    @Override
    public void debugs() {

    }
}
