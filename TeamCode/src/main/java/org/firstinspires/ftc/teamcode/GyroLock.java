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

    public void update() {
        pid.setInput(gyro.getX());
        pid.update();

//        Debug.msg("GyroLock: Input", pid.getInput().intValue() + "°");
//        Debug.msg("GyroLock: Output", (int) (100 * pid.getOutput()) + "%");
    }

    public void reactivate() {
        if (!active) {
            pid.error.reset();
            pid.setTarget(gyro.getX());
        }

        active = true;
    }

    public void deactivate() {
        if (active) active = false;
    }
}
