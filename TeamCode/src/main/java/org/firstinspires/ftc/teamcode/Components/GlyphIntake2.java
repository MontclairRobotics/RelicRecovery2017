package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.CompTeleop;

/**
 * Created by Montclair Robotics on 11/13/17.
 * @author Rafi
 * */

public class GlyphIntake2 {

    private double[] servoOpen;// = {, .751-.581, 0, 0};
    private double[] servoClose;// = {0, 0, .926-.883, .974-.866};

    private final int[] topServos = {0,1};
    private final int[] bottomServos = {2,3};

    private Servo[] servos;

    public GlyphIntake2(HardwareMap map) {
        this.servos = new Servo[] {
            map.get(Servo.class, "intake_right_top"),
            map.get(Servo.class, "intake_left_top"),
            map.get(Servo.class, "intake_right_bottom"),
            map.get(Servo.class, "intake_left_bottom"),
        };

        servoOpen=new double[4];
        servoClose=new double[4];

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

}