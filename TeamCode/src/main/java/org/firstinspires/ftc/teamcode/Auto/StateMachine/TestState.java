package org.firstinspires.ftc.teamcode.Auto.StateMachine;

/**
 * Created by thegb on 11/15/2017.
 */

public class TestState extends State {

    /**
     * By default, states run in a linear fashion running one after another, if the user desires to
     * have the state go somewhere other then the next linear state, they can define one in the
     * Constructor
     *
     * @param nextState indicates what state the state machine will go to after the current state is finished
     */
    public TestState(int nextState) {
        super(nextState);
    }

    public TestState() {
    }

    @Override
    public void start() {

    }

    @Override
    public void run() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isDone() {
        return false;
    }
}
