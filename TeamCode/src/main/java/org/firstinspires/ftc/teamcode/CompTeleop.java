package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

//import org.firstinspires.ftc.teamcode.Components.DriveTrain;
import org.firstinspires.ftc.teamcode.Components.GlyphIntake2;
import org.firstinspires.ftc.teamcode.Components.LobsterIntake;

/**
 * Created by Montclair Robotics on 11/13/17.
 * @Author:Garrett
 * */
@TeleOp(name="Teleop w/o Gyro")
public class CompTeleop extends OpMode {
    //public DriveTrain driveTrain;
    DcMotor frontRight, backRight, frontLeft, backLeft;
    Servo[] servos;
    Servo[] continuousServos;

    public static final int SERVORT=0,SERVOLT=1,SERVORB=2,SERVOLB=3;

    LobsterIntake intake;
    DcMotor liftA, liftB;
    DigitalChannel limitSwitch;

    @Override
    public void init() {

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
        continuousServos = new Servo[2];

        servos[0] = hardwareMap.get(Servo.class, "intake_right_top");
        servos[1] = hardwareMap.get(Servo.class, "intake_left_top");
        servos[2] = hardwareMap.get(Servo.class, "intake_right_bottom");
        servos[3] = hardwareMap.get(Servo.class, "intake_left_bottom");
        continuousServos[0] = servos[0];
        continuousServos[1] = servos[2];

        intake = new LobsterIntake(servos, continuousServos);
        limitSwitch = hardwareMap.get(DigitalChannel.class, "limit_switch_1");
    }

    @Override
    public void loop(){

        double pow = 1.0;

        if (gamepad1.left_bumper) {
            pow = 0.5;
        }

        double x = gamepad1.left_stick_x * pow * 2;
        double y = -gamepad1.left_stick_y * pow;
        double turn = gamepad1.right_stick_x * pow;

        if(gamepad1.dpad_up) {
            y=pow;
        }
        if(gamepad1.dpad_left) {
            x=-pow;
        }
        if(gamepad1.dpad_down) {
            y=-pow;
        }
        if(gamepad1.dpad_right) {
            x=pow;
        }

        frontRight.setPower(x - y + turn);
        backRight.setPower(-x - y + turn);
        backLeft.setPower(-x + y + turn);
        frontLeft.setPower(x + y + turn);

        if (gamepad2.a)
            intake.openBottom();
        else
            intake.closeBottom();

        if (gamepad2.b)
            intake.openTop();
        else
            intake.closeTop();

        intake.runTop(-gamepad2.left_stick_y);
        intake.runBottom(gamepad2.right_stick_y);

        if(gamepad2.dpad_up) {
            liftA.setPower(-.75);
            liftB.setPower(.75);
        }
        else if(gamepad2.dpad_down) {
            liftA.setPower(.75);
            liftB.setPower(-.75);
        }else{
            liftA.setPower(0);
            liftB.setPower(0);
        }

    }
}
