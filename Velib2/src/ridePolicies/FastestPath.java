package ridePolicies;

import myVelib.GPScoord;
import myVelib.Reseau;
import myVelib.Station;

public class FastestPath implements RidePolicy{

	@Override
	public Station computeStart(GPScoord start, GPScoord end, String typeBike)
			throws NoStartStationAvailibleException, NoEndStationAvailibleExecption {
		Station arrival = computeEnd(start,end,typeBike);
		Reseau reseau = Reseau.getInstance();
		return null;
	}

	@Override
	public Station computeEnd(GPScoord start,GPScoord end,String typeBike)
			throws NoEndStationAvailibleExecption {
		Reseau reseau = Reseau.getInstance();
		return null;
	}

}
