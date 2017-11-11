package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by MHS Robotics on 11/9/2017.
 */

public class DefultGlyphIntake {

    private final double[] servoOpen = {.7206, .751, .883, .866};
    private final double[] servoClose = {.624, .581, .926, .974};

    private final int[] topServos = {1, 2};
    private final int[] bottomServos = {0, 3};

    private Servo[] servos;

    public DefultGlyphIntake(Servo... servos) {
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
