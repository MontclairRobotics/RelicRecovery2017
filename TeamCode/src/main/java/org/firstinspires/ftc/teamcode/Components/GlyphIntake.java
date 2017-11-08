package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.hardware.Servo;

import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Utils;

/**
 * Created by thegb on 11/8/2017.
 */

public class GlyphIntake {
    Servo rightServo;
    Servo leftServo;

    public GlyphIntake(Servo rightServo, Servo leftServo) {
        this.rightServo = rightServo;
        this.leftServo = leftServo;
    }

    public void setPos(double pos){
        pos = Utils.constrain(pos, 0, 180);
        rightServo.setPosition(pos);
        leftServo.setPosition(-pos);
    }

    public double getPos(){
        return rightServo.getPosition();
    }

    public void open(double degPerSec){
        setPos(getPos() + degPerSec * Updater.getLoopTime());
    }

    public void close(double degPerSec){
        open(-degPerSec);
    }
}
