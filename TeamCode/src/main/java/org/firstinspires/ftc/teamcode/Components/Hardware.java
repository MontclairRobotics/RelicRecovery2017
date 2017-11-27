package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Montclair Robotics on 11/24/2017.
 */

public class Hardware {
    HardwareMap map;
    static Hardware instance;

    public Hardware(HardwareMap map){
        this.map = map;
        instance = this;
    }

    public static Hardware getInstance(){
        return instance;
    }

    public HardwareMap getHardwareMap(){
        return map;
    }
}
