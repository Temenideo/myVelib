package myVelib;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import myVelib.Bicycle.Bicycle;
/**
 * Une classe permettant de créer les objets de même nom
 * @author xavier
 *
 */
public class ParkingSlot {
	private static Long compteur=(long) 0;
	private Long slotID;
	private String state;
	private Bicycle bicycle;
	private ArrayList<TimeState> history;
	private Station station;
	/**
	 * Ce contructeur peux être amener à renvoyer une erreur dans le cas où le type d'état n'a pas été écrit correctement ou n'existe pas
	 * @param bicycle
	 * @param State
	 * @throws BadParkingSlotCreationException
	 */
	public ParkingSlot(Bicycle bicycle, String State, Station station) throws BadParkingSlotCreationException {
		super();
		if (State=="Occupied" ||State=="Broken"){
			compteur=compteur+1;
			slotID=compteur;
			this.bicycle = bicycle; // Possible de créer un parking slot avec l'état free et un vélo à l'intérieur
			this.state= State;
			this.station=station;
			this.history = new ArrayList<TimeState>();
			history.add(new TimeState(true,Calendar.getInstance().getTime()));
			station.addParkingSlot(this);
		}
		else if ( State=="Free" && bicycle == null) {
			compteur=compteur+1;
			slotID=compteur;
			this.state= State;
			this.station=station;
			this.history = new ArrayList<TimeState>();
			history.add(new TimeState(false,Calendar.getInstance().getTime()));	
			station.addParkingSlot(this);
		}
		else{
			throw new BadParkingSlotCreationException(State);}
	}

	public String getState() {
		return state;
	}
	/**
	 * Le changement d'état est aussi contrôlé afin que seuls les états possibles soit mis
	 * @param state
	 * @throws BadParkingSlotCreationException 
	 */
	public void setState(String state) throws BadParkingSlotCreationException {
		if (state=="Occupied" || state=="Free" ||state=="Broken"){
			this.state = state;
			station.calcul();
			history.get(history.size()-1).setEnd(Calendar.getInstance().getTime());
			if (state=="Free"){
				history.add(new TimeState(false,Calendar.getInstance().getTime()));
			}
			else{
				history.add(new TimeState(true,Calendar.getInstance().getTime()));
			}
			}
		else{
			throw new BadParkingSlotCreationException(state);}
	}
	
	public Bicycle retrieveBike() throws BadParkingSlotCreationException {
		if (state=="Occupied") {
			this.setState("Free");
				Bicycle bike = this.getBicycle();
				this.setBicycle(null);
				System.out.println(bike.getTypeBike()+" bike "+bike.getBikeID()+" has been retrieved from parking slot "+this.slotID);
				return bike;		
			}
		else {
			return null;
			}
	}
	
	public boolean storeBike(Bicycle bike) throws BadParkingSlotCreationException {
		if (state!="Free") {
			return false;
		}
		else {
			this.setState("Occupied");
			this.bicycle=bike;
			System.out.println(bike.getTypeBike()+" bike "+bike.getBikeID()+" has been parked in parking slot "+this.slotID);
			return true;
		}
	}
	/**
	 * 	
	 * @param start
	 * @param end
	 * @return
	 */
	public long getTimeOccupied(Date start,Date end){
		long timeOccupied=0;
		int longueur=history.size();
		for(int i=0;i<longueur-1;i++){
			System.out.println(history.get(i));
			if (history.get(i).getStart().after(start) && history.get(i).getEnd().before(end) && !history.get(i).isOccupied()){
				long diffInMillies = history.get(i).getEnd().getTime()-history.get(i).getStart().getTime();
				timeOccupied=timeOccupied+TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
			}
		}
		if (history.get(longueur-1).getStart().after(start) && !history.get(longueur-1).isOccupied() && Calendar.getInstance().getTime().after(end)){
			long diffInMillies = Calendar.getInstance().getTime().getTime()-history.get(longueur-1).getStart().getTime();
			timeOccupied=timeOccupied+TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
		}
		return(timeOccupied);
	}
	public Long getSlotID() {
		return slotID;
	}
	public Bicycle getBicycle() {
		return bicycle;
	}
	
	public ArrayList<TimeState> getHistory() {
		return history;
	}
	public void setBicycle(Bicycle bicycle) {
		this.bicycle = bicycle;
	}
	
	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	@Override
	public String toString() {
		return "ParkingSlot " + slotID + ", state:" + state + ", " + bicycle + "";
	}
	
	
}