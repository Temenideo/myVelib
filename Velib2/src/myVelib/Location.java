package myVelib;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import myVelib.Card.Card;
import myVelib.Bicycle.Bicycle;
import ridePolicies.RidePolicy;

public class Location implements Observer{
	private Date timeStart;
	private Date timeEnd;
	private Station departure;
	private Station arrival;
	private Bicycle bike;
	private GPScoord start;
	private GPScoord end;
	private boolean hasStarted;
	private User user;
	private RidePolicy ridePolicy;
	
	public Location(User user, GPScoord start, GPScoord end) {
		this.user=user;
		this.start=start;
		this.end=end;
		this.hasStarted=false;
		this.user.setLocation(this);
		Reseau.getInstance().addLocation(this);
	}
	
	public Location(User user, Station departure) {
		this.user=user;
		this.departure=departure;
		this.hasStarted=true;
		this.timeStart=Calendar.getInstance().getTime();
		this.user.setLocation(this);
		Reseau.getInstance().addLocation(this);
	}
	
	/**
	 * This method tries to retrieve a bike from the departure station. 
	 * It goes through all of the station's parking slot until it has found one that is holding a bike.
	 * If a bike is found, the location starts, hence the start time of the location is defined and a bike is linked to this location.
	 * If no bike is found, then the method gives an error message and finds another departure station fitting with the user's settings.
	 */
	
	public void takeBike(Station departure) throws BadParkingSlotCreationException{
		while(bike==null) {
			for(ParkingSlot pS : departure.getParkingSlotList()) {
				bike=pS.getBicycle();
			}
			System.out.println("No bike is available in this station");
			break;
		}
		if(bike!=null) {
			this.departure=departure;
			this.timeStart=Calendar.getInstance().getTime();
			this.hasStarted=true;
			this.user.setRideNumber(user.getRideNumber()+1);
		}
		else {
			this.computeStart();
		}
	}
	
	/**
	 * This method tries to store its bike in the arrival station. 
	 * It goes through all of the station's parking slot until it has found one that isn't holding a bike or free.
	 * If such a parking slot is found, the location ends, hence the end time of the location is defined, the bike is unlinked to the location
	 * and the cost of the location is computed.
	 * If no free parking slot is found, this method outputs an error message and finds another arrival station fitting with the user's settings.
	 */
	
	public void returnBike(Station arrival) throws BadParkingSlotCreationException {
		boolean stored = false;
		while(stored==false) {
			for(ParkingSlot pS : arrival.getParkingSlotList()){
				stored = pS.storeBike(this.bike);			
			}
			System.out.println("No parking slot is available in this station");
			break;
		}
		if(stored==true) {
			this.arrival=arrival;
			this.timeEnd=Calendar.getInstance().getTime();
			int duration = (int)Card.getDuration(timeStart, timeEnd, TimeUnit.MINUTES);
			int charge = this.user.getCard().getCharge(this, user);
			this.user.setTotalTime(user.getTotalTime()+(int)duration);
			this.user.setTotalCharges(user.getTotalCharges()+charge);
			this.bike=null;
			System.out.println("Bike location charged "+charge+"€");
			this.user.setLocation(null);
			
		}
		else {
			this.computeEnd();
		}
				
	}
	
	public void computeStart() {
		double dist=-1;
		Station startStation = null;
		for (Station stat : Reseau.getInstance().getStationList()) {
			if(stat.getState().equals("On service")) {
				if(stat.availableBikeE()||stat.availableBikeM()) {
					if (dist<0 || dist>this.start.getDistance(stat.getPosition())) {
						dist=this.start.getDistance(stat.getPosition());
						startStation=stat;
					}
				}
			}
		}
		if(startStation!=null) { 
			this.departure=startStation;
		}
		else
			System.out.println("No station fitting your criteria is availabale for departure, please try again later or change your ride settings");
	}
	
	public void computeEnd() {
		double dist=-1;
		Station endStation = null;
		for (Station stat : Reseau.getInstance().getStationList()) {
			if(stat.getState().equals("On service")) {
				if(stat.availableParkingSlot()) {
					if (dist<0 || dist>this.end.getDistance(stat.getPosition())) {
						dist=this.end.getDistance(stat.getPosition());
						endStation=stat;					
					}
				}
			}
		}
		if(endStation!=null) { 
			endStation.registerEndRide(this);
			this.arrival=endStation;
		}
		else
			System.out.println("No station fitting your criteria is availabale for arrival, please try again later or change your ride settings");
	}
	
	@Override
	public void updateArrival(Station arrival) {
		System.out.println("The destination station isn't available anymore.");
		System.out.println("Please proceed to this new station");	
		this.computeEnd();
	}
	

	public Date getTimeStart() {
		return timeStart;
	}
	
	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}
	
	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

	public Station getArrival() {
		return arrival;
	}

	public void setArrival(Station arrival) {
		this.arrival = arrival;
	}
	
	public Bicycle getBike() {
		return bike;
	}
	
	public void setBike(Bicycle bike) {
		this.bike = bike;
	}
	
	public Station getDeparture() {
		return departure;
	}

	public void setDeparture(Station departure) {
		this.departure = departure;
	}

	public GPScoord getEnd() {
		return end;
	}

	public void setEnd(GPScoord end) {
		this.end = end;
	}

	public boolean isHasStarted() {
		return hasStarted;
	}

	public void setHasStarted(boolean hasStarted) {
		this.hasStarted = hasStarted;
	}

	public void setStart(GPScoord start) {
		this.start = start;
	}

	public GPScoord getStart() {
		return start;
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public RidePolicy getRidePolicy() {
		return ridePolicy;
	}

	public void setRidePolicy(RidePolicy ridePolicy) {
		this.ridePolicy = ridePolicy;
	}


}
