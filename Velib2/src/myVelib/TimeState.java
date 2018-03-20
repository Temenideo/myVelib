package myVelib;

import java.util.Date;
/**
 * Cette classe permet de cr�er un historique des �tats du ParkingSlot, elle se compose de deux attributs un �tat sous la forme d'un booleen
 * et une date de d�part.
 * @author xavier
 *
 */
public class TimeState {
	private boolean occupied;
	private Date start;
	public TimeState(boolean occupied, Date start) {
		super();
		this.occupied = occupied;
		this.start = start;
	}
	public boolean isOccupied() {
		return occupied;
	}
	public Date getStart() {
		return start;
	}
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	
	
}
