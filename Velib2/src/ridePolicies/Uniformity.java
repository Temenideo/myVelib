package ridePolicies;

import myVelib.GPScoord;
import myVelib.Reseau;
import myVelib.Station;

public class Uniformity implements RidePolicy{
	public Station computeStart(Reseau reseau, GPScoord start, GPScoord end, String typeBike)
			throws NoStartStationAvailibleException {

		double dist=-1;
		Station startStation = null;
		int numberOfAvailibleBike=0;
		// ici on trouve la station la plus proche du lieu de départ
		if (typeBike.equals("Electrical")){
			for (Station stat : reseau.getStationList()) {
				if(stat.getState().equals("On service")) {
					if(stat.availableBikeE()) {
						if (dist<0 || dist>start.getDistance(stat.getPosition())) {
							dist=start.getDistance(stat.getPosition());
							startStation=stat;
							numberOfAvailibleBike=stat.NumberAvailableBikeE();
						}
					}
				}
			}
			// ici on compare si d'autres station aux alentours ont plus de vélo disponible
			for (Station stat : reseau.getStationList()) {
				if(stat.getState().equals("On service") && !stat.equals(startStation)) {
					if(stat.availableBikeE()) {
						if (numberOfAvailibleBike<stat.NumberAvailableBikeE() || 1.05*dist>start.getDistance(stat.getPosition())) {
							return(stat);
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
							numberOfAvailibleBike=stat.NumberAvailableBikeM();
						}
					}
				}
			}
			for (Station stat : reseau.getStationList()) {
				if(stat.getState().equals("On service") && !stat.equals(startStation)) {
					if(stat.availableBikeM()) {
						if (numberOfAvailibleBike<stat.NumberAvailableBikeM() || 1.05*dist>start.getDistance(stat.getPosition())) {
							return(stat);
						}
					}
				}
			}
		}
		// se cas se présente si la station la plus proche est aussi celle avec le plus de vélo
		if(startStation!=null) { 
			return(startStation);
		}
		else
			throw new NoStartStationAvailibleException();
	}
	public Station computeEnd(Reseau reseau,GPScoord start,GPScoord end,String typeBike) throws NoEndStationAvailibleExecption {
		double dist=-1;
		Station endStation = null;
		// ici on trouve la station la plus proche du lieu d'arriver
		for (Station stat : reseau.getStationList()) {
			if(stat.getState().equals("On service")) {
				if(stat.availableParkingSlot()) {
					if (dist<0 || dist>end.getDistance(stat.getPosition())) {
						dist=end.getDistance(stat.getPosition());
						endStation=stat;
					}
				}
			}
		}
		// ici on compare si d'autres station aux alentours ont plus de place disponible
		for (Station stat : reseau.getStationList()) {
			if(stat.getState().equals("On service") && !stat.equals(endStation)) {
				if(stat.availableBikeE()) {
					if (endStation.getFreeSlots()<stat.getFreeSlots() || 1.05*dist>start.getDistance(stat.getPosition())) {
						return(stat);
					}
				}
			}
		}
		// se cas se présente si la station la plus proche est aussi celle avec le plus de place
		if(endStation!=null) { 
			return(endStation);
		}
		else
			throw new NoEndStationAvailibleExecption();
	}
}
