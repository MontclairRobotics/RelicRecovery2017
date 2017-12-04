package org.firstinspires.ftc.teamcode;

/**
 * Created by Joshua Rapoport on 11/16/17.
 * @author Joshua Rapoport
 * @version 12/4/17
 * @see Gyro
 * @see PID
 * @see org.firstinspires.ftc.teamcode.Components.DriveTrain
 */

public class GyroLock {
    private Gyro gyro;
    public PID pid;
    public boolean lastEnabled;

    public GyroLock(PID pid, Gyro gyro) {
        this.gyro = gyro;
        this.pid = pid;
        this.lastEnabled = false;
    }

    public double correction() {
        if(!lastEnabled) {
            pid.error.reset();
            pid.setTarget(gyro.getX());
        }

        return pid.get(gyro.getX());
    }
}
