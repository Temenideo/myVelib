package ridePolicies;

import myVelib.GPScoord;
import myVelib.Reseau;
import myVelib.Station;

public class ShortestPath implements RidePolicy{

	@Override
	public Station computeStart(GPScoord start, GPScoord end, String typeBike)
			throws NoStartStationAvailibleException {
		double dist=-1;
		Station startStation = null;
		Reseau reseau = Reseau.getInstance();
		// on parcours la liste des stations en regardant qu'elle respecte les critères
		for (Station stat : reseau.getStationList()) {
			for (Station stat1: reseau.getStationList()){
				if(stat.getState().equals("On service") && stat1.getState().equals("On service") && stat.availableBike(typeBike)) {
					// on regarde si elle est plus proche
					if (dist<0 || dist>(start.getDistance(stat.getPosition())+stat.getPosition().getDistance(stat1.getPosition())+stat1.getPosition().getDistance(end))) {
						dist=start.getDistance(stat.getPosition())+stat.getPosition().getDistance(stat1.getPosition())+stat1.getPosition().getDistance(end);
						startStation=stat;
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
	public Station computeEnd(GPScoord start, GPScoord end, String typeBike)
			throws NoEndStationAvailibleExecption {
		double dist=-1;
		Station endStation = null;
		Reseau reseau = Reseau.getInstance();
		// on parcours la liste des stations en regardant qu'elle respecte les critères
		for (Station stat : reseau.getStationList()) {
			for (Station stat1: reseau.getStationList()){
				if(stat.getState().equals("On service") && stat1.getState().equals("On service") && stat.availableBike(typeBike)) {
					// on regarde si elle est plus proche
					if (dist<0 || dist>(start.getDistance(stat.getPosition())+stat.getPosition().getDistance(stat1.getPosition())+stat1.getPosition().getDistance(end))) {
						dist=start.getDistance(stat.getPosition())+stat.getPosition().getDistance(stat1.getPosition())+stat1.getPosition().getDistance(end);
						endStation=stat;
					}
				}
			}
		}
		// si la variable startSation n'est pas null c'est qu'au moins une station valide les critères
		if(endStation!=null) { 
			return(endStation);
		}
		// si ce n'est pas le cas on renvoie une erreur
		else
			throw new NoEndStationAvailibleExecption();
	}
}
