package org.firstinspires.ftc.teamcode;

import org.montclairrobotics.sprocket.actions.Action;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.XY;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;

/**
 * Created by MHS Robotics on 10/25/2017.
 */

public class AutoBalance implements Step<DTTarget>, Action {
    PID rollPID;
    PID pitchPID;
    Input<Double> roll;
    Input<Double> pitch;
    boolean enabled = false;

    public AutoBalance(PID rollPID, PID pitchPID, Input<Double> roll, Input<Double> pitch) {
        this.rollPID = rollPID;
        this.pitchPID = pitchPID;
        this.roll = roll;
        this.pitch = pitch;
    }

    @Override
    public void start() {

    }

    @Override
    public void enabled() {
        enabled = true;
        rollPID.setInput(roll);
        pitchPID.setInput(pitch);
        rollPID.update();
        pitchPID.update();
    }

    @Override
    public void stop() {

    }

    @Override
    public void disabled() {
        enabled = false;
    }

    @Override
    public DTTarget get(DTTarget dtTarget) {
        if(enabled){
            double XCorrection = dtTarget.getDirection().getX() + rollPID.get();
            double YCorrection = dtTarget.getDirection().getY() + pitchPID.get();
            return new DTTarget(new XY(XCorrection, YCorrection), 0);
        }else{
            return dtTarget;
        }
    }
}
