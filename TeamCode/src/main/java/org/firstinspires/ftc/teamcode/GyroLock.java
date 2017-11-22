package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Components.DriveTrain;
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

    public void teleopLoop(Gamepad g) {
        if (Math.abs(g.right_stick_x) < DriveTrain.TURN_ERROR) {
            // If the gyro-lock wasn't active before...
            if (!active) {
                // Reset the error record.
                pid.error.reset();
                // Set the target to the current trajectory.
                pid.setTarget(gyro.get());
            }

            // It sure is active now.
            active = true;
            // Run update loop.
            loop();
        } else {
            // DENIED!!!
            active = false;
        }
    }

    public void autoLoop() {
        //...
    }

    public void loop() {
        gyro.update();
        pid.setInput(gyro.get());
        pid.update();

        Debug.msg("PID Input", pid.getInput().intValue() + "°");
        Debug.msg("PID Output", (int) (100 * pid.getOutput()) + "%");
    }
}
