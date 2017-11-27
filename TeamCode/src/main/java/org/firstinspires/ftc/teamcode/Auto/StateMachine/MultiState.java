package org.firstinspires.ftc.teamcode.Auto.StateMachine;

/**
 * Created by Montclair Robotics on 11/22/2017.
 */


/**
 * The multiState class allows the user to run multiple states at once. For example, this can be useful
 * by lowering an arm while moving forward etc.
 * The user can decide what condition(s) need to be met for the state to be finished.
 * By passing in a negative number into finished state, MultiState will make sure all states are finished
 * If you only want one specific state to be finished, pass in the number of the state (NOTE: the states are indexed from 1 not 0)
 * To specify what state determines the next state, you can pass in the state number (NOTE: Again, indexed from 1)
 */
public class MultiState extends State{

    State[] states;
    int finishedState;
    Integer next;

    public MultiState(int finishedState, Integer next, State ... states){
        this.states = states;
        this.finishedState = finishedState;
        this.next = next;
    }

    public MultiState(int finishedState, State ... states){
        this(finishedState, null, states);
    }

    @Override
    public void start() {
        for(State state : states){
            state.start();
        }
    }

    @Override
    public void run() {
        for(State state : states){
            state.start();
        }
    }

    @Override
    public void stop() {
        for(State state : states){
            state.start();
        }
    }

    @Override
    public boolean isDone() {
        boolean done;
        if(finishedState < 0){
            done = true;
            for(State state : states){
                if(!state.isDone()){
                    done = false;
                }
            }
        }else{
            done = states[finishedState - 1].isDone();
        }
        return done;
    }

    @Override
    public int getNextState(int currentState){
        if(next != null && next - 1 < states.length){
            return states[next - 1].getNextState(currentState);
        }else{
            return currentState + 1;
        }
    }
}
