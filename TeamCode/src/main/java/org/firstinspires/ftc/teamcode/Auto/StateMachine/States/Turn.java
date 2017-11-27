package org.firstinspires.ftc.teamcode.Auto.StateMachine.States;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Auto.StateMachine.State;
import org.firstinspires.ftc.teamcode.Components.DriveTrain;

/**
 * Created by Montclair Robotics on 11/22/2017.
 */

public class Turn extends State {

    private double degrees;
    private double power;
    DriveTrain driveTrain;
    private double position;
    private double ticks;

    public final double TICKS_PER_INCH = 1500/42.3;
    public final double TICKS_PER_DEGREE = (43.23/360)*TICKS_PER_INCH*2.5;


    public Turn(double degrees, double power) {
        this.degrees = degrees;
        this.power = power;
        driveTrain = DriveTrain.getInstance();
        if(degrees < 0){
            this.power *= -1;
        }
    }

    @Override
    public void start() {
        driveTrain.resetEncoders();
        driveTrain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void run() {
        driveTrain.setPower(power, -power);
        ticks = driveTrain.getAvgPos();
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isDone() {
        return ticks < Math.abs(degrees*TICKS_PER_DEGREE);
    }
}
