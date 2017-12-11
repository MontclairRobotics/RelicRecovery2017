package org.firstinspires.ftc.teamcode;

/**
 * Created by Montclair Robotics on 11/14/17.
 * @author Joshua Rapoport
 * @version 12/11/17
 */

public class PID {
    public class Error {
        double last, current, rate, total;
        public Error () { reset(); }
        public void reset() { last = current = rate = total = 0; }
        public void updateWith(double i) {
            double d = in.diff();
            current = ((target - i - d/2) % d + d) % d + d/2;
            rate = (dt() > 0) ? (last - current) / dt() : 0;
            last = current;

            if (I != 0) {
                double potentialI = (total + last) * I;
                if (out.compareTo(potentialI) > 0)
                    total = out.max / I;
                else if (out.compareTo(potentialI) < 0)
                    total = out.min / I;
                else
                    total += current * dt();
            }
        }
    }

    public class Range {
        double min, max;
        public Range() { min = max = 0; }
        public Range(double i, double f) {
            this.min = i;
            this.max = f;
        }
        public int compareTo(double d) {
            if (d <= min)
                return -1;
            else if (d >= max)
                return +1;
            else
                return 0;
        }
        public double diff() {
            return max - min;
        }
    }

    double P,I,D;
    Range in;
    Range out;

    double target;
    long lastUpdateTime;

    Error error;

    public PID(double p,double i, double d) {
        this.P = p;
        this.I = i;
        this.D = d;

        this.in = new Range();
        this.out = new Range();

        this.target = 0.0;
        this.error = new Error();
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public PID setInRange (double i, double f) {
        in = new Range(i, f);
        return this;
    }
    public PID setOutRange(double i, double f) {
        out = new Range(i, f);
        return this;
    }

    public PID setTarget(double t) {
        this.target = t;
        return this;
    }

    public double get(double input) {
        error.updateWith(input);

        lastUpdateTime = System.currentTimeMillis();

        return (P * error.current) + (I * error.total) + (D * error.rate);
    }

    public double dt() {
        return System.currentTimeMillis() - lastUpdateTime;
    }

    @Override
    public String toString() {
        return "{" + P + ", " + I + ", " + D + "}";
    }

//    public static PID angleToPower(double p, double i, double d) {
//        return new PID(p, i, d).setInRange(-180, +180).setOutRange(-1.0, +1.0);
//    }
}
