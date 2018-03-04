package myVelib;

import java.util.ArrayList;

/**
 * La classe station cr�era les objets de m�me nom
 * @author xavier
 *
 */
public class Station implements Observable {
	protected ArrayList<ParkingSlot> parkingSlotList;
	protected String typeStation;
	protected String state;
	protected GPScoord position;
	protected static Long compteur=(long) 0;
	protected Long stationID;
	protected String name;
	protected ArrayList<Location> leavingRideList;
	protected ArrayList<Location> incomingRideList;
	/**
	 * Ce contructeur peux �tre amen� � renvoyer une erreur dans le cas o� le type de station ou le type d'�tat n'a pas �t� �crit correctement ou n'existe pas
	 * @param parkingSlotList
	 * @param typeStation doit �tre une chaine de caract�re du type Standard ou Plus
	 * @param state doit �tre une chaine de caract�re du type on service ou offline
	 * @param position
	 * @param name
	 * @throws BadStateStationCreationException,BadTypeStationCreationException
	 */
	public Station(ArrayList<ParkingSlot> parkingSlotList, String typeStation, String state, GPScoord position,
			String name) throws BadStateStationCreationException,BadTypeStationCreationException {
		super();
		if (typeStation=="Standard" || typeStation=="Plus"){
			if ( state=="on service" || state=="offline"){
				compteur=compteur+1;
				stationID=compteur;
				this.parkingSlotList = parkingSlotList;
				this.typeStation = typeStation;
				this.state = state;
				this.position = position;
				this.name = name;
				this.leavingRideList=new ArrayList<Location>();
				this.incomingRideList=new ArrayList<Location>();
			}
			else {
				throw new BadStateStationCreationException(state);
			}
		}
		else {
			throw new BadTypeStationCreationException(typeStation);
		}
		
		
	}
	public String getTypeStation() {
		return typeStation;
	}
	/**
	 * Le changement d'�tat est aussi contr�l� afin que aucun �tat autre que Plus ou Standard ne soit mis
	 * @param typeStation
	 * @throws BadTypeStationCreationException
	 */
	public void setTypeStation(String typeStation) throws BadTypeStationCreationException {
		if (typeStation=="Standard" || typeStation=="Plus"){
			this.typeStation = typeStation;}
		else{
			throw new BadTypeStationCreationException(typeStation);
		}
	}
	public ArrayList<ParkingSlot> getParkingSlotList() {
		return parkingSlotList;
	}
	public String getState() {
		return state;
	}
	/**
	 * Le changement d'�tat est aussi contr�l� afin que aucun �tat autre que on service ou offline ne soit mis
	 * @param state
	 * @throws BadStateStationCreationException
	 */
	public void setState(String state) throws BadStateStationCreationException {
		if ( state=="on service" || state=="offline"){
			this.state = state;}
		else{
			throw new BadStateStationCreationException(state);
		}
	}
	public GPScoord getPosition() {
		return position;
	}
	public Long getStationID() {
		return stationID;
	}
	public String getName() {
		return name;
	}
	
	/* 
	 * Method to check if the station currently holds an electrical bike in one of its parking slots.
	 */	
	public boolean availableBikeM() {
		for (ParkingSlot pS : parkingSlotList) {
			if (pS.state.equals("Occupied")) {
				if (pS.bicycle.typeBike.equals("Mechanical")) {
				return true;
				}
			}
		}
		return false;
	}
	
	/* 
	 * Method to check if the station currently holds an electrical bike in one of its parking slots.
	 */
	public boolean availableBikeE() {
		for (ParkingSlot pS : parkingSlotList) {
			if (pS.state.equals("Occupied")) {
				if (pS.bicycle.typeBike.equals("Electrical")) {
				return true;
				}
			}
		}
		return false;
	}
		
	/* 
	 * Method to check if the station currently has a free parking slot.
	 */
	public boolean availableParkingSlot() {
		for (ParkingSlot pS : parkingSlotList) {
			if (pS.state.equals("Free")) {
				return true;
			}
		}
		return false;
	}
	
	public void addParkingSlot(ParkingSlot pS) {
		parkingSlotList.add(pS);
	}
	
	public void removeParkingSlot(ParkingSlot pS) {
		parkingSlotList.remove(pS);
	}
	
	@Override
	public void registerStartRide(Location loc) {
		this.leavingRideList.add(loc);		
	}
	
	@Override
	public void registerEndRide(Location loc) {
		this.incomingRideList.add(loc);
	}
	
	@Override
	public void removeRide(Location loc) {
		this.leavingRideList.remove(loc);
		this.incomingRideList.remove(loc);
	}
	@Override
	public void notifyStartRide() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void notifyEndRide() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String toString() {
		return "Station"+ stationID+" "+name+" ("+position+") Parking Slots:" + parkingSlotList;
	}
	
	
	
}
