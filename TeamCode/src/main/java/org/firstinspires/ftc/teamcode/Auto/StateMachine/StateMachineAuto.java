package org.firstinspires.ftc.teamcode.Auto.StateMachine;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Components.DriveTrain;
import org.firstinspires.ftc.teamcode.Components.Hardware;
import org.firstinspires.ftc.teamcode.Components.JewelArm;
import org.firstinspires.ftc.teamcode.Debug;
import org.firstinspires.ftc.teamcode.Gyro;


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
    public StateMachine auto; // The actual auto mode that should be instantiated in init()
    public static ElapsedTime timer;

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
     * The setup method needs to be run at the beginning of every auto mode
     * This ensures that all of the OpMode Specific variables are available in all of the states
     * By creating instances of all of the variables, for example, to access telemetry in a state
     * you can do `Debug.getInstance().log()`, And it will log using the telemetry.
     * This method should be edited to fit the robots needs
     */
    public void setup(){
        DriveTrain dt = new DriveTrain(hardwareMap, new Gyro(hardwareMap)); // set up a new drivetrain
        JewelArm ja = new JewelArm(hardwareMap); // set up a new jewel arm
        Debug d = new Debug(telemetry); // create a new debug with the telemetry
        Hardware h = new Hardware(hardwareMap); // Set up a hardware object
        timer = new ElapsedTime(); // Create a new timer, this timer should be accessed globally
        timer.reset(); // reset the timer
    }


    /**
     * Start() sets running to true and runs the start() method of the state machine
     * @see StateMachine
     */
    @Override
    public void start(){
        auto.start(); // run the start method of the state machine
        running = true; // the state machine has started running
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
            auto.run(); // Run the state machine, which will in turn run the states
        }
        if(auto.isDone()){ // check if the state machine has finished (Last state achieved)
            running = false; // stop the state machine
            auto.stop(); // Finally stop the state machine
        }
    }
}