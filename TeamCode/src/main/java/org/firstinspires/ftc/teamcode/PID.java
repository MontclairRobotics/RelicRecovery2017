package org.firstinspires.ftc.teamcode;

/**
 * Created by Joshua Rapoport on 11/14/17.
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
        public boolean contains(double x) {
            return x >= min && x <= max;
        }
        public int compareTo(double x) {
            if (contains(x))
                return 0;
            else if (x < min)
                return -1;
            else
                return 1;
        }
    }

    public class Error {
        double current, total;
        public Error() {
            reset();
        }
        public void updateCurrent() {
            if (Math.abs(target - input) <= 180) {
                current = target - input;
            } else if (target > input) {
                current = 360 - Math.abs(target) - Math.abs(input);
            } else {
                current = Math.abs(target) - Math.abs(input) - 360;
            }
        }
        public void updateTotal() {
            total += current * dTime();
        }
        public void reset() { current = total = 0; }
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

        update();
    }

    public double getOutput() {
        return output;
    }
    private double setOutput(double newInput) {
        error.updateCurrent();
        double dInput = newInput - input;

        double diff = inRange.distance();
        if (diff != 0) {
            error.current = ((error.current - inRange.min) % diff + diff) % diff + inRange.min;
            dInput = ((dInput - inRange.min) % diff + diff) % diff + inRange.min;
        }

        error.updateTotal();

        if (I != 0) {
            double potentialI = (error.current + error.total) * I;
            if (outRange.compareTo(potentialI) > 0)
                error.total = outRange.max / I;
            else if (outRange.compareTo(potentialI) < 0)
                error.total = outRange.min / I;
            else
                error.total += error.current;
        }

        double out = (P * error.current * dTime()) + (I * error.total) + (D * -dInput / dTime());

        if (outRange.compareTo(out) > 0)
            out = outRange.max;
        else if (outRange.compareTo(out) < 0)
            out = outRange.min;

        return out;
    }

    /** @return a reference to a different PID object with identical properties */
    public PID copy() {
        PID c = new PID(P, I, D);
        c.setInputRange(inRange.min, inRange.max);
        c.setOutputRange(outRange.min, outRange.max);
        c.setTarget(target);

        return c;
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

    /** Use this method to get a new output value. */
    public void setInput(double i) {
        this.output = setOutput(i);
        this.input = i;
    }

    public double getTarget() {
        return target;
    }
    public void setTarget(double t) { this.target = target; }

    /** @return the time difference from the last update. */
    public double dTime() {
        return System.currentTimeMillis() - lastUpdateTime;
    }

    public void update() {
        lastUpdateTime = System.currentTimeMillis();
    }
}
