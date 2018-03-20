package myVelib;

import java.util.ArrayList;

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
	 * Le changement d'état est aussi contrôlé afin que seuls les états possibles soit mis
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
				System.out.println(bike.getTypeBike()+" bike "+bike.getBikeID()+"has been retrieved from parking slot"+this.slotID);
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
			System.out.println(bike.getTypeBike()+" bike "+bike.getBikeID()+"has been parked in parking slot"+this.slotID);
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
