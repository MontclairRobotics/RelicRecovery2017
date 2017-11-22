package org.firstinspires.ftc.teamcode.Auto.StateMachine.States;

import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.Auto.Enums.Color;
import org.firstinspires.ftc.teamcode.Auto.StateMachine.State;
import org.firstinspires.ftc.teamcode.Components.JewelArm;


/**
 * Created by Montclair Robotics on 11/22/2017.
 */

public class JewelColor extends State {

    ColorSensor sensor;
    Color targetColor;
    int state1;
    int state2;


    public JewelColor(Color targetColor, int state1, int state2){
        this.targetColor = targetColor;
        this.state1 = state1;
        this.state2 = state2;
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
