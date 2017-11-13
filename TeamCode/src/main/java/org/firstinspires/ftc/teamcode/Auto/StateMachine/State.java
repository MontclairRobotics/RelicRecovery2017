package org.firstinspires.ftc.teamcode.Auto.StateMachine;

/**
 * @author MHS Robotics
 *
 * The State class is designed to be passed in to a state machine.
 *
 */

public abstract class State {

    Integer nextState;


    /**
     * By default, states run in a linear fashion running one after another, if the user desires to
     * have the state go somewhere other then the next linear state, they can define one in the
     * Constructor
     */
    public State(int nextState){
        this.nextState = nextState;
    }

    public State(){}

    /**
    * First thing called when the state is run
    */
    public abstract void start();

    /*
    * This method is run once every loop while the state is running
    */
    public abstract void run();

    /*
    * Run one time after the state is done
    */
    public abstract void stop();

    /*
    * Boolean to check weather the state is done
    */
    public abstract boolean isDone();

    public int getNextState(int currentState){
        if(nextState != null){
            return nextState;
        }
        return currentState + 1;
    }

}
