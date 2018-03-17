package ridePolicies;

import myVelib.GPScoord;
import myVelib.Reseau;
import myVelib.Station;

public class AvoidPlus implements RidePolicy{
	@Override
	public Station computeStart(Reseau reseau,GPScoord start,GPScoord end,String typeBike) throws NoStartStationAvailibleException {
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
	public Station computeEnd(Reseau reseau,GPScoord start,GPScoord end,String typeBike) throws NoEndStationAvailibleExecption {
		double dist=-1;
		Station endStation = null;
		for (Station stat : reseau.getStationList()) {
			if(stat.getState().equals("On service") && !stat.getTypeStation().equals("Plus")) {
				if(stat.availableParkingSlot()) {
					//voir si c'est bien start.get....
					if (dist<0 || dist>start.getDistance(stat.getPosition())) {
						dist=start.getDistance(stat.getPosition());
						endStation=stat;
					}
				}
			}
		}
		if(endStation!=null) { 
			return(endStation);
		}
		else
			throw new NoEndStationAvailibleExecption();
	}
}
