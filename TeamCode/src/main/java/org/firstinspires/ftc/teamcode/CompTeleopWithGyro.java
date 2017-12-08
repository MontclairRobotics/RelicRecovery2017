package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

//import org.firstinspires.ftc.teamcode.Components.DriveTrain;
import org.firstinspires.ftc.teamcode.Components.GlyphIntake2;
import org.firstinspires.ftc.teamcode.Components.RollerIntake;
import org.montclairrobotics.sprocket.geometry.Degrees;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;

/**
 * Created by Montclair Robotics on 11/13/17.
 * @author Garrett
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

    double lastTime;

    double zeroAngle;

    enum CTRL_MODE {ROBOT,FIELD};

    CTRL_MODE myCtrlMode;

    RollerIntake intake;
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

        intake = new RollerIntake(servos[0], servos[1], servos[2], servos[3]);
        gyro = new Gyro(hardwareMap);
        myCtrlMode=CTRL_MODE.ROBOT;
        gyroLock=new PID(.06,0,0, -180, 180, -1.0, 1.0);
        zeroAngle=gyro.get().getX();
        limitSwitch = hardwareMap.get(DigitalChannel.class, "limit_switch_1");
        lastTime=System.currentTimeMillis();
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
        double dt=(System.currentTimeMillis()-lastTime)/1000.0;
        lastTime=System.currentTimeMillis();

        if(gamepad2.dpad_right)
        {
            gyroLock.P+=0.2*dt;
        }
        if(gamepad2.dpad_left)
        {
            gyroLock.P-=0.2*dt;
        }
        if(gamepad2.dpad_up)
        {
            gyroLock.D+=0.2*dt;
        }
        if(gamepad2.dpad_down)
        {
            gyroLock.D-=0.2*dt;
        }

        if (gamepad1.a) {
            if(!lastEnabled) {
                gyroLock.setTarget(-gyro.get().getX());
            }

            turn = gyroLock.get(-gyro.get().getX());
        }
        else
        {
            gyroLock.get(-gyro.get().getX());
        }

        lastEnabled = gamepad1.a;

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
        backLeft.setPower(bl / max);
        frontLeft.setPower(fl / max);



        intake.control(gamepad2);

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

        telemetry.addData("Limit Switch", limitSwitch);
        telemetry.addData("Control Mode",myCtrlMode);
        telemetry.addData("Gyro Angle",  (int) gyro.get().getX() + " deg");
        telemetry.addData("Turn: ", turn);
        telemetry.addData("target",gyroLock.target);
        telemetry.addData("P",gyroLock.P);
        telemetry.addData("D",gyroLock.D);
        telemetry.addData("Error",gyroLock.lastError);
        telemetry.update();
    }
}
