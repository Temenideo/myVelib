package myVelib;

public class Ride implements Observer {
	private GPScoord start;
	private GPScoord end;
	
	public GPScoord getStart() {
		return start;
	}
	public void setStart(GPScoord start) {
		this.start = start;
	}
	public GPScoord getEnd() {
		return end;
	}
	public void setEnd(GPScoord end) {
		this.end = end;
	}
	
	@Override
	public void updateArrival(Station arrival) {
		System.out.println("The destination station isn't available anymore.");
		System.out.println("Please proceed to this new station");
	}
	
	
}
