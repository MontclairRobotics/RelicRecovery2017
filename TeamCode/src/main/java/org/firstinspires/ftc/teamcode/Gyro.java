package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;

/**
 * Created by Montclair Robotics on 11/13/17.
 * @Author: Jack
 * */

public class Gyro {
    public static Gyro current;

    private RRQuaternion quat; // An angle object to store the gyro angles
    private BNO055IMU imu; // Gyroscope

    public Gyro(BNO055IMU imu) {
        this.imu = imu;
        this.quat = new RRQuaternion(0, 0, 0, 0);
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters(); // Create a new parameter object for the gyro
        parameters.angleUnit            = BNO055IMU.AngleUnit.DEGREES; // set the angle unit parameter to
        parameters.calibrationDataFile  = "gyroData.json"; // specify the gyro calibration file, see @GyroCalibration
        parameters.loggingEnabled       = true; // enable logging
        parameters.loggingTag           = "IMU"; // set the logging tag

        imu.initialize(parameters);

        current = this;
    }

    public void update() {
        quat = new RRQuaternion(imu.getQuaternionOrientation());
    }

    public Double getX() {
        double x = quat.getX();

        if (x > 0)
            return quat.getX() - 180;

        return quat.getX() + 180;
    }
    public Double getY() {
        return quat.getY();
    }
    public Double getZ() {
        return quat.getZ();
    }
}
