package org.firstinspires.ftc.teamcode.Auto.StateMachine.States;

import org.firstinspires.ftc.teamcode.Auto.StateMachine.State;
import org.firstinspires.ftc.teamcode.Components.JewelArm;

/**
 * Created by Montclair Robotics on 11/22/2017.
 */

public class SetJewelArm extends State {
    JewelArm jewelArm;
    double position;
    public static final double DOWN_POSITION = 1;
    public static final double UP_POSITION   = 0;

    public SetJewelArm(double pos){
        position = pos;
        jewelArm = JewelArm.getInstance();
    }

    @Override
    public void start() {

    }

    @Override
    public void run() {
        jewelArm.setPos(position);
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isDone() {
        return Math.abs(jewelArm.getPos() - position) < 5;
    }
}
