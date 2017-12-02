package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.montclairrobotics.sprocket.ftc.FTCMotor;
import org.montclairrobotics.sprocket.motors.Module;

/**
 * Created by Montclair Robotics on 11/13/17.
 * @author Garrett
 * */

public class GlyphLift extends Module{
    FTCMotor rightMotor;
    FTCMotor leftMotor;
    public GlyphLift(FTCMotor rightMotor, FTCMotor leftMotor){
        super(rightMotor, leftMotor);
        rightMotor.direction(FTCMotor.DIRECTION.BACKWARDS);
        this.rightMotor = rightMotor;
        this.leftMotor  = leftMotor;
    }

    public void setPos(double pos){
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftMotor.setTargetPosition(pos);
        rightMotor.setTargetPosition(pos);
    }
}
