package org.firstinspires.ftc.teamcode.Auto;

import org.montclairrobotics.sprocket.drive.DTTarget;
import org.montclairrobotics.sprocket.geometry.Vector;

/**
 * Created by Rafi on 11/9/2017.
 */

public class DefaultMecanumMapper {

    private double maxTurn=0.1;

    public void setup(DefaultFTCDriveModule[] defaultFTCDriveModules)
    {
        if(defaultFTCDriveModules.length==0)return;
        for(DefaultFTCDriveModule module: defaultFTCDriveModules)
        {


            double t=Math.abs(module.getOffset().crossProduct(module.getForce()));
            if (t>maxTurn)
                maxTurn=t;
        }
    }

    public void map(DTTarget driveTarget, DefaultFTCDriveModule[] defaultFTCDriveModules) {
        if(defaultFTCDriveModules.length==0) return;
        Vector dir=driveTarget.getDirection();
        double turn=driveTarget.getTurn();
        double maxForce=0.1;
        double maxPow=0.1;
        for(DefaultFTCDriveModule module: defaultFTCDriveModules)
        {
            double f=Math.abs(module.getForce().normalize().dotProduct(dir.normalize()));
            if(f>maxForce)
                maxForce=f;
        }
        for(DefaultFTCDriveModule module: defaultFTCDriveModules)
        {
            double d=0;
            d=module.getForce().normalize().dotProduct(dir)/maxForce;
            double t=module.getOffset().crossProduct(module.getForce())*turn/maxTurn;
            module.temp=d+t;
            double pow=Math.abs(module.temp);
            if(pow>maxPow)
            {
                maxPow=pow;
            }
        }
        for(DefaultFTCDriveModule module: defaultFTCDriveModules)
        {
            if(maxPow>1)
            {
                module.set(module.temp/maxPow);
            }
            else
            {
                module.set(module.temp);
            }
        }
    }

}
