package ridePolicies;

import myVelib.GPScoord;
import myVelib.Reseau;
import myVelib.Station;

public class AvoidPlus implements RidePolicy{
	@Override
	public Station computeStart(Reseau reseau,GPScoord start,GPScoord end,String typeBike) {
	double dist=-1;
	Station startStation = null;
	for (Station stat : reseau.getStationList()) {
		if(stat.getState().equals("On service") && stat.getTypeStation()!="Plus") {
			if(stat.availableBikeE()||stat.availableBikeM()) {
				if (dist<0 || dist>this.start.getDistance(stat.getPosition())) {
					dist=this.start.getDistance(stat.getPosition());
					startStation=stat;
				}
			}
		}
	}
	if(startStation!=null) { 
		return(startStation);
	}
	else
		System.out.println("No station fitting your criteria is availabale for departure, please try again later or change your ride settings");
}
}
