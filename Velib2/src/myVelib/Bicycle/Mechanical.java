package myVelib.Bicycle;

import myVelib.BadBicycleCreationException;

public class Mechanical extends Bicycle {
	public static String typeBike="Mechanical";
	public static double speed=15;

	public Mechanical() throws BadBicycleCreationException {
		super(Mechanical.typeBike);
	}


}
