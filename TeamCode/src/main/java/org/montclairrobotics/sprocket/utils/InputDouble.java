package org.montclairrobotics.sprocket.utils;

public abstract class InputDouble extends Number implements Input<Double>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7875067748687693370L;

	@Override
	public double doubleValue() {
		// TODO Auto-generated method stub
		return get();
	}

	@Override
	public float floatValue() {
		// TODO Auto-generated method stub
		return get().floatValue();
	}

	@Override
	public int intValue() {
		// TODO Auto-generated method stub
		return get().intValue();
	}

	@Override
	public long longValue() {
		// TODO Auto-generated method stub
		return get().longValue();
	}

}
