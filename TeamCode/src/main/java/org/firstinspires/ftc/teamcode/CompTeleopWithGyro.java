package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Auto.DefaultAutoMode;
//import org.firstinspires.ftc.teamcode.Components.DriveTrain;
import org.firstinspires.ftc.teamcode.Components.GlyphIntake2;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;

/**
 * Created by Montclair Robotics on 11/13/17.
 * @Author:Garrett
 * */
@TeleOp(name="Teleop: Gyro Enabled")
//@Disabled
public class CompTeleopWithGyro extends OpMode {
    //public DriveTrain driveTrain;
    DcMotor frontRight, backRight, frontLeft, backLeft;
    Servo[] servos;

    Gyro gyro;
    PID gyroLock;
    boolean lastEnabled;
    double zeroAngle;

    enum CTRL_MODE {ROBOT,FIELD};

    CTRL_MODE myCtrlMode;

    GlyphIntake2 intake;
    DcMotor liftA, liftB;
    DigitalChannel limitSwitch;

    @Override
    public void init() {
        //this.driveTrain = new DriveTrain(hardwareMap);

        frontRight = hardwareMap.get(DcMotor.class, "right_front");
        backRight = hardwareMap.get(DcMotor.class, "right_back");
        backLeft = hardwareMap.get(DcMotor.class, "left_back");
        frontLeft = hardwareMap.get(DcMotor.class, "left_front");

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        liftA = hardwareMap.get(DcMotor.class,"lift_left");
        liftB = hardwareMap.get(DcMotor.class,"lift_right");

        servos = new Servo[4];
        servos[0] = hardwareMap.get(Servo.class, "intake_right_top");
        servos[1] = hardwareMap.get(Servo.class, "intake_left_top");
        servos[2] = hardwareMap.get(Servo.class, "intake_right_bottom");
        servos[3] = hardwareMap.get(Servo.class, "intake_left_bottom");

        intake = new GlyphIntake2(servos);
        gyro = new Gyro(hardwareMap);
        myCtrlMode=CTRL_MODE.ROBOT;
        gyroLock=new PID(.04,0,0.4, -180, 180, -1.0, 1.0);
        zeroAngle=gyro.get().getX();
        limitSwitch = hardwareMap.get(DigitalChannel.class, "limit_switch_1");
    }

    @Override
    public void loop(){
        double pow = 1;

        if (gamepad1.left_bumper) {
            pow = 0.5; //maybe lower
        }

        double x = 0.0;
        double y = 0.0;
        double turn = gamepad1.right_stick_x * pow;

        if (gamepad1.dpad_up)
            y = pow;
        else if (gamepad1.dpad_down)
            y = -pow;
        else
            y = -gamepad1.left_stick_y * pow;

        if (gamepad1.dpad_left)
            x = -pow;
        else if (gamepad1.dpad_right)
            x=pow;
        else
            x = gamepad1.left_stick_x * pow;

        if(gamepad1.x) {
            zeroAngle = gyro.get().getX();
        }
//
//        if (gamepad1.a && !lastEnabled) {
//            gyroLock.setTarget(-gyro.get().getX());
//            turn = gyroLock.get(-gyro.get().getX());
//        }
//
//        lastEnabled = gamepad1.a;

        if (gamepad1.left_trigger > 0.5) {
            myCtrlMode = CTRL_MODE.ROBOT;
        }

        if (gamepad1.right_trigger > 0.5) {
            myCtrlMode = CTRL_MODE.FIELD;
        }

        if(myCtrlMode == CTRL_MODE.FIELD) {
            Vector ctrl = new XY(x,y);
            ctrl.rotate(new Degrees(gyro.get().getX() - zeroAngle));
            x = ctrl.getX();
            y = ctrl.getY();
        }

        double fr =  x - y + turn;
        double br = -x - y + turn;
        double bl = -x + y + turn;
        double fl =  x + y + turn;

        double max = Math.max(1.0, Math.max(Math.max(Math.abs(fr), Math.abs(br)), Math.max(Math.abs(bl), Math.abs(fl))));

        frontRight.setPower(fr / max);
        backRight.setPower(br / max);
        backLeft.setPower(bl/max);
        frontLeft.setPower(fr/max);

        if (gamepad2.a || gamepad2.x)
            intake.openBottom();
        else
            intake.closeBottom();

        if (gamepad2.b||gamepad2.x)
            intake.openTop();
        else
            intake.closeTop();

        liftA.setPower(gamepad2.left_stick_y);
        liftB.setPower(-gamepad2.left_stick_y);

        telemetry.addData("Limit Switch", limitSwitch);
        telemetry.addData("Control Mode",myCtrlMode);
        telemetry.addData("Gyro Angle",  (int) gyro.get().getX() + " deg");
        telemetry.addData("Turn: ", turn);
        telemetry.update();
    }
}
