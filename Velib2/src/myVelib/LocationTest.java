package myVelib;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import myVelib.Bicycle.Electrical;
import myVelib.Bicycle.Mecanical;

public class LocationTest {

	@Test
	public void testTakeBike() throws BadStateStationCreationException, BadTypeStationCreationException, BadParkingSlotCreationException {
		User user=new User("Jean","Paul");
		Reseau res = new Reseau();
		for(int i=1; i<=3;i++) {
			res.addStation(new Station(new ArrayList<ParkingSlot>(), "Standard", "on service", new GPScoord(i,i), null));
			for (int j=1;j<=10;j++) {
				res.getStationList().get(i-1).addParkingSlot(new ParkingSlot(new Electrical(), "Occupied",res.getStationList().get(i-1)));
			}
			res.getStationList().get(i-1).addParkingSlot(new ParkingSlot(new Mecanical(), "Occupied",res.getStationList().get(i-1)));
		}
		Location loc1=new Location(user,res.getStationList().get(0));
		fail("Not yet implemented");
	}

	@Test
	public void testReturnBike() {
		fail("Not yet implemented");
	}

}
