package ridePolicies;

import myVelib.GPScoord;
import myVelib.Reseau;
import myVelib.Station;

public class AvoidPlus implements RidePolicy{
	@Override
	public Station computeStart(Reseau reseau,GPScoord start,GPScoord end,String typeBike) throws NoStartStationAvailibleException {
		double dist=-1;
		Station startStation = null;
		// On regarde le type de vélo voulu
		if (typeBike.equals("Electrical")){
			// on parcours la liste des stations en regardant qu'elle respecte les critères
			for (Station stat : reseau.getStationList()) {
				if(stat.getState().equals("On service")) {
					if(stat.availableBikeE()) {
						// on regarde si elle est plus proche
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
		// si la variable startSation n'est pas null c'est qu'au moins une station valide les critères
		if(startStation!=null) { 
			return(startStation);
		}
		// si ce n'est pas le cas on renvoie une erreur
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
					if (dist<0 || dist>end.getDistance(stat.getPosition())) {
						dist=end.getDistance(stat.getPosition());
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
