package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;


/**
 * Created by garrett on 1/9/17.
 */
@TeleOp(name="Teleop Competion", group="147")
public class teleop extends OpMode{
    DriveTrain driveTrain;
    froshHardwareMap hardware;
    Intake intake;
    Controller controller;
    BeaconPusher pusher;
    Shooter shooter;
    boolean intaking;
    ElapsedTime timer;
    double startTime;
    boolean setup = false;
    boolean autoIntake = false;
    @Override
    public void init() {
        hardware = new froshHardwareMap();
        hardware.init(hardwareMap);
        driveTrain = new DriveTrain();
        driveTrain.init(hardware);
        controller = new Controller();
        controller.init(gamepad1, gamepad2);
        intake = new Intake();
        intake.init(hardware);
        pusher = new BeaconPusher();
        pusher.init(hardware);
        shooter = new Shooter();
        shooter.init(hardware);
        intaking = false;
        timer = new ElapsedTime();
        startTime = timer.seconds();
        telemetry.addData("INFO","Initialized");
        intake.intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        startTime = timer.seconds();
    }

    public void loop() {

        if(!setup){
            intake.intake.setPower(1);
            intake.intakeHalf();
            setup=true;
        }
        //Drive Controls
        float multiplier;
        if(controller.getRightBumper()) {
            multiplier = 0.25f;
        }else if(controller.getLeftBumper()){
            multiplier = 1;
        }else {
            multiplier = 0.5f;
        }
        driveTrain.setDriveTank(controller.getRightPower() * multiplier, controller.getLeftPower() * multiplier);



        //Beacon Pusher Controls
        if (controller.getButtonPressed("B")){
            telemetry.addData("INFO","Beacon Out");
            telemetry.addData("INFO", pusher.getPos());
            pusher.pusherOut();
        }else{
            telemetry.addData("INFO", "Beacon Int");
            pusher.pusherIn();
        }



        //intake Controls(automatic)
        if (controller.getButtonPressed("Y")){
            telemetry.addData("Projected", intake.intakeDownPos);
            telemetry.addData("Actual", intake.getVals());
            intake.intakeDown();
            intaking = true;
            autoIntake = true;
        }else if(intaking) {
            telemetry.addData("Projected", intake.intakeUpPos);
            telemetry.addData("Actual", intake.getVals());
            intake.intakeUp();
            if (intake.isCloseTo(intake.intakeUpPos)) {
                intaking = false;
            }
        }else if(controller.dpad("UP")){
            intake.intakeDown();
        }else if(controller.dpad("DOWN")){
            intake.intakeUp();
        }else{
            intake.semiIntakeHalf();
            telemetry.addData("Projected", intake.intakeHalfPos);
            telemetry.addData("Actual", intake.getVals());
        }

        //intake Controls(manual)
        /*if(controller.dpad("UP")){
            intake.incrUp(150);
        }
        if(controller.dpad("DOWN")){
            intake.incrDown(150);
        }*/




        //Shooter Controls
        if (controller.getButtonPressed("A")){
            telemetry.addData("Shooter Pos: ",shooter.getPos());
            shooter.shooterUp();
        }else{
            telemetry.addData("Shooter Pos: ",shooter.getPos());
            shooter.shooterDown();
        }
        telemetry.addData("INFO", pusher.getPos());
        updateTelemetry(telemetry);
    }


}
