package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Montclair Robotics on 11/13/17.
 * @Author:Rafi
 * */

public class GlyphIntake2 {

    private double[] servoOpen;// = {, .751-.581, 0, 0};
    private double[] servoClose;// = {0, 0, .926-.883, .974-.866};

    private final int[] topServos = {0,1};
    private final int[] bottomServos = {2,3};

    private Servo[] servos;

    public GlyphIntake2(Servo... servos) {
        this.servos = servos;
        servoOpen=new double[4];
        servoClose=new double[4];

        servoOpen[0]     = 0.650; //from 11/30
        servoOpen[1]      = 0.440; //from 11/30
        servoOpen[2]  = 0.400; //from 11/30
        servoOpen[3]   = 0.460; //from 11/30

        servoClose[0]    = 0.470; //from 11/30
        servoClose[1]     = 0.520; //from 11/30
        servoClose[2] = 0.500; //from 11/30
        servoClose[3]  = 0.370; //from 11/30
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