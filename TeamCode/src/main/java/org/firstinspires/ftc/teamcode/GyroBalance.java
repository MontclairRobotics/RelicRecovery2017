package org.firstinspires.ftc.teamcode;

import org.montclairrobotics.sprocket.utils.Debug;

/**
 * Created by Joshua Rapoport on 11/27/17.
 */

public class GyroBalance {
    private Gyro gyro;
    private PID xPID, yPID;
    private boolean active;

    public GyroBalance(PID x, PID y) {
        this.gyro = Gyro.current;
        this.xPID = x;
        this.yPID = y;

        xPID.setInputRange(-45, 45);
        yPID.setInputRange(-45, 45);

        xPID.setOutputRange(-1.0, 1.0);
        yPID.setOutputRange(-1.0, 1.0);
    }

    public double correctionX() { return xPID.getOutput(); }
    public double correctionY() { return yPID.getOutput(); }

    public void update() {
        xPID.setInput(gyro.getY()); // TODO: double check please
        yPID.setInput(gyro.getZ()); // TODO: double check please

        xPID.update();
        yPID.update();

        Debug.msg("GyroBalance: Input", xPID.getInput().intValue() + "°, " + xPID.getInput().intValue() + "°");
        Debug.msg("GyroBalance: Output", (int) (100 * xPID.getOutput()) + "%, " + (int) (100 * xPID.getOutput()) + "%");
    }

    public void reactivate() {
        if (!active) {
            xPID.error.reset();
            xPID.setTarget(gyro.getX());
        }

        active = true;
    }

    public void deactivate() {
        active = false;
    }
}
