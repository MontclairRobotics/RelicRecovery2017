package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Montclair Robotics on 11/22/2017.
 */

public class JewelArm {
    Servo arm;
    static JewelArm instance;
    public  JewelArm(HardwareMap hm){
        arm = hm.get(Servo.class, "Jewel Arm");
        instance = this;
    }

    public static JewelArm getInstance(){
        return instance;
    }

    public void setPos(double pos){
        arm.setPosition(pos);
    }

    public double getPos(){
        return arm.getPosition();
    }
}
