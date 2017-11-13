package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.montclairrobotics.sprocket.ftc.FTCRobot;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Input;

/**
 * Created by Hymowitz on 11/9/2017.
 */

public class Gyro implements Input<Double>, Updatable {
    double x, y, z;

    private RRQuaternion quat; // An angle object to store the gyro angles
    private BNO055IMU imu; // Gyroscope

    public Gyro() {
        x = y = z = 0;

        imu = FTCRobot.ftcHardwareMap.get(BNO055IMU.class, "gyro");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters(); // Create a new parameter object for the gyro
        parameters.angleUnit            = BNO055IMU.AngleUnit.DEGREES; // set the angle unit parameter to
        parameters.calibrationDataFile  = "gyroData.json"; // specify the gyro calibration file, see @GyroCalibration
        parameters.loggingEnabled       = true; // enable logging
        parameters.loggingTag           = "IMU"; // set the logging tag

        imu.initialize(parameters);
        imu.write8(BNO055IMU.Register.AXIS_MAP_CONFIG, 6);

        update();
        Updater.add(this, Priority.INPUT);
    }

    @Override
    public void update() {
        quat = new RRQuaternion(imu.getQuaternionOrientation());
        x = (double) ((int) (10*quat.getX())) / 10;
        y = (double) ((int) (10*quat.getY())) / 10;
        z = (double) ((int) (10*quat.getZ())) / 10;
    }

    @Override
    public Double get() {
        return quat.getZ();
    }
}
