package org.montclairrobotics.sprocket.auto.states;

import org.montclairrobotics.sprocket.actions.State;
import org.montclairrobotics.sprocket.actions.StateMachine;
import org.montclairrobotics.sprocket.drive.steps.GyroCorrection;
import org.montclairrobotics.sprocket.geometry.Degrees;

public class DriveEncoderGyro extends StateMachine{
	
	public DriveEncoderGyro(Number distance,Number speed,Number angle,Comparable<Boolean> relative,GyroCorrection gyroCorrection)
	{
		super(makeStates(distance,speed,angle,relative,gyroCorrection));
	}
	
	public static State[] makeStates(Number distance,Number speed,Number angle,Comparable<Boolean> relative,GyroCorrection gyroCorrection)
	{
		final GyroCorrection gcFinal=gyroCorrection;
		final Number angleFinal=angle;
		final Comparable<Boolean> relativeFinal=relative;
		State[] r={
			new TurnGyro(angle,relative),
			new State()
			{

				@Override
				public void start() {
					// TODO DefaultAuto-generated method stub
					gcFinal.setTargetAngle(new Degrees(angleFinal.doubleValue()),relativeFinal.compareTo(true)==0);
				}

				@Override
				public void enabled() {
					// TODO DefaultAuto-generated method stub
					gcFinal.use();
				}

				@Override
				public void stop() {
					// TODO DefaultAuto-generated method stub
					
				}

				@Override
				public void disabled() {
					// TODO DefaultAuto-generated method stub
					
				}

				@Override
				public boolean isDone() {
					// TODO DefaultAuto-generated method stub
					return false;
				}
			}
		};
		return r;
	}
}
