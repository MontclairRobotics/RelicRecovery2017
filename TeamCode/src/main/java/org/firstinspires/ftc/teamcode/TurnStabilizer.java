package org.firstinspires.ftc.teamcode;

import org.montclairrobotics.sprocket.drive.DTInput;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.pipeline.Step;
import org.montclairrobotics.sprocket.utils.Input;
import org.montclairrobotics.sprocket.utils.PID;

/**
 * Created by thegb on 10/27/2017.
 */

public class TurnStabilizer implements Step<DTTarget> {

    Input<Double> turn;
    int turnRate;
    PID pid;

    public TurnStabilizer(Input<Double> turn, int turnRate, PID pid){
        this.turnRate = turnRate;
        this.turn = turn;
        this.pid = pid;
    }


    @Override
    public DTTarget get(DTTarget dtTarget) {
        return new DTTarget(dtTarget.getDirection(), dtTarget.getTurn() + pid.get());
    }
}
