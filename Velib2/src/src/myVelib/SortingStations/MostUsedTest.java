package myVelib.SortingStations;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import myVelib.BadParkingSlotCreationException;
import myVelib.BadStateStationCreationException;
import myVelib.BadTypeStationCreationException;
import myVelib.GPScoord;
import myVelib.Location;
import myVelib.ParkingSlot;
import myVelib.Station;
import myVelib.User;
import myVelib.Bicycle.Mechanical;

public class MostUsedTest {

	@Test
	public void testSortStation() throws BadStateStationCreationException, BadTypeStationCreationException, BadParkingSlotCreationException {
		Station departure = new Station(new ArrayList<ParkingSlot>(), "Standard", "on service", new GPScoord(0,0), null);
		new ParkingSlot(new Mechanical(),"Occupied",departure);
		User user1 = new User("Jean","Paul");
		Location loc = new Location(user1,departure);
		loc.takeBike(departure,"Mechanical");
		loc.returnBike(departure);
		
	}

}
