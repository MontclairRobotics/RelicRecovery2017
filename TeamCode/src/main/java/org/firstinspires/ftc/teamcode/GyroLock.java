package org.firstinspires.ftc.teamcode;

/**
 * @author Joshua Rapoport
 * @version 11/27/17.
 */

public class GyroLock {
    private Gyro gyro;
    private PID pid;
    private boolean active;

    public GyroLock(PID pid) {
        this.gyro = Gyro.current;
        this.pid = pid;
        this.active = false;

        pid.setTarget(gyro.getX());

        pid.setInputRange(-180, 180);
        pid.setOutputRange(-1.0, 1.0);
    }

    public double correction() {
        return pid.getOutput();
    }

    public void setTarget() {
        setTarget(gyro.getX());
    }

    public void setTarget(double t) {
        pid.setTarget(t);
    }

    public void setInput() {
        pid.setInput(gyro.getX());
    }

    public boolean init() {
        if (!active) {
            pid.error.reset();
            active = true;
            return true;
        }

        return false;
    }

    public boolean stop() {
        if (active) {
            active = false;
            return true;
        }

        return false;
    }
}
