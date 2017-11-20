package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Montclair Robotics on 11/20/2017.
 */

public class Debug {

    public static Debug instance;
    public static Telemetry telemetry;

    public Debug(Telemetry telemetry){
        instance = this;
        this.telemetry = telemetry;
    }

    public void log(String key, Object value){
        telemetry.addData(key, value);
    }

    public void msg(String msg){
        log("Debug: ", msg);
    }

    public Debug getInstance(){
        return instance;
    }
}
