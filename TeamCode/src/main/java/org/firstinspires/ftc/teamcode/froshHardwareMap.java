package org.firstinspires.ftc.teamcode;

/**
 * Created by Will on 1/9/17.
 */

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode.
 *
 * Motor channel:  Front Left  drive motor:        "left_driveA"
 * Motor channel:  Back  Left  drive motor:        "left_driveB"
 * Motor channel:  Front Right drive motor:        "right_driveA"
 * Motor channel:  Back  Right drive motor:        "right_driveB"
 * Motor channel:  Intake drive motor:             "intake"
 */

public class froshHardwareMap {

    /* Public OpMode members. */
    public DcMotor  leftMotorA   = null;
    public DcMotor  leftMotorB   = null;
    public DcMotor  rightMotorA  = null;
    public DcMotor  rightMotorB  = null;
    public DcMotor  intakeMotor  = null;
    public DcMotor  shooterMotor = null;
    public DcMotor  beaconMotor  = null;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;
        // Define and Initialize Motors
        leftMotorA   = hwMap.dcMotor.get("left_driveA");
        leftMotorB   = hwMap.dcMotor.get("left_driveB");
        rightMotorA  = hwMap.dcMotor.get("right_driveA");
        rightMotorB  = hwMap.dcMotor.get("right_driveB");
        intakeMotor  = hwMap.dcMotor.get("intake");
        shooterMotor = hwMap.dcMotor.get("shooter");
        beaconMotor  = hwMap.dcMotor.get("beacon_pusher");

        //Define and Initialize Sensors

        // Set all motors to run with encoders.
        leftMotorA.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftMotorB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotorA.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotorB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        shooterMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        beaconMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set all motors to power
        leftMotorA.setPower(0);
        leftMotorB.setPower(0);
        rightMotorA.setPower(0);
        rightMotorB.setPower(0);
        intakeMotor.setPower(1);
        shooterMotor.setPower(.7);
        beaconMotor.setPower(.5);

    }

    /***
     *
     * waitForTick implements a periodic delay. However, this acts like a metronome with a regular
     * periodic tick.  This is used to compensate for varying processing times for each cycle.
     * The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
     *
     * @param periodMs  Length of wait cycle in mSec.
     */
    public void waitForTick(long periodMs) {

        long  remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0) {
            try {
                Thread.sleep(remaining);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Reset the cycle clock for the next pass.
        period.reset();
    }

}

