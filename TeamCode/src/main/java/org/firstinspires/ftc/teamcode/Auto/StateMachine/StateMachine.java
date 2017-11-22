
package org.firstinspires.ftc.teamcode.Auto.StateMachine;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * @author MHS Robotics
 *
 * State machines are designed to be run in a State Machine Auto
 * @see StateMachineAuto
 *
 * StateMachine is a state itsef so State Machines can be run inside of other State Machines,
 * This allows the user to re-use code for actions that are run often in autonomous.
 * @see #run()
 *
 * The user should pass in the states that they want to run
 * The state machine will finish when it does not have a state to go to or if the final state is acheived
 * The Final state is defined by the user in the constructor
 */

public class StateMachine extends State {

    State[] states;
    Telemetry telemetry;
    String name;
    int state;
    boolean stateStarted = false;
    int finalState;
    boolean done = false;
    private double startTime;


    public StateMachine(String name, int finalstate,  Telemetry telemetry, State ... states){
        this.states     = states;
        this.telemetry  = telemetry;
        this.name       = name;
        this.finalState = finalstate;
    }

    @Override
    public void start() {
        telemetry.addData("Running", name);
    }

    @Override
    public void run() {

        State currentState = states[state];

        //Check If the state has started, if it hasn't run the 'start()' method
        if (!stateStarted) {
            currentState.start();
            stateStarted = true; // State has been started
        }

        states[state].run(); //run the state

        if(currentState.isDone()){ //check if the state has finished
            currentState.stop(); // Run the stop() method on the state
            if(currentState.getNextState(state) == finalState){ // Check if the state machine is done
                done = true; // set the state machine as done
                return; // return (exit) out of the run() method
            }
            if(currentState.getNextState(state) < states.length) { // make sure there is a next state to go to
                state = currentState.getNextState(state); // go to the next state
            }else{
                telemetry.addData("ERROR", "STATE MACHINE OUT OF BOUNDS"); // Give the user an error if there is no next state to go to
                done = true; // stop the state machine
                return; // exit the run method to ensure nothing else runs
            }
            stateStarted = false; // The next state has not started yet.
        }
    }

    @Override
    public void stop() {
        telemetry.addData("Status", name + ", Finished ");
    }

    @Override
    public boolean isDone() {
        return done;
    }
}