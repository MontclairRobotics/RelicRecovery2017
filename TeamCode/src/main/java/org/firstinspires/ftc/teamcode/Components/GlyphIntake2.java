package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Name;
import org.firstinspires.ftc.teamcode.Robot;

/**
 * Created by Montclair Robotics on 11/13/17.
 * @author Rafi
 * */

public class GlyphIntake2 {

    private double[] servoOpen;// = {, .751-.581, 0, 0};
    private double[] servoClose;// = {0, 0, .926-.883, .974-.866};

    private final int[] topServos = {Robot.RIGHT_TOP,Robot.LEFT_TOP};
    private final int[] bottomServos = {Robot.RIGHT_BOTTOM,Robot.LEFT_BOTTOM};

    private Servo[] servos;

    public GlyphIntake2(HardwareMap map) {
        this.servos = new Servo[] {
                map.get(Servo.class, Name.INTAKE_TR),
                map.get(Servo.class, Name.INTAKE_TL),
                map.get(Servo.class, Name.INTAKE_BR),
                map.get(Servo.class, Name.INTAKE_BL)
        };

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