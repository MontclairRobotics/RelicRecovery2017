package org.firstinspires.ftc.teamcode;

/**
 * @author Joshua Rapoport
 * @version 12/1/2017
 */

public class PID {
    public class Range {
        double min, max;

        public Range() {
            this.min = this.max = 0;
        }
        public Range(double a, double b) {
            this.min = a;
            this.max = b;
        }

        public double distance() {
            return Math.abs(max - min);
        }
        public int compareTo(double x) {
            if (x < min)
                return -1;
            else if (x > max)
                return 1;
            else
                return 0;
        }
    }

    public class Error {
        double current, rate, total;

        public Error() {
            reset();
        }

        /**
         * Update the current error, error rate, and total error.
         * @param i new input with which to update.
         */
        public void update(double i) {
            rate = ((target - i) - current) / dTime();
            current = target - i;
            total += current * dTime();
        }

        public void reset() { current = rate = total = 0; }
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

    /** Create a copy of a PID object with identical properties */
    public PID(PID pid) {
        this.P = pid.P;
        this.I = pid.I;
        this.D = pid.D;

        this.inRange = pid.inRange;
        this.outRange = pid.outRange;

        this.error = pid.error;

        this.input = pid.input;
        this.target = pid.target;
        this.output = pid.output;

        this.lastUpdateTime = pid.lastUpdateTime;
    }

    /** Use this method to get a new output value. */
    public void setInput(double i) {
        error.update(i);
        this.updateOutput();
        this.input = i;
    }

    private void updateOutput() {
        double d = inRange.distance();
        if (d != 0) {
            error.current = ((error.current - inRange.min) % d + d) % d + inRange.min;
        }

        if (I != 0) {
            double potentialI = I * error.total;

            if (outRange.compareTo(potentialI) > 0)
                error.total = outRange.max / I;
            else if (outRange.compareTo(potentialI) < 0)
                error.total = outRange.min / I;
        }

        this.output = (P * error.current) + (I * error.total) + (D * error.rate);

        if (outRange.compareTo(output) > 0)
            this.output = outRange.max;
        else if (outRange.compareTo(output) < 0)
            this.output = outRange.min;

        lastUpdateTime = System.currentTimeMillis() / 1000;
    }

    public void setPID(double p, double i, double d) {
        P = p;
        I = i;
        D = d;
    }
    /** @return P, the proportional constant. */
    public double getP() {
        return P;
    }
    /** @return I, the integral constant. */
    public double getI() {
        return I;
    }
    /** @return D, the derivative constant. */
    public double getD() {
        return D;
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
    public double getTarget() {
        return target;
    }
    public double getOutput() {
        return output;
    }

    public void setTarget(double t) { this.target = t; }

    /** @return the time difference from the last update. */
    public double dTime() {
        return (System.currentTimeMillis() / 1000) - lastUpdateTime;
    }
}
