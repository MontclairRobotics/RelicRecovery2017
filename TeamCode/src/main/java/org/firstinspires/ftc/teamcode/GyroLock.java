package org.firstinspires.ftc.teamcode;

import org.montclairrobotics.sprocket.utils.Debug;

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
        this.pid = PID.angleToPower(p, i, d);
        this.lastEnabled = false;
    }

    public double correction() {
        if(!lastEnabled) {
            pid.error.reset();
            pid.setTarget(gyro.getX());
        }

        Debug.msg("PID Updated", true);
        return pid.get(gyro.getX());
    }
}
