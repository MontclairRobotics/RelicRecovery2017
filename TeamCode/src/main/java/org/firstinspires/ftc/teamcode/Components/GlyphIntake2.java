package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Montclair Robotics on 11/13/17.
 * @Author:Rafi
 * */

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

        servoOpen[Robot.RIGHT_TOP]     = 0.650; //from 11/30
        servoOpen[Robot.LEFT_TOP]      = 0.440; //from 11/30
        servoOpen[Robot.RIGHT_BOTTOM]  = 0.400; //from 11/30
        servoOpen[Robot.LEFT_BOTTOM]   = 0.460; //from 11/30

        servoClose[Robot.RIGHT_TOP]    = 0.470; //from 11/30
        servoClose[Robot.LEFT_TOP]     = 0.520; //from 11/30
        servoClose[Robot.RIGHT_BOTTOM] = 0.500; //from 11/30
        servoClose[Robot.LEFT_BOTTOM]  = 0.370; //from 11/30
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