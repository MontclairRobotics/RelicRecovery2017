package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Garrett on 12/8/16.
 */

public class Controller {
    public  static Gamepad drive = null;
    public static Gamepad shoot = null;
    public static void init(Gamepad a, Gamepad b){
        drive = a;
        shoot = b;
    }


    public float getRightPower(){
        return drive.right_stick_x + drive.left_stick_y;
    }
    public float getLeftPower() {
        return drive.right_stick_x - drive.left_stick_y;
    }
    public float getLeftY(){
        return drive.left_stick_y;
    }
    public boolean getRightBumper(){
        return drive.right_bumper;
    }
    public boolean getLeftBumper(){
        return drive.left_bumper;
    }


    public boolean getButtonPressed(String button){
        if (button.equals("A")){
            return shoot.a;
        }else if (button.equals("B")){
            return shoot.b;
        }else if (button.equals("X")){
            return shoot.x;
        }else if (button.equals("Y")){
            return shoot.y;
        }else{
            return false;
        }
    }

    public boolean dpad(String direction){
        if (direction.equals("UP")){
            return shoot.dpad_up;
        }else if (direction.equals("DOWN")){
            return shoot.dpad_down;
        }else if (direction.equals("RIGHT")){
            return shoot.dpad_right;
        }else if (direction.equals("LEFT")){
            return shoot.dpad_left;
        }else{
            return false;
        }
    }

}