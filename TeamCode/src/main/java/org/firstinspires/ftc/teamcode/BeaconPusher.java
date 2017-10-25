package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by sarab on 1/14/2017.
 */

public class BeaconPusher {
    DcMotor pusher;
    froshHardwareMap hardware;

    int pusherInPos = 0;
    int pusherOutPos  = 1100;
    int pusherOutHalfPos = (int)(pusherOutPos * .65);

    public void init(froshHardwareMap hwMap){
        hardware = hwMap;
        pusher = hardware.beaconMotor;
    }
    public void pusherIn(){
        pusher.setPower(0.4);
        pusher.setTargetPosition(pusherInPos);
    }
    public void pusherOutHalf(){
        pusher.setPower(.4);
        pusher.setTargetPosition(pusherOutHalfPos);
    }

    public void pusherOut(){
        pusher.setPower(0.4);
        pusher.setTargetPosition(pusherOutPos);
    }
    public int getPos(){
        return pusher.getCurrentPosition();
    }
    public boolean isCloseTo(int pos){
        return Math.abs(pusher.getCurrentPosition() - pos) < 20;
    }
}
