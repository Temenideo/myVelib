package myVelib;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import myVelib.Bicycle.Electrical;
import myVelib.Bicycle.Mechanical;

public class StationTest {

	@Test
	public void testCalcul() throws BadStateStationCreationException, BadTypeStationCreationException, BadParkingSlotCreationException {
		Reseau res = Reseau.getInstance();
		Station stat=new Station(new ArrayList<ParkingSlot>(), "Plus", "on service", new GPScoord(1,1), null);
		for (int i=0;i<10;i++){
			new ParkingSlot(null, "Free", stat);
		}
		for (int i=0;i<10;i++){
			new ParkingSlot(new Electrical(), "Occupied", stat);
		}
		for (int i=0;i<10;i++){
			new ParkingSlot(new Mechanical(), "Occupied", stat);
		}
		for (int i=0;i<10;i++){
			new ParkingSlot(new Mechanical(), "Broken", stat);
		}
		fail("Not yet implemented");
	}

	@Test
	public void testAvailableBike() {
		fail("Not yet implemented");
	}

	@Test
	public void testAvailableParkingSlot() {
		fail("Not yet implemented");
	}

	@Test
	public void testNumberOfRentsOperation() {
		fail("Not yet implemented");
	}

	@Test
	public void testNumberOfReturnOperation() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRateOfOccupation() {
		fail("Not yet implemented");
	}

}
