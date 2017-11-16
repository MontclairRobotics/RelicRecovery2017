package org.montclairrobotics.sprocket.actions;

public class Toggle implements Action{

	private Togglable obj;
	
	public Toggle(Togglable obj)
	{
		this.obj=obj;
	}
	
	@Override
	public void start()
	{
		obj.enable();
	}
	
	@Override
	public void stop()
	{
		obj.disable();
	}

	@Override
	public void enabled() {
		// TODO DefaultAuto-generated method stub
		
	}

	@Override
	public void disabled() {
		// TODO DefaultAuto-generated method stub
		
	}
	
}
