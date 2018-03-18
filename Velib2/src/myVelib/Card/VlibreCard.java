package myVelib.Card;

import java.util.concurrent.TimeUnit;

import myVelib.Location;
import myVelib.User;

public class VlibreCard extends Card{
	public double costMH1=0;
	public double costMH2=1;
	public double costEH1=1;
	public double costEH2=2;
	
	@Override
	public int getCharge(Location loc, User user) {
		if(loc.getArrival().getTypeStation().equals("Plus")) {
			this.setTimeCredit(getTimeCredit()+5);
			user.setEarnedCredits(user.getEarnedCredits()+5);
		}
		long duration = Card.getDuration(loc.getTimeStart(), loc.getTimeEnd(), TimeUnit.MINUTES);
		long hours = duration/60;
		long min = duration%60;
		int cost = 0;
		if(loc.getBike().getTypeBike().equalsIgnoreCase("Mechanical")) {
			if(hours<1) {
				cost+=costMH1;
			}
			else if (min<this.getTimeCredit()) {
				this.setTimeCredit(getTimeCredit()-(int)min);
				hours--;
				cost+=costMH1+costMH2*hours;
			}
			
		}
		if(loc.getBike().getTypeBike().equalsIgnoreCase("Electrical")) {
			if(hours<1) {
				cost+=costEH1;
			}
			else if (min<this.getTimeCredit()) {
				this.setTimeCredit(getTimeCredit()-(int)min);
				hours--;
				cost+=costEH1+costEH2*hours;
			}
		}
		
		return cost;
	}
}
