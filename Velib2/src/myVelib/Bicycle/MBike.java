package myVelib.Bicycle;

import myVelib.BadBicycleCreationException;

public class MBike extends Bicycle {
	public static String typeBike="Mechanical";
	public static double speed=15;

	public MBike() throws BadBicycleCreationException {
		super(MBike.typeBike);
	}


}
