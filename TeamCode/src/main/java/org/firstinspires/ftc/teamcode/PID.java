package org.firstinspires.ftc.teamcode;

/**
 * Created by Montclair Robotics on 11/10/2017
 * @author Joshua Rapoport
 * @version 12/2/2017
 */

public class PID {
    class Range {
        double min, max;

        Range() {
            this.min = this.max = 0;
        }
        Range(double a, double b) {
            this.min = a;
            this.max = b;
        }

        double distance() {
            return Math.abs(max - min);
        }
        int compareTo(double x) {
            if (x < min)
                return -1;
            else if (x > max)
                return 1;
            else
                return 0;
        }
    }

    class Error {
        double current, rate, total;

        Error() {
            reset();
        }
        void reset() { current = rate = total = 0; }
    }

    private double P, I, D;
    /** The acceptable range of input. */
    protected Range inRange;
    /** The acceptable range of output. */
    protected Range outRange;

    /** An object that handles error due to input-target difference. */
    protected Error error;

    /** The input from the robot, to be compared with the target. */
    private Double input;
    /** The robot's target, to be compared with the input. */
    private double target;
    /** The calculated output to correct the error. */
    private double output;

    private long lastUpdateTime;

    /**
     * @param p the proportionality constant.
     * @param i the integral constant.
     * @param d the derivative constant.
     */
    public PID(double p, double i, double d) {
        this.P = p;
        this.I = i;
        this.D = d;

        this.inRange = new Range();
        this.outRange = new Range();

        this.error = new Error();

        this.input = this.target = this.output = 0.0;

        this.lastUpdateTime = System.currentTimeMillis() / 1000;
    }

    /** Use this method to get a new output value. */
    public void setInput(double i) {
        this.updateOutput(i);
        this.input = i;
    }

    private void updateOutput(double i) {
        double d = inRange.distance();
        error.current = ((target - i - d / 2) % d + d) % d + d / 2;
        error.rate = (dTime() > 0) ? ((target - i) - error.current) / dTime() : 0;

        if (I != 0) {
            double potentialI = I * error.total;

            if (outRange.compareTo(potentialI) > 0)
                error.total = outRange.max / I;
            else if (outRange.compareTo(potentialI) < 0)
                error.total = outRange.min / I;
            else
                error.total += error.current * dTime();
        }

        lastUpdateTime = System.currentTimeMillis();

        if (outRange.compareTo(output) > 0)
            this.output = outRange.max;
        else if (outRange.compareTo(output) < 0)
            this.output = outRange.min;
        else
            this.output = (P * error.current) + (I * error.total) + (D * error.rate);
    }

    public void setInputRange(double a, double b) {
        this.inRange = new Range(a, b);
    }
    public void setOutputRange(double a, double b) {
        this.outRange = new Range(a, b);
    }

    public Double getInput() {
        return input;
    }
    public double getOutput() {
        return output;
    }

    public void setTarget(double t) { this.target = t; }

    /** @return the time difference from the last update. */
    public double dTime() {
        return (System.currentTimeMillis()) - lastUpdateTime;
    }
}
