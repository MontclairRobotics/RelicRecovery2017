package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.montclairrobotics.sprocket.geometry.Vector;

/**
 * Created by Rafi on 11/9/2017.
 */

public class DefaultFTCDriveModule {

    private DcMotor motor;
    private Vector offset;
    private Vector force;
    public double temp;

    public DefaultFTCDriveModule(DcMotor motor, Vector offset, Vector force) {
        this.motor = motor;
        this.offset = offset;
        this.force = force;
    }

    public DcMotor getMotor() {
        return motor;
    }

    public Vector getOffset() {
        return offset;
    }

    public Vector getForce() {
        return force;
    }

    public void set(double power) {
        motor.setPower(power);
    }
}
