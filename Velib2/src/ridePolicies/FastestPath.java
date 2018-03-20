package ridePolicies;

import myVelib.GPScoord;
import myVelib.Reseau;
import myVelib.Station;

public class FastestPath implements RidePolicy{

	@Override
	public Station computeStart(Reseau reseau, GPScoord start, GPScoord end, String typeBike)
			throws NoStartStationAvailibleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Station computeEnd(Reseau reseau, GPScoord start, GPScoord end, String typeBike)
			throws NoEndStationAvailibleExecption {
		// TODO Auto-generated method stub
		return null;
	}

}
