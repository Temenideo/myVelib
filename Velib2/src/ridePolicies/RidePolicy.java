package ridePolicies;

import myVelib.GPScoord;
import myVelib.Reseau;
import myVelib.Station;

public interface RidePolicy {
	
	public Station computeStart(GPScoord start,GPScoord end,String typeBike) throws NoStartStationAvailibleException, NoEndStationAvailibleExecption;
	public Station computeEnd(GPScoord start,GPScoord end,String typeBike) throws NoEndStationAvailibleExecption;
}
