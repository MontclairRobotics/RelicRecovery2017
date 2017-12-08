package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Rafi on 12/8/2017.
 */

public class RollerIntake {
    Servo tr, tl, br, bl;

    public RollerIntake(Servo tr, Servo tl, Servo br, Servo bl) {
        this.tr = tr;
        this.tl = tl;
        this.br = br;
        this.bl = bl;
    }

    public void setTopRight(double speed){
        tr.setPosition(speed/2 + .5);
    }

    public void setTopLeft(double speed){
        tl.setPosition(speed/2 + .5);
    }

    public void setBottomRight(double speed){
        br.setPosition(speed/2 + .5);
    }

    public void setBottomLeft(double speed){
        bl.setPosition(speed/2 + .5);
    }

    public void control(Gamepad gamepad){
        setTopRight(    -gamepad.left_stick_y  - gamepad.left_stick_x);
        setTopLeft(     -gamepad.left_stick_y  + gamepad.left_stick_x);
        setBottomRight( -gamepad.right_stick_y - gamepad.right_stick_x);
        setBottomLeft(  -gamepad.right_stick_y + gamepad.right_stick_x);
    }

    public void openAll(){
        setTopRight(1);
        setTopLeft(1);
        setBottomRight(1);
        setBottomLeft(1);
    }

    public void closeAll(){
        setTopRight(-1);
        setTopLeft(-1);
        setBottomRight(-1);
        setBottomLeft(-1);
    }

    public void openTop(){
        setTopRight(1);
        setTopLeft(1);
    }
    public void closeTop(){
        setTopRight(-1);
        setTopLeft(-1);
    }
    public void openBottom(){
        setBottomRight(1);
        setBottomLeft(1);
    }
    public void closeBottom(){
        setBottomRight(-1);
        setBottomLeft(-1);
    }

}
