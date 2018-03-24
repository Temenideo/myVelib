package ridePolicies;

import myVelib.GPScoord;
import myVelib.Reseau;
import myVelib.Station;

public class PreferPlus implements RidePolicy{
	@Override
	/**
	 * Méthode fonctionnent pareil que c'est de AvoidPlus pour le départ
	 */
	public Station computeStart(Reseau reseau, GPScoord start, GPScoord end, String typeBike)
			throws NoStartStationAvailibleException {

		double dist=-1;
		Station startStation = null;
			for (Station stat : reseau.getStationList()) {
				if(stat.getState().equals("On service")) {
					if(stat.availableBike(typeBike)) {
						if (dist<0 || dist>start.getDistance(stat.getPosition())) {
							dist=start.getDistance(stat.getPosition());
							startStation=stat;
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
		double distPlus=-1;
		double distNoPlus=-1;
		Station endPlusStation = null;
		Station endNoPlusStation = null;
		// on cherche la station plus la plus proche respectant les critères
		for (Station stat : reseau.getStationList()) {
			if(stat.getState().equals("On service") && stat.getTypeStation().equals("Plus")) {
				if(stat.availableParkingSlot()) {
					if (distPlus<0 || distPlus>end.getDistance(stat.getPosition())) {
						distPlus=end.getDistance(stat.getPosition());
						endPlusStation=stat;
					}
				}
			}
		}
		// on cherche la station non plus la plus proche respectant les critères
		for (Station stat : reseau.getStationList()) {
			if(stat.getState().equals("On service") && !stat.getTypeStation().equals("Plus")) {
				if(stat.availableParkingSlot()) {
					if (distNoPlus<0 || distNoPlus>end.getDistance(stat.getPosition())) {
						distNoPlus=end.getDistance(stat.getPosition());
						endNoPlusStation=stat;
					}
				}
			}
		}
		// si on a trouvé une station plus et une non plus on compare les distances
		if (distNoPlus>0 && distPlus>0 ){
			if (distPlus<1.1*distNoPlus){
				return (endPlusStation);
			}
			else{
				return (endNoPlusStation);
			}

		}
		// si il y a que une station plus on la renvoie
		else if(distPlus>0){
			return (endPlusStation);
		}
		// si il y a que une station  non plus on la renvoie
		else if(distNoPlus>0){
			return (endNoPlusStation);
		}
		// sinon on renvoie une erreur
		else{
			throw new NoEndStationAvailibleExecption();
		}
	}
}
