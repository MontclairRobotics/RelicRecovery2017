package org.montclairrobotics.sprocket.test;

import org.montclairrobotics.sprocket.auto.AutoMode;
import org.montclairrobotics.sprocket.auto.states.Delay;
import org.montclairrobotics.sprocket.core.Sprocket;
import org.montclairrobotics.sprocket.utils.Debug;

/**
 * Created by thegb on 11/8/2017.
 */

public class AutoTest extends TestRobot {
    public AutoTest() {
        super(Sprocket.MODE.AUTO, 10);
    }

    @Override
    public void setup() {
    }

    @Override
    public void enableMode(Sprocket.MODE mode) {

    }

    @Override
    public void teleopInit() {

    }

    @Override
    public void autoInit() {
        Debug.msg("starting","yay");
        sprocket.currentAction=new AutoMode("Plesse",new Delay(0.5));

    }

    @Override
    public void testInit() {

    }

    @Override
    public void disable() {

    }

    @Override
    public void update() {

    }

    @Override
    public void disabledUpdate() {

    }

    @Override
    public void debugs() {

    }

    public static void main(String args[])
    {
        new AutoTest();
    }
}
