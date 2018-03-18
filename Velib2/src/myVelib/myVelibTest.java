package myVelib;

import java.util.ArrayList;

import myVelib.Bicycle.Bicycle;

public class myVelibTest {
	public static void main(String[] args) throws BadStateStationCreationException, BadTypeStationCreationException, BadParkingSlotCreationException, BadBicycleCreationException {
		Reseau res = new Reseau();
		for(int i=1; i<=3;i++) {
			res.addStation(new Station(new ArrayList<ParkingSlot>(), "Standard", "on service", new GPScoord(i,i), null));
			for (int j=1;j<=10;j++) {
				res.stationList.get(i-1).addParkingSlot(new ParkingSlot(new Bicycle("Mechanical"), "Occupied",res.getStationList().get(i-1)));
			}
			res.stationList.get(i-1).addParkingSlot(new ParkingSlot(new Bicycle("Electrical"), "Occupied",res.getStationList().get(i-1)));
		}
		System.out.println(res);
	}
}
