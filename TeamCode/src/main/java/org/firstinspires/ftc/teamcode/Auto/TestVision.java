package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

/**
 * Created by MHS Robotics on 11/8/2017.
 */
@Autonomous(name = "Test: Vision")
@Disabled
<<<<<<< HEAD:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/Auto/TestVuforia.java
public class TestVuforia extends DefaultAutoMode {
=======
public class TestVision extends DefaultAutoMode {
>>>>>>> 141fed4c84d9de77ee8b0433a2fed3d638e41f9c:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/Auto/TestVision.java
    @Override
    public void init() {
        autoInit();
    }

    @Override
    public void loop() {
        switch (state){
            case 0:
//                nextState(getPictogram());
                break;

            case 1:
                telemetry.addData("Vision",pictogram);
                pictogramDrive(pictogram);
                break;

            case 4:
                telemetry.addData("INFO","Last State Achieved");
                break;
        }
    }
}
