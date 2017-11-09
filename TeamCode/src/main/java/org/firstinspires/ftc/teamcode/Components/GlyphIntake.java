package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.hardware.Servo;

import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Utils;

/**
 * Created by thegb on 11/8/2017.
 */

public class GlyphIntake {
    Servo rightServoTop;
    Servo leftServoTop;
    Servo rightServoBottom;
    Servo leftServoBottom;

    double tgt;

    public GlyphIntake(Servo rightServoTop, Servo leftServoTop, Servo rightServoBottom, Servo leftServoBottom) {
        this.rightServoTop = rightServoTop;
        this.leftServoTop = leftServoTop;
        this.rightServoBottom = rightServoBottom;
        this.leftServoBottom = leftServoBottom;
    }

    public void setPosTop(double pos){
        pos = Utils.constrain(pos, 0, 180);
        rightServoTop.setPosition(pos);
        leftServoTop.setPosition(-pos);
    }

    public void setPosBottom(double pos){
        rightServoBottom.setPosition(-pos);
        leftServoBottom.setPosition(pos);
    }

    public void setPos(double pos){
        rightServoTop.setPosition(pos);
        leftServoTop.setPosition(-pos);
        rightServoBottom.setPosition(-pos);
        leftServoBottom.setPosition(pos);
        tgt=pos;
    }

    public double[] getPos(){
        double[] positions = {
                rightServoTop.getPosition(),
                rightServoBottom.getPosition(),
                leftServoTop.getPosition(),
                leftServoBottom.getPosition()
        };
        return positions;
    }

    public double getTgt()
    {
        return tgt;
    }
}
