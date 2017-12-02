package org.firstinspires.ftc.teamcode;

import org.montclairrobotics.sprocket.utils.Input;

/**
 * Created by Joshua Rapoport on 11/14/17.
 */

public class PID {
    double P,I,D;
    double minIn,maxIn;
    double minOut,maxOut;

    double target;
    double lastError;
    double currentError;
    double rateError;
    double totalError;
    long lastUpdateTimeMillis;

    public PID(double p,double i, double d,double minIn,double maxIn, double minOut,double maxOut)
    {
        P=p;
        I=i;
        D=d;
        this.minIn=minIn; this.maxIn = maxIn;
        this.minOut=minOut; this.maxOut = maxOut;
        target=0.0;
        totalError=currentError = rateError= 0.0;
        lastUpdateTimeMillis=System.currentTimeMillis();
    }

    public void setTarget(double t)
    {
        target=t;
    }

    public double get(double in) {
        double dt = System.currentTimeMillis() - lastUpdateTimeMillis;
        lastUpdateTimeMillis = System.currentTimeMillis();


        double diff=maxIn-minIn;
        double currentError = ((target-in-minIn)%diff+diff)%diff+minIn;
        double rateError = (dt > 0) ? (lastError - currentError) / dt : 0;
        lastError=currentError;

        if (I != 0) {
            double potentialI = (totalError + lastError) * I;
            if (potentialI > maxOut)
                totalError = maxOut / I;
            else if (potentialI < minOut)
                totalError = minOut / I;
            else
                totalError += currentError * dt;
        }

        return (P * currentError) + (I * totalError) + (D * rateError);
    }
}
