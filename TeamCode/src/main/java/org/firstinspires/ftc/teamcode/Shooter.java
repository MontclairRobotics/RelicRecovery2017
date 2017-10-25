package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by garrett on 1/10/17.
 */

public class Shooter {
    DcMotor shooter;
    froshHardwareMap hwMap;

    int shooterUpPos = -750;
    int shooterDownPos = 200;

    public void init(froshHardwareMap hwMap){
        this.hwMap = hwMap;
        shooter = hwMap.shooterMotor;
    }

    public void shooterUp(){
        shooter.setPower(.75);
        shooter.setTargetPosition(shooterUpPos);
    }

    public void shooterDown(){
        shooter.setPower(1);
        shooter.setTargetPosition(shooterDownPos);
    }

    public void incrUp(int incr){
        shooter.setTargetPosition(shooter.getCurrentPosition() + incr);
    }

    public int getPos(){
        return shooter.getCurrentPosition();
    }

    public void incrDown(int incr){
        shooter.setTargetPosition(shooter.getCurrentPosition() - incr);
    }
    
    public boolean isCloseTo(double val) {
        return Math.abs(val - shooter.getCurrentPosition()) < 15;
    }

}
