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

    OurQuaternion quat; // An angle object to store the gyro angles
    BNO055IMU imu; // Gyroscope
    public Gyro(String name)
    {
        imu = FTCRobot.ftcHardwareMap.get(BNO055IMU.class, "gyro");


        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters(); // Create a new parameter object for the gyro
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES; // set the angle unit parameter to
        parameters.calibrationDataFile = "gyroData.json"; // specify the gyro calibration file, see @GyroCalibration
        parameters.loggingEnabled      = true; // enable logging
        parameters.loggingTag          = "IMU"; // set the logging tag
        imu.initialize(parameters);
        imu.write8(BNO055IMU.Register.AXIS_MAP_CONFIG,6);
        update();
        Updater.add(this, Priority.INPUT);
    }

    @Override
    public void update() {
        quat=new OurQuaternion(imu.getQuaternionOrientation());
    }
    public OurQuaternion getAngles()
    {
        return quat;
    }

    @Override
    public Double get() {
        return quat.getRotZ();
    }
}
