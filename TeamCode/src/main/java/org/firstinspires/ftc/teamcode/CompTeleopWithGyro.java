package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Components.DriveTrain;
import org.firstinspires.ftc.teamcode.Components.GlyphIntake2;

/**
 * Created by Montclair Robotics on 11/13/17.
 * @author Garrett
 * */
@TeleOp(name="Teleop: Gyro Enabled")
//@Disabled
public class CompTeleopWithGyro extends OpMode {
    public DriveTrain driveTrain;
    DcMotor frontRight, backRight, frontLeft, backLeft;
    Servo[] servos;

    Gyro gyro;
    PID gyroLock;

    double lastTime;

    double zeroAngle;

    enum CTRL_MODE {ROBOT,FIELD};

    CTRL_MODE myCtrlMode;

    GlyphIntake2 intake;
    DcMotor liftA, liftB;
    DigitalChannel limitSwitch;

    @Override
    public void init() {
        gyro = new Gyro(hardwareMap);
        driveTrain = new DriveTrain(hardwareMap, gyro);

        liftA = hardwareMap.get(DcMotor.class,"lift_left");
        liftB = hardwareMap.get(DcMotor.class,"lift_right");

        servos = new Servo[4];
        servos[0] = hardwareMap.get(Servo.class, "intake_right_top");
        servos[1] = hardwareMap.get(Servo.class, "intake_left_top");
        servos[2] = hardwareMap.get(Servo.class, "intake_right_bottom");
        servos[3] = hardwareMap.get(Servo.class, "intake_left_bottom");

        intake = new GlyphIntake2(servos);

//        myCtrlMode = CTRL_MODE.ROBOT;
//        gyroLock = new PID(.06,0,0).setInputRange(-180, 180).setOutputRange(-1, 1);
//        zeroAngle = gyro.getX();
        limitSwitch = hardwareMap.get(DigitalChannel.class, "limit_switch_1");
    }

    @Override
    public void loop() {
        gyro.update();
        driveTrain.driveMecanum(gamepad1);

//        if(gamepad1.x) {
//            zeroAngle = gyro.getX();
//        }
//
//        if (gamepad1.left_trigger > 0.5) {
//            myCtrlMode = CTRL_MODE.ROBOT;
//        }
//
//        if (gamepad1.right_trigger > 0.5) {
//            myCtrlMode = CTRL_MODE.FIELD;
//        }
//
//        if(myCtrlMode == CTRL_MODE.FIELD) {
//            Vector ctrl = new XY(x,y);
//            ctrl.rotate(new Degrees(gyro.getX() - zeroAngle));
//            x = ctrl.getX();
//            y = ctrl.getY();
//        }

        if (gamepad2.a || gamepad2.x)
            intake.openBottom();
        else
            intake.closeBottom();

        if (gamepad2.b || gamepad2.x)
            intake.openTop();
        else
            intake.closeTop();

        liftA.setPower(gamepad2.left_stick_y);
        liftB.setPower(-gamepad2.left_stick_y);

        telemetry.addData("Limit Switch", limitSwitch);
        telemetry.addData("Control Mode",myCtrlMode);
        telemetry.addData("Gyroscope", gyro);
        telemetry.addData("Target", driveTrain.lock.pid.target);
        telemetry.addData("PID", driveTrain.lock);
        telemetry.addData("Error", driveTrain.lock.pid.error.last);
        telemetry.update();
    }
}
