
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
 *
 * @see #run()
 */

public class StateMachine extends State {

    State[] states;
    Telemetry telemetry;
    String name;
    int state;
    boolean stateStarted = false;
    int finalState;
    boolean done = false;


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

        states[state].run();

        if(currentState.isDone()){
            currentState.stop();
            if(currentState.getNextState(state) == finalState){
                done = true;
                return;
            }
            if(currentState.getNextState(state) < states.length) {
                state = currentState.getNextState(state);
            }
            stateStarted = false;
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