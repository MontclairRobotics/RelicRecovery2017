package org.firstinspires.ftc.teamcode;

/**
 * Created by Joshua Rapoport on 11/16/17.
 * @author Joshua Rapoport
 * @version 12/11/17
 */

public class GyroLock {
    private Gyro gyro;
    public PID pid;
    public boolean lastEnabled;

    public GyroLock(double p, double i, double d) {
        this.gyro = Gyro.current;
        this.pid = new PID(p, i, d).setInRange(-180, 180).setOutRange(-1.0, 1.0);
        this.lastEnabled = false;
    }

    public double correction() {
        if(!lastEnabled) {
            pid.setTarget(gyro.getX());
            pid.error.reset();
        }

        return pid.get(gyro.getX());
    }
}
