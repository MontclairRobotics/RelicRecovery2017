package org.firstinspires.ftc.teamcode;

/**
 * Created by Hymowitz on 11/9/2017.
 */

public class RRVector {
    public static final RRVector ZERO = new RRVector(0, 0, 0);
    public static final RRVector I = new RRVector(1, 0, 0);
    public static final RRVector J = new RRVector(0, 1, 0);
    public static final RRVector K = new RRVector(0, 0, 1);

    public double x, y, z;

    public RRVector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public RRVector add(RRVector v)
    {
        return new RRVector(x+v.x,y+v.y,z+v.z);
    }

    public RRVector scale(double s)
    {
        return new RRVector(x*s,y*s,z*s);
    }
    public RRVector opposite() {
        return new RRVector(x, y, z).scale(-1);
    }

    public double dot(RRVector v) {
        return this.x*v.x + this.y*v.y + this.z*v.z;
    }
    public RRVector cross(RRVector v) {
        double detX = y*v.z - z*v.y, detY = z*v.x - x*v.z, detZ = x*v.y - y*v.x;
        return new RRVector(detX, detY, detZ);
    }
}
