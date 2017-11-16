package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Auto.StateMachine.State;
import org.firstinspires.ftc.teamcode.Auto.StateMachine.StateMachine;


/**
 * @author MHS Robotics
 *
 * This class is to be extended by all auto modes that plan to use a state machine
 * The purpose of this class is to control when all of the state machine functions run
 *
 * When creating your auto modes you should override the init mehtod and instantiate the auto object
 * @see #init
 * The user is welcome to override any other methods but needs to take care of when the state machine
 * methods are run
 * @see State
 * By default this class will take care of the running the state machine
 */

@Disabled
public class StateMachineAuto extends OpMode {

    boolean running; // Keeps track of when the auto mode is running (True after started and before finished)
    StateMachine auto; // The actual auto mode that should be instantiated in init()

    /**
     * User defined init method
     * <p>
     * This method will be called once when the INIT button is pressed.
     *
     * init() is where the State machine should be instantiated
     */
    @Override
    public void init() {

    }


    /**
     * Start() sets running to true and runs the start() method of the state machine
     * @see StateMachine
     */
    @Override
    public void start(){
        auto.start();
        running = true;
    }

    /**
     * User defined loop method
     * <p>
     * This method will be called repeatedly in a loop while this op mode is running
     *
     *
     * Loop() is in charge of running the actual states in the state machine and also determining when
     * the auto mode is done.
     *
     * If you override this make sure to include auto.run() somewhere in the code to make sure the staes
     * actually run
     */
    @Override
    public void loop() {
        if(running){
            auto.run();
        }
        if(auto.isDone()){
            running = false;
            auto.stop();
        }
    }
}
