package myVelib;

import java.util.ArrayList;

/**
 * La classe station créera les objets de même nom
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
	private int freeSlots;
	private int freeBikes;
	protected ArrayList<Location> incomingRideList;
	/**
	 * Ce contructeur peux être amené à renvoyer une erreur dans le cas où le type de station ou le type d'état n'a pas été écrit correctement ou n'existe pas
	 * @param parkingSlotList
	 * @param typeStation doit être une chaine de caractère du type Standard ou Plus
	 * @param state doit être une chaine de caractère du type on service ou offline
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
				this.incomingRideList=new ArrayList<Location>();
				Reseau.getInstance().addStation(this);
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
	 * Le changement d'état est aussi contrôlé afin que aucun état autre que Plus ou Standard ne soit mis
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
	 * Le changement d'état est aussi contrôlé afin que aucun état autre que on service ou offline ne soit mis
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
	
	public int getFreeSlots() {
		return freeSlots;
	}
	
	public int getFreeBikes() {
		return freeBikes;
	}
	
	/**
	 * Method to recompute the number of available parking slots and bikes
	 */
	public void calcul() {
		int free = 0;
		int bikes = 0;
		for (ParkingSlot pS : parkingSlotList) {
			if(pS.getState().equals("Free")) {
				free++;
			}
			if(pS.getState().equals("Occupied")) {
				bikes++;
			}
		}
		this.freeBikes=bikes;
		this.freeSlots=free;
	}
	
	/**
	 * Method to check if the station currently holds an electrical bike in one of its parking slots.
	 */	
	public boolean availableBikeM() {
		if(freeBikes==0) {
			return false;
		}
		for (ParkingSlot pS : parkingSlotList) {
			if (pS.getState().equals("Occupied")) {
				if (pS.getBicycle().getTypeBike().equals("Mechanical")) {
				return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Method to check if the station currently holds an electrical bike in one of its parking slots.
	 * @return
	 */
	public boolean availableBikeE() {
		if(freeBikes==0) {
			return false;
		}
		for (ParkingSlot pS : parkingSlotList) {
			if (pS.getState().equals("Occupied")) {
				if (pS.getBicycle().getTypeBike().equals("Electrical")) {
				return true;
				}
			}
		}
		return false;
	}
		
	/**
	 * Method to check if the station currently has a free parking slot.
	 * @return
	 */
	
	public boolean availableParkingSlot() {
			if (this.freeSlots>0) {
				return true;
		}
		return false;
	}
	
	public void addParkingSlot(ParkingSlot pS) {
		parkingSlotList.add(pS);
		pS.setStation(this);
		this.calcul();
	}
	
	public void removeParkingSlot(ParkingSlot pS) {
		parkingSlotList.remove(pS);
		this.calcul();
	}
		
	@Override
	public void registerEndRide(Location loc) {
		this.incomingRideList.add(loc);
	}
	
	@Override
	public void removeRide(Location loc) {
		this.incomingRideList.remove(loc);
	}

	@Override
	public void notifyEndRide() {
		if(this.freeSlots==0) {
			for(Location loc:incomingRideList) {
				loc.updateArrival(this);
			}
		}
		
	}
	@Override
	public String toString() {
		return "Station"+ stationID+" "+name+" ("+position+") Parking Slots:" + parkingSlotList;
	}
}
