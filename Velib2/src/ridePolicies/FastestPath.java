package ridePolicies;

import myVelib.GPScoord;
import myVelib.Reseau;
import myVelib.Station;

public class FastestPath implements RidePolicy{

	@Override
	public Station computeStart(Reseau reseau, GPScoord start, GPScoord end, String typeBike)
			throws NoStartStationAvailibleException {

		double dist=-1;
		Station startStation = null;
		if (typeBike.equals("Electrical")){
			for (Station stat : reseau.getStationList()) {
				if(stat.getState().equals("On service")) {
					if(stat.availableBikeE()) {
						if (dist<0 || dist>start.getDistance(stat.getPosition())) {
							dist=start.getDistance(stat.getPosition());
							startStation=stat;
						}
					}
				}
			}
		}
		if (typeBike.equals("Mecanical")){
			for (Station stat : reseau.getStationList()) {
				if(stat.getState().equals("On service")) {
					if(stat.availableBikeM()) {
						if (dist<0 || dist>start.getDistance(stat.getPosition())) {
							dist=start.getDistance(stat.getPosition());
							startStation=stat;
						}
					}
				}
			}
		}
		if(startStation!=null) { 
			return(startStation);
		}
		else
			throw new NoStartStationAvailibleException();
	}


	@Override
	public Station computeEnd(Reseau reseau, GPScoord start, GPScoord end, String typeBike)
			throws NoEndStationAvailibleExecption {
		double distplus=-1;
		double distnoplus=-1;
		Station endPlusStation = null;
		Station endNoPlusStation = null;
		for (Station stat : reseau.getStationList()) {
			if(stat.getState().equals("On service") && stat.getTypeStation().equals("Plus")) {
				if(stat.availableParkingSlot()) {
					//voir si c'est bien start.get....
					if (distplus<0 || distplus>start.getDistance(stat.getPosition())) {
						distplus=start.getDistance(stat.getPosition());
						endPlusStation=stat;
					}
				}
			}
		}
		for (Station stat : reseau.getStationList()) {
			if(stat.getState().equals("On service") && !stat.getTypeStation().equals("Plus")) {
				if(stat.availableParkingSlot()) {
					//voir si c'est bien start.get....
					if (distnoplus<0 || distnoplus>start.getDistance(stat.getPosition())) {
						distnoplus=start.getDistance(stat.getPosition());
						endNoPlusStation=stat;
					}
				}
			}
		}
		try {
			//on compare les deux distance et on revois la bonne station
			;
		}
		catch {
		// comprendre ce qu'il veux avec le finally et tout
			throw new NoEndStationAvailibleExecption();
	}

}
