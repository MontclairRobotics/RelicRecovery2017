package org.firstinspires.ftc.teamcode;

/**
 * @author Joshua Rapoport
 * @version 11/27/17.
 */

public class GyroBalance {
    private Gyro gyro;
    private PID xPID, yPID;

    public GyroBalance(PID x, PID y) {
        this.gyro = Gyro.current;
        this.xPID = x;
        this.yPID = y;

        xPID.setTarget(0.0);
        yPID.setTarget(0.0);

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
    }
}
