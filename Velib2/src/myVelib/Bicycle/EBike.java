package myVelib.Bicycle;

import myVelib.BadBicycleCreationException;

public class EBike extends Bicycle{
	public static String typeBike="Electrical";
	public static double speed=20;

	public EBike() throws BadBicycleCreationException {
		super(EBike.typeBike);
		
	}


}
