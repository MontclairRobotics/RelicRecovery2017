package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.CompTeleop;

/**
 * Created by Montclair Robotics on 11/13/17.
 * @author Rafi
 * */

public class LobsterIntake {

    private double[] servoOpen;// = {, .751-.581, 0, 0};
    private double[] servoClose;// = {0, 0, .926-.883, .974-.866};

    private final int[] topServos = {1};
    private final int[] bottomServos = {3};

    private Servo[] servos;
    private Servo[] cont;

    public LobsterIntake(Servo[] servos, Servo[] cont) {
        this.servos = servos;
        servoOpen=new double[4];
        servoClose=new double[4];
        this.cont=cont;

        servoOpen[CompTeleop.SERVORT]     = 0.78; //from 11/30
        servoOpen[CompTeleop.SERVOLT]      = 0.4; //from 11/30
        servoOpen[CompTeleop.SERVORB]  = 0.44; //from 11/30
        servoOpen[CompTeleop.SERVOLB]   = 0.5; //from 11/30

        servoClose[CompTeleop.SERVORT]    = 0.45; //from 11/30
        servoClose[CompTeleop.SERVOLT]     = 0.5; //from 11/30
        servoClose[CompTeleop.SERVORB] = 0.55; //from 11/30
        servoClose[CompTeleop.SERVOLB]  = 0.37; //from 11/30
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


    public void runTop(double speed){
        cont[0].setPosition(speed/2 + 0.5);
    }

    public void runBottom(double speed){
        cont[1].setPosition(speed/2 + 0.5);
    }

}