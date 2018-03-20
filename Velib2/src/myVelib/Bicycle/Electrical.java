package myVelib.Bicycle;

import myVelib.BadBicycleCreationException;

public class Electrical extends Bicycle{
	public static String typeBike="Electrical";
	public static double speed=20;

	public Electrical() throws BadBicycleCreationException {
		super(Electrical.typeBike);
		
	}


}
