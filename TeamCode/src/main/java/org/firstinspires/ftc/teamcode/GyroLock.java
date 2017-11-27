package org.firstinspires.ftc.teamcode;

import org.montclairrobotics.sprocket.utils.Debug;

/**
 * Created by Joshua Rapoport on 11/16/17.
 */

public class GyroLock {
    private Gyro gyro;
    private PID pid;
    private boolean active;

    public GyroLock(PID pid, Gyro gyro) {
        this.gyro = gyro;
        this.pid = pid;
        this.active = false;

        pid.setInputRange(-180, 180);
        pid.setOutputRange(-1.0, 1.0);
    }

    public double correction() {
        return pid.getOutput();
    }

    public void update() {
        gyro.update();
        pid.setInput(gyro.getX());
        pid.update();

        Debug.msg("PID Input", pid.getInput().intValue() + "°");
        Debug.msg("PID Output", (int) (100 * pid.getOutput()) + "%");
    }

    public void reactivate() {
        if (!active) {
            pid.error.reset();
            pid.setTarget(gyro.getX());
        }

        active = true;
    }

    public void deactivate() {
        active = false;
    }
}
