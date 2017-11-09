package org.firstinspires.ftc.teamcode;

/**
 * Created by Hymowitz on 11/9/2017.
 */

public class OurVector3 {
    public static final OurVector3 forward=new OurVector3(0,1,0);
    public static final OurVector3 right=new OurVector3(1,0,0);
    public static final OurVector3 up=new OurVector3(0,0,1);

    public double x,y,z;
    public OurVector3(double x,double y,double z)
    {
        this.x=x;
        this.y=y;
        this.z=z;
    }
    public OurVector3 add(OurVector3 v)
    {
        return new OurVector3(x+v.x,y+v.y,z+v.z);
    }
    public OurVector3 cross(OurVector3 v)
    {
        return new OurVector3(y*v.z-z*v.y,z*v.x-x*v.z,x*v.y-y*v.x);
    }
    public OurVector3 scale(double s)
    {
        return new OurVector3(x*s,y*s,z*s);
    }
}
