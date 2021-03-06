package org.montclairrobotics.sprocket.drive;

import org.montclairrobotics.sprocket.actions.Action;
import org.montclairrobotics.sprocket.core.IMotor;
import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Input;


/**
 * ControlledMotor is a wrapper class for Motors which allows a motor to be
 * directly controlled either by an Input{@literal <}Double{@literal >} such as JoystickYAxis,
 * or by using a pair of Input{@literal <}Boolean{@literal >} such as Button for running the motor
 * forwards and backwards depending on which button is pressed. One likely use 
 * case for a ControlledMotor would be if you just want to quickly debug a motor
 * using Joystick inputs. If you're using button pairs, you could also use
 * ControlledMotor to run a shooter forward and backward using the button pair.
 */
public class ControlledMotor implements Updatable,Action {
	
	private IMotor output;
	private Input<Double> input;
	private boolean enabled=true;
	
	/**
	 * Used to construct a ControlledMotor based off of an Input of a double such as
	 * a Joystick axis.
	 * @param motor the motor which should be controlled
	 * @param input the input to read from
	 */
	public ControlledMotor(IMotor motor, Input<Double> input) {
		this.output=motor;
		this.input = input;
		Updater.add(this, Priority.OUTPUT);
	}
	
	/**
	 * Constructs a ControlledMotor based off of two boolean Inputs and a given speed.
	 * If forwardInput is true, speed * 1 is sent to the motor. If reverseInput is true,
	 * speed * -1 is sent to the motor.
	 * @param motor the motor which should be controlled
	 * @param forwardInput this input is true if the motor should go forwards. Can be null.
	 * @param reverseInput this input is true if the motor should go in reverse. Can be null.
	 * @param speed the speed at which to run the motor (whether forwards or backwards)
	 */
	public ControlledMotor(IMotor motor, Input<Boolean> forwardInput, Input<Boolean> reverseInput, double speed) {
		this.output=motor;
		this.input = new ButtonPairInput(forwardInput, reverseInput, speed);
		Updater.add(this, Priority.OUTPUT);
	}
	
	/**
	 * Constructs a ControlledMotor based off of two boolean Inputs and a given speed.
	 * If forwardInput is true,a speed of 1 is sent to the motor. If reverseInput 
	 * is true, a speed of -1 is sent to the motor.
	 * @param motor the motor which should be controlled
	 * @param forwardInput this input is true if the motor should go forwards. Can be null.
	 * @param reverseInput this input is true if the motor should go in reverse. Can be null.
	 */
	public ControlledMotor(IMotor motor, Input<Boolean> forwardInput, Input<Boolean> reverseInput) {
		this(motor, forwardInput, reverseInput, 1.0);
	}
	
	@Override
	public void update() {
		if(enabled)
			output.set(input.get());
	}

	@Override
	public void start() {
		enabled=true;
	}

	@Override
	public void stop() {
		enabled=false;
	}

	@Override
	public void enabled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disabled() {
		// TODO Auto-generated method stub
		
	}

}

class ButtonPairInput implements Input<Double> {
	
	private Input<Boolean> forward;
	private Input<Boolean> reverse;
	private double speed;
	
	ButtonPairInput(Input<Boolean> forward, Input<Boolean> reverse, double speed) {
		this.forward = forward;
		this.reverse = reverse;
		this.speed = speed;
	}
	
	@Override
	public Double get() {
		if(forward != null && forward.get()) {
			return speed;
		} else if(reverse != null && reverse.get()) {
			return -speed;
		} else {
			return 0.0;
		}
	}
	
}