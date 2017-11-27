package org.firstinspires.ftc.teamcode.Auto.StateMachine.States;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Auto.DefaultHardwareMap;
import org.firstinspires.ftc.teamcode.Auto.DefaultMecanumMapper;
import org.firstinspires.ftc.teamcode.Auto.StateMachine.State;
import org.firstinspires.ftc.teamcode.Components.DriveTrain;
import org.firstinspires.ftc.teamcode.Components.Hardware;
import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Vector;
import org.montclairrobotics.sprocket.geometry.XY;

/**
 * Created by Montclair Robotics on 11/20/2017.
 */

public class Drive extends State {

    DefaultHardwareMap hardware;
    private Vector translation;
    private float power;

    public final static double TICKS_PER_INCH = 1500/42.3;


    public Drive(Vector translation, float power){
        hardware = new DefaultHardwareMap();
        hardware.init(Hardware.getInstance().getHardwareMap());
        this.power = power;
        this.translation = translation;
    }

    public Drive(Vector translation){
        this(translation, .5f);
    }

    public Drive(int distance, float power){
        this(new XY(0, distance), power);
    }

    public Drive(int distance){
        this(distance, .5f);
    }

    @Override
    public void start() {
        DriveTrain.getInstance().setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    @Override
    public void run() {
        DefaultMecanumMapper.map(new DTTarget(translation.normalize().scale(power), 0), hardware.driveModules);
    }

    @Override
    public void stop() {
        DefaultMecanumMapper.map(new DTTarget(Vector.ZERO, 0), hardware.driveModules);
        hardware.resetDriveEncoders();
    }

    @Override
    public boolean isDone() {
        return Math.abs(hardware.getTicks()) < translation.getMagnitude()*TICKS_PER_INCH;
    }
}
