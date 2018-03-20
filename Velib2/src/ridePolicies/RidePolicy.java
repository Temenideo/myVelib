package ridePolicies;

import myVelib.GPScoord;
import myVelib.Reseau;
import myVelib.Station;

public interface RidePolicy {
	
	public Station computeStart(Reseau reseau,GPScoord start,GPScoord end,String typeBike) throws NoStartStationAvailibleException;
	public Station computeEnd(Reseau reseau,GPScoord start,GPScoord end,String typeBike) throws NoEndStationAvailibleExecption;
}
