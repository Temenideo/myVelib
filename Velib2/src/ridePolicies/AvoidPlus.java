package ridePolicies;

import myVelib.GPScoord;
import myVelib.Reseau;
import myVelib.Station;

public class AvoidPlus implements RidePolicy{
	@Override
	public Station computeStart(Reseau reseau,GPScoord start,GPScoord end,String typeBike) {
		double dist=-1;
		Station startStation = null;
		if (typeBike=="Electrical"){
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
		if (typeBike=="Mecanical"){
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
			//il faut aussi return une erreur à coder ensuite
			System.out.println("No station fitting your criteria is availabale for departure, please try again later or change your ride settings");
	}
	@Override
	public Station computeEnd(Reseau reseau,GPScoord start,GPScoord end,String typeBike) {
		double dist=-1;
		Station endStation = null;
		for (Station stat : reseau.getStationList()) {
			if(stat.getState().equals("On service") && stat.getTypeStation()!="Plus") {
				if(stat.availableParkingSlot()) {
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
			//il faut aussi return une erreur à coder ensuite
			System.out.println("No station fitting your criteria is availabale for departure, please try again later or change your ride settings");
	}
}
