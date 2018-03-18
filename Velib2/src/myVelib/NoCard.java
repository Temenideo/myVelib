package myVelib;

import java.util.concurrent.TimeUnit;

import myVelib.Card.Card;

public class NoCard extends Card{
	public double costMH1=1;
	public double costMH2=1;
	public double costEH1=2;
	public double costEH2=2;
	
	
	
	@Override
	public int getCharge(Location loc, User user) {
		if (loc.getArrival().getTypeStation().equals("Plus")) {
			this.setTimeCredit(this.getTimeCredit()+5);
		}
		long duration = Card.getDuration(loc.getTimeStart(), loc.getTimeEnd(), TimeUnit.MINUTES);
		long hours = duration/60;
		int cost = 0;
		if(loc.getBike().getTypeBike().equalsIgnoreCase("Mechanical")) {
			cost+=costMH1+costMH2*hours;
			}
			
		if(loc.getBike().getTypeBike().equalsIgnoreCase("Electrical")) {
			cost+=costEH1+costEH2*hours;
			}		
		return cost;
	}
}
