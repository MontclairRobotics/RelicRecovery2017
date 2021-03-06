package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Robot;

/**
 * Created by MHS Robotics on 11/9/2017.
 */

public class GlyphIntake2 {

    private double[] servoOpen;// = {, .751-.581, 0, 0};
    private double[] servoClose;// = {0, 0, .926-.883, .974-.866};

    private final int[] topServos = {Robot.RIGHT_TOP,Robot.LEFT_TOP};
    private final int[] bottomServos = {Robot.RIGHT_BOTTOM,Robot.LEFT_BOTTOM};

    private Servo[] servos;

    public GlyphIntake2(Servo... servos) {
        this.servos = servos;
        servoOpen=new double[4];
        servoClose=new double[4];

        servoOpen[Robot.RIGHT_TOP]     = 0.573;
        servoOpen[Robot.LEFT_TOP]      = 0.449;
        servoOpen[Robot.RIGHT_BOTTOM]  = 0.450;
        servoOpen[Robot.LEFT_BOTTOM]   = 0.600;

        servoClose[Robot.RIGHT_TOP]    = 0.440;
        servoClose[Robot.LEFT_TOP]     = 0.560;
        servoClose[Robot.RIGHT_BOTTOM] = 0.560;
        servoClose[Robot.LEFT_BOTTOM]  = 0.530;
    }

    public void closeTop() {
        for(int i : topServos) {
            servos[i].setPosition(servoClose[i]);
        }
    }

    public void closeBottom() {
        for(int i : bottomServos) {
            servos[i].setPosition(servoClose[i]);
        }
    }

    public void closeAll() {
        closeTop();
        closeBottom();
    }


    public void openTop() {
        for(int i : topServos) {
            servos[i].setPosition(servoOpen[i]);
        }
    }

    public void openBottom() {
        for(int i : bottomServos) {
            servos[i].setPosition(servoOpen[i]);
        }
    }

    public void openAll() {
        openTop();
        openBottom();
    }

}