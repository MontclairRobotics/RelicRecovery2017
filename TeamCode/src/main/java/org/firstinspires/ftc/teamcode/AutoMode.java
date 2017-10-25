package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import java.text.DecimalFormat;

/**
 * Created by Garrett on 1/16/2017.
 * Extended by Will on 1/20/2017
 */
@Disabled
public class AutoMode extends OpMode {

    //AutoMode Objects
    DriveTrain driveTrain;
    froshHardwareMap hardware;
    Intake intake;
    Shooter shooter;
    BeaconPusher beaconPusher;
    public int[][] motorPos;
    ElapsedTime timer;
    DeviceInterfaceModule dim;



    public void autoInit() {
        hardware = new froshHardwareMap();
        hardware.init(hardwareMap);
        telemetry.addData("INFO", "Hardware Map Init");
        intake = new Intake();
        intake.init(hardware);
        telemetry.addData("INFO", "Intake Init");
        driveTrain = new DriveTrain();
        driveTrain.autoInit(hardware);
        telemetry.addData("INFO", "Drive Train Init");
        beaconPusher = new BeaconPusher();
        beaconPusher.init(hardware);
        telemetry.addData("INFO", "Pusher Init");
        shooter = new Shooter();
        shooter.init(hardware);
        telemetry.addData("INFO", "Shooter Init");
        motorPos = driveTrain.getMotorPos();
        timer = new ElapsedTime();
        startTime = timer.milliseconds();


    }

    //AutoMode Variables

    //DIM setup
    public static final int    BLUE_LED    = 0;     // Blue LED channel on DIM
    public static final int    RED_LED     = 1;     // Red LED Channel on DIM



    //Positions
    public static int totalPos = 0;
    public static int currentPos = 0;
    public static int targetPos = 0;

    // Distances(Basic)
    public static final int DEGREES_PER_INCH = 10000 / 85; //10000 Degrees over how many inches
    public static final int SINGLE_BLOCK_DISTANCE = 24 * DEGREES_PER_INCH; //length of block converted int degrees
    public static final int HALF_BLOCK_DISTANCE = 12 * DEGREES_PER_INCH; // distance for half a block
    public static final int ONE_AND_HALF_BLOCK_DISTANCE = SINGLE_BLOCK_DISTANCE + HALF_BLOCK_DISTANCE;

    //Distances after turns or between things
    public static final int DISTANCE_AFTER_TURN = (int)(Math.sqrt(2) * 2 * SINGLE_BLOCK_DISTANCE); //distance to cover after turning in non-beacon timer side autos

    //beacon color distances
    public static final int rightBeaconDistance = (int)(4 * DEGREES_PER_INCH);
    public static final int leftBeaconDistance = (int)(9 * DEGREES_PER_INCH);

    //Turning
    public static final int Left45 = -45; // used for 45 deg turns left
    public static final int Right45 = 45; // used for 45 deg turns right
    public static final int Left90 = 2 * Left45; // used for 90 deg turns left
    public static final int Right90 = 2 * Right45; // used for 90 turns right

    //Math for turning logic
    public static final double circumference = 14 * Math.sqrt(2) * Math.PI;
    public static final double degree = circumference / 360;

    // Math for Gyro turning logic
    public static float currentAngle; // for taking angle currently
    public static int currentIntAngle; // int version of current angle
    public static int gyroA; // X quaternion value for calculating distance
    public static int gyroB; // Y quaternion value for calculating distance
    public static int gyroC; // for drive distance without error and PIDs

    //control variables
    public boolean driving = false;
    public boolean turning = false;
    public boolean beaconDriving;

    //Time and pauses
    private double startTime;
    public double pauseTime = 0.5;


    //Other Variables
    public String beaconLeftColor;
    public String beaconRightColor;

    //State Machine
    public int state = 0;

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void nextState(boolean nextState) {
        if (nextState) {
            state++;
            telemetry.addData("INFO", "State " + state + " achieved");
            startTime = timer.milliseconds();
        }
    }

    //AUTO DRIVING
    int distanceTraveled = 0;
    int driveStartingPos = 0;

    public boolean drive(int distance) {
        if (!(driving)) {
            for (int i = 0; i < driveTrain.motors[0].length; i++) {
                driveStartingPos = driveTrain.motors[0][i].getCurrentPosition();
                driveTrain.motors[0][i].setTargetPosition(driveStartingPos + distance);
            }
            for (int i = 0; i < driveTrain.motors[1].length; i++) {
                driveStartingPos = driveTrain.motors[1][i].getCurrentPosition();
                driveTrain.motors[1][i].setTargetPosition((driveStartingPos - distance));
            }
            driving = true;
        }

        distanceTraveled = driveTrain.motors[0][0].getCurrentPosition() + driveStartingPos;

        if (Math.abs(distanceTraveled - distance) < 20) {
            driving = false;
            distanceTraveled = 0;
            driveStartingPos = 0;
        }
        return !(driving);
    }





