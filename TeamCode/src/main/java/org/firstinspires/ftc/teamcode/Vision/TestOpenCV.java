package org.firstinspires.ftc.teamcode.Vision;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Vision.RunGrip;

/**
 * Created by Will on 11/15/17.
 */

@Autonomous(name = "THIS BETTER WORK")
public class TestOpenCV extends OpMode{

    RunGrip grip;
    RunGripTest gripTest;

    @Override
    public void init() {
//        grip = new RunGrip();
        gripTest = new RunGripTest();

//        grip.startVuforia(); // init vuforia
        gripTest.startVuforia(); // init Vuforia
    }

    @Override
    public void start() {
//        grip.start();
        try {
            gripTest.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
            telemetry.addData("INTERRUPTED EXCEPTION",e);
        }
    }

    @Override
    public void loop() {
//        telemetry.addData("output", grip.getBlobsDetected());
        telemetry.addData("output", gripTest.getBlobsDetected());
    }
}
