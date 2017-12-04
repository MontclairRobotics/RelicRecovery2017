package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Montclair Robotics on 11/13/17.
 * @author Joshua Rapoport
 * @version 12/4/17
 */

public class Gyro {
    private BNO055IMU imu; // Gyroscope
    private RRQuaternion quat; // An angle object to store the gyro angles

    public Gyro(HardwareMap map) {
        imu = map.get(BNO055IMU.class, "gyro");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters(); // Create a new parameter object for the gyro
        parameters.angleUnit            = BNO055IMU.AngleUnit.DEGREES; // set the angle unit parameter to
        parameters.calibrationDataFile  = "gyroData.json"; // specify the gyro calibration file, see @GyroCalibration
        parameters.loggingEnabled       = true; // enable logging
        parameters.loggingTag           = "IMU"; // set the logging tag

        imu.initialize(parameters);
        //imu.write8(BNO055IMU.Register.AXIS_MAP_CONFIG, 6);

        quat = new RRQuaternion(imu.getQuaternionOrientation());
    }

    public void update() {
        quat.set(imu.getQuaternionOrientation());
    }

    public double getX() {
        return -quat.getX();
    }
    public double getY() {
        return -quat.getY();
    }
    public double getZ() {
        return -quat.getZ();
    }

    @Override
    public String toString() {
        return "{x: " + (int) getX() + "°, y: " + (int) getY() + "°, z: " + (int) getZ() + "°}";
    }
}
