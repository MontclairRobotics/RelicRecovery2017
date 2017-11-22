package org.firstinspires.ftc.teamcode.Auto.StateMachine;

/**
 * Created by Montclair Robotics on 11/22/2017.
 */

public class MultiState extends State{

    State[] states;
    int finishedState;

    public MultiState(int finishedState, State ... states){
        this.states = states;
        this.finishedState = finishedState;
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
            done = states[finishedState].isDone();
        }
        return done;
    }
}
