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

    JewelColor jewelColor;
    int x = 0;
    int colorSum;
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
                jewelArm.setPosition(1);
                if(jewelArm.getPosition()==1){
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
                telemetry.addData("COLOR",jewelColor);

        }



    }
}
