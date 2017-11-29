package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Auto.Enums.AllianceColor;
import org.firstinspires.ftc.teamcode.Auto.Enums.JewelColor;

/**
 * Created by Montclair Robotics on 11/29/2017.
 */
@Autonomous(name="ahjfasdisha")
public class IHAteLIFE extends OpMode {
    ColorSensor colorSensor;
    Servo jewelArm;
    DefaultAutoMode autoMode;

    JewelColor jewelColor;
    int x = 0;
    int y = 0;
    boolean done = false;
    double startTime;

    @Override
    public void init() {
        colorSensor = hardwareMap.get(ColorSensor.class, "sensor_color");
        jewelArm    = hardwareMap.get(Servo.class,"jewel_arm");
    }

    @Override
    public void loop() {
        switch(x){
            case 0:
                jewelArm.setPosition(autoMode.JEWEL_ARM_DOWN_POS);
                if(jewelArm.getPosition()==autoMode.JEWEL_ARM_DOWN_POS){
                    x++;
                    startTime=System.currentTimeMillis();
                }
                break;

            case 1:
                if(colorSensor.red() > colorSensor.blue()){
                    jewelColor = JewelColor.RED;
                    done = true;
                }else if(colorSensor.blue()> colorSensor.red()){
                    jewelColor = JewelColor.BLUE;
                    done = true;
                }else{
                    done=System.currentTimeMillis()-startTime>5000;
                    jewelColor = JewelColor.UNKNOWN;
                }

                if(done){
                    x++;
                }
                break;

            case 2:
                switch (jewelColor){
                    case UNKNOWN:
                        jewelArm.setPosition(autoMode.JEWEL_ARM_UP_POS);
                        break;

                    case BLUE:
                        telemetry.addData("COLOR",jewelColor);
                        break;

                    case RED:
                        telemetry.addData("COLOR",jewelColor);
                        break;
                }
        }



    }
}
