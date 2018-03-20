package myVelib.Bicycle;

import myVelib.BadBicycleCreationException;

public class BikeFactory {
	public Bicycle createBicycle (String bikeType) throws BadBicycleCreationException {
		if(bikeType==null) {
			return null;
		}
		if (bikeType.equalsIgnoreCase("Mechanical")) {
			return new Mechanical();
		}
		else if(bikeType.equalsIgnoreCase("Electrical")) {
			return new Electrical();
		}
		return null;
	}

}
