package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;

/**
 * Created by Hymowitz on 11/9/2017.
 */

/*
    Inertial Measurement Unit Maths Library
    Copyright (C) 2013-2014  Samuel Cowen
	www.camelsoftware.com
    Bug fixes and cleanups by Gé Vissers (gvissers@gmail.com)
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

public class RRQuaternion {
    public double w, x, y, z;

    public RRQuaternion(double w, double x, double y, double z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public RRQuaternion(Quaternion q) {
        set(q);
    }

    public void set(Quaternion q) {
        this.w = q.w;
        this.x = q.x;
        this.y = q.y;
        this.z = q.z;
    }

    public double magnitude() {
        return Math.sqrt(w*w + x*x + y*y + z*z);
    }
    public RRQuaternion conjugate() {
        return new RRQuaternion(-w, -x, -y, -z);
    }

    public RRVector rotateVector(RRVector v) {
        RRVector q = new RRVector(-x, -y, -z);
        RRVector t = q.cross(v).scale(2);
        return v.add(t.scale(w)).add(q.cross(t));
    }

    /** @return the rotational angle about the x-axis (in degrees). */
    public double getX() {
        RRVector v = rotateVector(RRVector.J);
        return Math.atan2(v.z, v.y) * (180/Math.PI);
    }

    /** @return the rotational angle about the y-axis (in degrees). */
    public double getY() {
        RRVector v = rotateVector(RRVector.I);
        return Math.atan2(v.z, v.x) * (180/Math.PI);
    }

    /** @return the rotational angle about the z-axis (in degrees). */
    public double getZ() {
        RRVector v = rotateVector(RRVector.J);
        return Math.atan2(v.x, v.y) * (180/Math.PI);
    }
}

/*
    // Returns euler angles that represent the quaternion.  Angles are
    // returned in rotation order and right-handed about the specified
    // axes:
    //
    //   v[0] is applied 1st about z (ie, roll)
    //   v[1] is applied 2nd about y (ie, pitch)
    //   v[2] is applied 3rd about x (ie, yaw)
    //
    // Note that this means result.x() is not a rotation about x;
    // similarly for result.z().
    //
    Vector<3> toEuler() const
    {
        Vector<3> ret;
        double sqw = _w*_w;
        double sqx = _x*_x;
        double sqy = _y*_y;
        double sqz = _z*_z;

        ret.x() = atan2(2.0*(_x*_y+_z*_w),(sqx-sqy-sqz+sqw));
        ret.y() = asin(-2.0*(_x*_z-_y*_w)/(sqx+sqy+sqz+sqw));
        ret.z() = atan2(2.0*(_y*_z+_x*_w),(-sqx-sqy+sqz+sqw));

        return ret;
    }


    Vector<3> toAngularVelocity(double dt) const
    {
        Vector<3> ret;
        Quaternion one(1.0, 0.0, 0.0, 0.0);
        Quaternion delta = one - *this;
        Quaternion r = (delta/dt);
        r = r * 2;
        r = r * one;

        ret.x() = r.x();
        ret.y() = r.y();
        ret.z() = r.z();
        return ret;
    }

    Vector<3> rotateVector(const Vector<2>& v) const
    {
        return rotateVector(Vector<3>(v.x(), v.y()));
    }

    Vector<3> rotateVector(const Vector<3>& v) const
    {
        Vector<3> qv(_x, _y, _z);
        Vector<3> t = qv.cross(v) * 2.0;
        return v + t*_w + qv.cross(t);
    }


    Quaternion operator*(const Quaternion& q) const
    {
        return Quaternion(
                _w*q._w - _x*q._x - _y*q._y - _z*q._z,
                _w*q._x + _x*q._w + _y*q._z - _z*q._y,
                _w*q._y - _x*q._z + _y*q._w + _z*q._x,
                _w*q._z + _x*q._y - _y*q._x + _z*q._w
        );
    }

    Quaternion operator+(const Quaternion& q) const
    {
        return Quaternion(_w + q._w, _x + q._x, _y + q._y, _z + q._z);
    }

    Quaternion operator-(const Quaternion& q) const
    {
        return Quaternion(_w - q._w, _x - q._x, _y - q._y, _z - q._z);
    }

    Quaternion operator/(double s) const
    {
        return Quaternion(_w / s, _x / s, _y / s, _z / s);
    }

    Quaternion operator*(double s) const
    {
        return scale(s);
    }

    Quaternion scale(double s) const
    {
        return Quaternion(_w * s, _x * s, _y * s, _z * s);
    }
*/