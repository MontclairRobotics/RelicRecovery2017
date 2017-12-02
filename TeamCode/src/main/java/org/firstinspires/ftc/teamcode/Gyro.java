package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;

/**
 * Created by Montclair Robotics on 11/13/17.
 * @author Jack
 * */

public class Gyro {
    public static Gyro current;

    private RRQuaternion quat; // An angle object to store the gyro angles
    private BNO055IMU imu; // Gyroscope

    public Gyro(BNO055IMU imu) {
        this.imu = imu;
        this.quat = new RRQuaternion();

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
        return -quat.getX();
    }
    public Double getY() {
        return quat.getY();
    }
    public Double getZ() {
        return quat.getZ();
    }

    public String toString() {
        return "{x: " + getX().intValue() + "°,\ty: " + getY().intValue() + "°,\tz: " + getZ().intValue() + "°}";
    }
}