    //BEACON DRIVING (DRIVING USING THE WALL WHEELS)
    int beaconStart = 0;
    int beaconDistanceTraveled = 0;
    public boolean beaconDrive(int distance){
        if(!(beaconDriving)){
            beaconStart = driveTrain.motors[0][0].getCurrentPosition();
            beaconDriving = true;
        }
        driveTrain.motors[0][0].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveTrain.motors[0][1].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveTrain.motors[1][0].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveTrain.motors[1][1].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveTrain.motors[0][0].setPower(0.6);
        driveTrain.motors[0][1].setPower(0.6);
        driveTrain.motors[1][0].setPower(-0.5);
        driveTrain.motors[1][1].setPower(-0.5);
        beaconDistanceTraveled = driveTrain.motors[0][0].getCurrentPosition() - beaconStart;
        if(Math.abs(driveTrain.motors[0][0].getCurrentPosition() - distance) < 20){
            driveTrain.motors[0][0].setPower(0);
            driveTrain.motors[0][1].setPower(0);
            driveTrain.motors[1][0].setPower(0);
            driveTrain.motors[1][1].setPower(0);
            driveTrain.motors[0][0].setMode(DcMotor.RunMode.RUN_TO_POSITION);
            driveTrain.motors[0][1].setMode(DcMotor.RunMode.RUN_TO_POSITION);
            driveTrain.motors[1][0].setMode(DcMotor.RunMode.RUN_TO_POSITION);
            driveTrain.motors[1][1].setMode(DcMotor.RunMode.RUN_TO_POSITION);
            beaconDriving = false;
        }
        return beaconDriving;
    }

    //AUTO SHOOTING
    int shootingState = 0;
    public boolean shoot() {
        switch (shootingState){
            case 0:
                shooter.shooterUp();
                if(shooter.isCloseTo(shooter.shooterUpPos)) shootingState = 1;
                break;
            case 1:
                shooter.shooterDown();
                if(shooter.isCloseTo(shooter.shooterDownPos)){
                    shootingState = 0;
                    return true;
                }
                break;
        }
        return false;
    }

    // AUTO BEACON PUSHER
    private int beaconState = 0;
    public boolean beacon() {
       switch(beaconState) {
           case 0:
               beaconPusher.pusherOutHalf();
               if(beaconPusher.isCloseTo(beaconPusher.pusherOutHalfPos)) beaconState = 1;
               break;
           case 1:
               beaconPusher.pusherIn();
               if(beaconPusher.isCloseTo(beaconPusher.pusherInPos)) {
                   beaconState = 0;
                   return true;
               }
               break;
       }
       return false;
    }

    //Auto turning (Garrett)
    int turnStartingPos = 0;
    public boolean turn2(int degrees){
        //calculate how much each motor has to move
        int distance = (int) (degrees * degree * DEGREES_PER_INCH);
        //Get and set original positions
        if (!turning) {
            for (int i = 0; i < driveTrain.motors.length; i++) {
                for (int j = 0; j < driveTrain.motors[i].length; j++) {
                    turnStartingPos = driveTrain.motors[i][j].getCurrentPosition();
                    driveTrain.motors[i][j].setTargetPosition(turnStartingPos + distance);
                }
            }
            turning = true;
        }
        telemetry.addData("Target Position", distance);

        //calculate the average all of the motor positions
        int avgPos = 0;
        for (int i = 0; i < driveTrain.motors.length; i++) {
            for (int j = 0; j < driveTrain.motors[i].length; j++) {
                avgPos = avgPos + driveTrain.motors[i][j].getCurrentPosition();
            }
        }
        avgPos = avgPos / 4;
        telemetry.addData("AvgPos", avgPos);

        //Test if the motors have gotten to the right position
        distanceTraveled = driveTrain.motors[0][0].getCurrentPosition() + turnStartingPos;
        telemetry.addData("Distance Traveled", distanceTraveled);
        telemetry.addData("Diff", Math.abs(distanceTraveled - distance));
        if (Math.abs(distanceTraveled - distance) < 300) {
            turning = false;
            distanceTraveled = 0;
            turnStartingPos = 0;
        }
        return turning;
    }

    //AUTO TURNING (RAFI)
    private int motor1end = 0;
    public boolean turn(int degrees) {
        if(!turning) {
            int distance = (int)(degrees * degree * DEGREES_PER_INCH);
            motor1end = driveTrain.motors[0][0].getCurrentPosition() + distance;
            for(DcMotor[] row : driveTrain.motors) {
                for(DcMotor m : row) {
                    m.setTargetPosition(m.getCurrentPosition() + distance);
                }
            }
            turning = true;
        }
        if(Math.abs(driveTrain.motors[0][0].getCurrentPosition() - motor1end) < 20) {
            turning = false;
            return true;
        }
        return false;
    }

    //AUTO INTAKE
    public int intakeState = 0;
    public boolean intake(){
        switch(intakeState) {
            case 0:
                intake.intakeDown();
                if(intake.isCloseTo(intake.intakeDownPos)) intakeState = 1;
                break;
            case 1:
                intake.intakeUp();
                if(intake.isCloseTo(intake.intakeUpPos)) intakeState = 2;
                break;
            case 2:
                intake.intakeHalf();
                if(intake.isCloseTo(intake.intakeHalfPos)) {
                    intakeState = 0;
                    return true;
                }

        }
        return false;
    }

    //WAIT METHOD
    public boolean pause(double seconds){
        telemetry.addData("Seconds", getSec());
        telemetry.addData("Target Seconds",seconds);
        if(getSec() > seconds){
            return true;
        }
        return false;
    }

    public double getMillis() {
        return timer.milliseconds() - startTime;
    }

    public double getSec() {
        return getMillis() / 1000.0;
    }

    @Override
    public void init() {
    }

    @Override
    public void loop() {
    }
}
