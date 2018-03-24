package ridePolicies;

import myVelib.GPScoord;
import myVelib.Reseau;
import myVelib.Station;

public class AvoidPlus implements RidePolicy{
	@Override
	public Station computeStart(GPScoord start,GPScoord end,String typeBike) throws NoStartStationAvailibleException {
		double dist=-1;
		Station startStation = null;
		Reseau reseau = Reseau.getInstance();
			// on parcours la liste des stations en regardant qu'elle respecte les crit�res
			for (Station stat : reseau.getStationList()) {
				if(stat.getState().equals("On service") && stat.availableBike(typeBike)) {
						// on regarde si elle est plus proche
						if (dist<0 || dist>start.getDistance(stat.getPosition())) {
							dist=start.getDistance(stat.getPosition());
							startStation=stat;
						}
					}
				}
		// si la variable startSation n'est pas null c'est qu'au moins une station valide les crit�res
		if(startStation!=null) { 
			return(startStation);
		}
		// si ce n'est pas le cas on renvoie une erreur
		else
			throw new NoStartStationAvailibleException();
	}
	@Override
	public Station computeEnd(GPScoord start,GPScoord end,String typeBike) throws NoEndStationAvailibleExecption {
		double dist=-1;
		Station endStation = null;
		Reseau reseau = Reseau.getInstance();
		for (Station stat : reseau.getStandardStationList()) {
			if(stat.getState().equals("On service") && stat.availableParkingSlot()) {
					if (dist<0 || dist>end.getDistance(stat.getPosition())) {
						dist=end.getDistance(stat.getPosition());
						endStation=stat;
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
