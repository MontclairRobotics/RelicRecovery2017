
package org.firstinspires.ftc.teamcode.Auto.StateMachine;

/**
 * @author MHS Robotics
 *
<<<<<<< HEAD
 * The State class is designed to be passed in to a state machine
 *
 * To create a state, create a new class and have it implement this interface
 * Implement all of the methods you wish to use for that state
 *
 * Every state has methods that determines what to be run when the state is running
 * For each states you can define a start, stop, run, and isDone method to control how the state is run
 *
 */

public abstract class State {

    private Integer nextState;



    /**
     * By default, states run in a linear fashion running one after another, if the user desires to
     * have the state go somewhere other then the next linear state, they can define one in the
     * Constructor
     */
    public State(int nextState){
        this.nextState = nextState;
    }

    public  State(){}

    /**
     * First thing called when the state is run,
     * This is where any Motor powers, initialization or any other setup should be run
     * This is called ONCE when the state starts
     */
    public abstract void start();

    /**
     * This method is run once every loop while the state is running
     * This is where the actual implementation of the state should be run
     * Any logic, motor positions, motor powers and anything else to be run should be run here
     * This is called EVERY LOOP until the auto mode is done
     */
    public abstract void run();

    /**
     * Run one time after the state is done
     * This is where any braking, encoder resets, motor resets and any cleanup should be done
     * This is called ONCE at the end of the state
     */
    public abstract void stop();

    /**
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
