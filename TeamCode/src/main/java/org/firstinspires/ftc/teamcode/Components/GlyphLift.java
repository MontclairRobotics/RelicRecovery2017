package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.montclairrobotics.sprocket.core.IMotor;
import org.montclairrobotics.sprocket.ftc.FTCMotor;
import org.montclairrobotics.sprocket.motors.Module;
import org.montclairrobotics.sprocket.motors.SEncoder;
import org.montclairrobotics.sprocket.utils.PID;

/**
 * Created by thegb on 11/8/2017.
 */

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
