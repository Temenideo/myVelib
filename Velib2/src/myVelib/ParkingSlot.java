package myVelib;

import java.util.ArrayList;
/**
 * Une classe permettant de cr�er les objets de m�me nom
 * @author xavier
 *
 */
public class ParkingSlot {
	protected static Long compteur=(long) 0;
	protected Long slotID;
	protected String state;
	protected Bicycle bicycle;
	protected ArrayList<TimeState> history;
	private Station station;
	/**
	 * Ce contructeur peux �tre amener � renvoyer une erreur dans le cas o� le type d'�tat n'a pas �t� �crit correctement ou n'existe pas
	 * @param bicycle
	 * @param State
	 * @throws BadParkingSlotCreationException
	 */
	public ParkingSlot(Bicycle bicycle, String State, Station station) throws BadParkingSlotCreationException {
		super();
		if (State=="Occupied" || State=="Free" ||State=="Broken"){
		compteur=compteur+1;
		slotID=compteur;
		this.bicycle = bicycle;
		this.state= State;
		this.station=station;
		this.history = new ArrayList<TimeState>();}
		else{
			throw new BadParkingSlotCreationException(State);}
	}
	
	public String getState() {
		return state;
	}
	/**
	 * Le changement d'�tat est aussi contr�l� afin seul les �tats possibles soit mis
	 * @param state
	 * @throws BadParkingSlotCreationException 
	 */
	public void setState(String state) throws BadParkingSlotCreationException {
		if (state=="Occupied" || state=="Free" ||state=="Broken"){
			this.state = state;
			station.calcul();
			}
		else{
			throw new BadParkingSlotCreationException(state);}
	}
	
	public Bicycle getBike() throws BadParkingSlotCreationException {
		if (state=="Occupied") {
			this.setState("Free");
				Bicycle bike = this.getBicycle();
				this.setBicycle(null);
				System.out.println(bike.typeBike+" bike "+bike.bikeID+"has been retrieved from parking slot"+this.slotID);
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
			System.out.println(bike.typeBike+" bike "+bike.bikeID+"has been parked in parking slot"+this.slotID);
			return true;
		}
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
