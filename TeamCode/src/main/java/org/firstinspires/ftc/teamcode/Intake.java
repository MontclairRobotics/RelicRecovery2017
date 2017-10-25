package org.firstinspires.ftc.teamcode;

/**
 * Created by garrett on 1/10/17.
 */

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Garrett on 1/10/17.
 */

public class Intake {
    public DcMotor intake;
    froshHardwareMap hwMap;

    public int intakeTolerance = 20;
    final int intakeUpPos = 100;
    final int intakeDownPos = 1100;
    final int intakeHalfPos = 500;

    public void init(froshHardwareMap hwMap){
        this.hwMap = hwMap;
        intake = this.hwMap.intakeMotor;
    }

    public void intakeUp(){
        intake.setPower(1);
        intake.setTargetPosition(intakeUpPos);
    }

    public void intakeUpSlow(){
        intake.setPower(.5);
        intake.setTargetPosition(intakeUpPos);
    }

    public void intakeDown(){
        intake.setPower(1);
        intake.setTargetPosition(intakeDownPos);
    }

    public void intakeDownSlow(){
        intake.setPower(.4);
        intake.setTargetPosition(intakeDownPos);
    }

    public void intakeHalf() {
        intake.setPower(.7);
        intake.setTargetPosition(intakeHalfPos);
    }

    public void semiIntakeHalf(){
        intake.setPower(.3);
        intake.setTargetPosition(intakeHalfPos);
    }
    public void setPos(int pos){ intake.setTargetPosition(pos);}
    public double getVals(){
        return intake.getCurrentPosition();
    }
    public void incrUp(int incr){
        intake.setTargetPosition(intake.getCurrentPosition() + incr);
    }
    public void incrDown(int incr){
        intake.setTargetPosition(intake.getCurrentPosition() - incr);
    }
    public boolean isCloseTo(double val) {
        return Math.abs(val - intake.getCurrentPosition()) < intakeTolerance;
    }
    
}
