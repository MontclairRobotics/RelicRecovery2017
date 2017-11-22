package org.firstinspires.ftc.teamcode.Auto.StateMachine.States;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Auto.StateMachine.State;
import org.firstinspires.ftc.teamcode.Components.DriveTrain;

/**
 * Created by Montclair Robotics on 11/20/2017.
 */

public class Drive extends State {

    DriveTrain dt;
    private double inches;
    private float power;
    private int position;

    public final static double TICKS_PER_INCH = 1500/42.3;
    public final static double TICKS_PER_DEGREE = (43.23/360)*TICKS_PER_INCH*2.5;


    public Drive(double in, float pow){
        inches = in;
        power = pow;
        dt = DriveTrain.getInstance();
    }

    public Drive(double in){
        this(in, 50);
    }

    @Override
    public void start() {
        position = (int)(TICKS_PER_INCH * inches);
        dt.resetEncoders();
        dt.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dt.setPower(power, power);

    }

    @Override
    public void run() {
        dt.setPosition(position, position);
    }

    @Override
    public void stop() {
        dt.resetEncoders();
        dt.setPower(0, 0);
    }

    @Override
    public boolean isDone() {
        return Math.abs(dt.getAvgPos() - position) < 10;
    }
}
