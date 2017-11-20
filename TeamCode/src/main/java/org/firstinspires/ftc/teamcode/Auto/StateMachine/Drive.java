package org.firstinspires.ftc.teamcode.Auto.StateMachine;

import org.firstinspires.ftc.teamcode.Components.DriveTrain;

/**
 * Created by Montclair Robotics on 11/20/2017.
 */

public class Drive extends State {

    DriveTrain dt;
    private double inches;
    private float power;

    public final static double TICKS_PER_INCH = 1500/42.3;
    public final static double TICKS_PER_DEGREE = (43.23/360)*TICKS_PER_INCH*2.5;


    public Drive(double in, float pow){
        inches = in;
        power = pow;
        dt = DriveTrain.getInstance();

    }

    @Override
    public void start() {
    }

    @Override
    public void run() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isDone() {
        return false;
    }
}
