package myVelib.Bicycle;

import myVelib.BadBicycleCreationException;

public class BikeFactory {
	public Bicycle createBicycle (String bikeType) throws BadBicycleCreationException {
		if(bikeType==null) {
			return null;
		}
		if (bikeType.equalsIgnoreCase("Mechanical")) {
			return new MBike();
		}
		else if(bikeType.equalsIgnoreCase("Electrical")) {
			return new EBike();
		}
		return null;
	}

}
