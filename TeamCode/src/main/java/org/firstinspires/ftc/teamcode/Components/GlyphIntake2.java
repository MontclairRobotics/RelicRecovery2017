package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by MHS Robotics on 11/9/2017.
 */

public class GlyphIntake2 {

//    private final double[] servoOpen = {.7206, .751, .883, .866};
//    private final double[] servoClose = {.624, .581, .926, .974};
    private final double[] servoOpen = {.7206-.624, .751-.581, 0, 0};
    private final double[] servoClose = {0, 0, .926-.883, .974-.866};

    private final int[] topServos = {1, 2};
    private final int[] bottomServos = {0, 3};

    private Servo[] servos;

    public GlyphIntake2(Servo... servos) {
        this.servos = servos;
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