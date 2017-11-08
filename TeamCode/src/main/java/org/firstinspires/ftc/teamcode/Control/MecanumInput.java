package org.firstinspires.ftc.teamcode.Control;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.montclairrobotics.sprocket.drive.DTInput;
import org.montclairrobotics.sprocket.ftc.FTCButton;
import org.montclairrobotics.sprocket.ftc.FTCDebug;
import org.montclairrobotics.sprocket.ftc.FTCJoystick;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;

/**
 * Created by thegb on 10/27/2017.
 */

public class MecanumInput implements DTInput {

    FTCJoystick joystick1;
    FTCJoystick joystick2;
    FTCButton maxButton;
    FTCButton minButton;
    float minVal;
    float maxVal;
    float medVal;

    public MecanumInput(FTCJoystick joystick1, FTCJoystick joystick2, FTCButton maxButton, FTCButton minButton, float maxVal, float minVal, float medVal){
        this.joystick1 = joystick1;
        this.joystick2 = joystick2;
        this.maxButton = maxButton;
        this.minButton = minButton;
        this.maxVal = maxVal;
        this.minVal = minVal;
        this.medVal = medVal;
    }

    public MecanumInput(FTCJoystick joystick1, FTCJoystick joystick2, FTCButton maxButton, FTCButton minButton){
         this(joystick1, joystick2, maxButton, minButton, 1, 0, 0.5f);
    }

    @Override
    public Vector getDir() {
        Vector dir = new XY(joystick2.getX(), joystick2.getY());
        if(maxButton.enabled){
            return dir.scale(maxVal);
        }else if(minButton.enabled){
            return dir.scale(maxVal);
        }else{
            return dir;
        }
    }

    @Override
    public double getTurn() {
        return joystick1.getX();
    }
}
