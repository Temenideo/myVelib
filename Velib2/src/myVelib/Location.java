package myVelib;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import myVelib.Card.Card;
import myVelib.ridePolicies.NoEndStationAvailableException;
import myVelib.ridePolicies.NoStartStationAvailableException;
import myVelib.ridePolicies.RidePolicy;
import myVelib.Bicycle.Bicycle;
/**
 * Cette classe represente la location d'un vélo par un utilisateur
 * Chaque location ou intention de location par une planification entrainera la création d'un objet location
 * @author xavier
 *
 */
public class Location implements Observer{
	private Date timeStart;
	private Date timeEnd;
	private Station departure;
	private Station arrival;
	private Bicycle bike;
	private GPScoord start;
	private GPScoord end;
	private boolean hasStarted;
	private boolean hasEnded;
	private User user;
	private RidePolicy ridePolicy;
/**
 * Permet la création d'une location lorsque l'utilisateur veux programmer son parcourt
 * @param user	utilisateur qui veux faire une location
 * @param start	coordonnées GPS de départ
 * @param end	coordonnées GPS d'arrivée
 * @param ridePolicy	type de parcours voulu
 * @param typeBike	type de vélo voulu
 * @throws NoEndStationAvailableException erreur dans le cas où il n'y  pas de station d'arrivée possible
 * @throws NoStartStationAvailableException erreur dans le cas où il n'y pas de station de départ voulu
 */
	public Location(User user, GPScoord start, GPScoord end, RidePolicy ridePolicy, String typeBike) throws NoEndStationAvailableException, NoStartStationAvailableException {
		this.user=user;
		this.start=start;
		this.end=end;
		this.hasStarted=false;
		this.hasEnded=false;
		this.user.setLocation(this);
		this.ridePolicy=ridePolicy;
		this.departure=ridePolicy.computeStart(start, end, typeBike);
		this.arrival=ridePolicy.computeEnd(start, end, typeBike);
		Reseau.getInstance().addLocation(this);
	}
/**
 * Permet la création d'une location lorsque l'utilisateur se trouve à une station donnée
 * @param user utilisateur qui veux faire une location
 * @param departure station de départ
 */
	public Location(User user, Station departure) {
		this.user=user;
		this.departure=departure;
		this.hasStarted=true;
		this.timeStart=Calendar.getInstance().getTime();
		this.user.setLocation(this);
		this.hasEnded=false;
		Reseau.getInstance().addLocation(this);
	}
	/**
	 * Permet la création d'une location lorsque l'utilisateur veux programmer son parcourt
	 * @param user utilisateur qui veux faire une location
	 * @param departure station de départ
	 * @param typeBike type de vélo voulu
	 * @param policy type de parcours voulu
	 * @throws BadParkingSlotCreationException
	 */
	public Location(User user, Station departure, String typeBike, RidePolicy policy) throws BadParkingSlotCreationException {
		this.user=user;
		this.departure=departure;
		this.hasStarted=true;
		this.timeStart=Calendar.getInstance().getTime();
		this.user.setLocation(this);
		this.hasEnded=false;
		this.ridePolicy=policy;
		this.takeBike(departure, typeBike);
		Reseau.getInstance().addLocation(this);
	}


//Pour l'instant aucune vérification n'est faite pour vérifier que l'user n'a pas déjà une location en cours
	/**
	 * This method tries to retrieve a bike from the departure station. 
	 * It goes through all of the station's parking slot until it has found one that is holding a bike.
	 * If a bike is found, the location starts, hence the start time of the location is defined and a bike is linked to this location.
	 * If no bike is found, then the method gives an error message and finds another departure station fitting with the user's settings.
	 * @param departure station de départ
	 * @param type type de vélo voulu
	 * @throws BadParkingSlotCreationException
	 */
	public void takeBike(Station departure,String type) throws BadParkingSlotCreationException{
		if(this.user.getLoc()==null) {
			while(bike==null) {
				for(ParkingSlot pS : departure.getParkingSlotList()) {
					if (pS.getBicycle().getTypeBike()==type){
						bike=pS.retrieveBike();
						break;}
				}
				break;
			}
			if(bike!=null) {
				this.departure=departure;
				this.timeStart=Calendar.getInstance().getTime();
				this.hasStarted=true;
				this.user.setRideNumber(user.getRideNumber()+1);
				this.user.setLocation(this);
			}
			else {
				System.out.println("No bike is available in this station");
			}
		}
		else {
			System.out.println("User is currently renting another bike. Please return it before getting a new one");
		}
		
	}

	@Override
	public String toString() {
		return "Location [timeStart=" + timeStart + ", timeEnd=" + timeEnd + ", departure=" + departure + ", arrival="
				+ arrival + ", bike=" + bike + ", start=" + start + ", end=" + end + ", hasStarted=" + hasStarted
				+ ", user=" + user + ", ridePolicy=" + ridePolicy + "]";
	}

	/**
	 *  This method tries to store its bike in the arrival station. 
	 * It goes through all of the station's parking slot until it has found one that isn't holding a bike or free.
	 * If such a parking slot is found, the location ends, hence the end time of the location is defined, the bike is unlinked to the location
	 * and the cost of the location is computed.
	 * If no free parking slot is found, this method outputs an error message and finds another arrival station fitting with the user's settings.
	 * @param arrival station où l'on souhaite deposer le velo
	 * @throws BadParkingSlotCreationException
	 */
	public void returnBike(Station arrival) throws BadParkingSlotCreationException {
		boolean stored = false;
		while(stored==false) {
			for(ParkingSlot pS : arrival.getParkingSlotList()){
				stored = pS.storeBike(this.bike);		
				break;
			}
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
			this.hasEnded=false;
			System.out.println("Bike location charged "+charge+"€");
			this.user.setLocation(null);
			
			

		}
		else {
			System.out.println("No parking slot is available in this station");
		}

	}
/**
 * Permet de savoir si la location a fini ou non
 * @return retroune True si la location a fini
 */
	public boolean isHasEnded() {
		return hasEnded;
	}
/**
 * Permet de changer le paramètre hasEnded, lorsque la location a fini
 * @param hasEnded état de la location
 */
	public void setHasEnded(boolean hasEnded) {
		this.hasEnded = hasEnded;
	}


	@Override
	/**
	 * Actualise la station d'arriver lorsque la station intialement prévu n'est plus disponible
	 */
	public void updateArrival(Station arrival) throws NoEndStationAvailableException {
		System.out.println("The destination station isn't available anymore.");
		System.out.println("Please proceed to this new station");
		this.arrival.removeRide(this);
		this.arrival=this.ridePolicy.computeEnd(start, end, bike.getTypeBike());
		this.arrival.registerEndRide(this);
	}

/**
 * Fonction qui donne l'heure de départ de la location
 * @return Heure de départ sous le format Date
 */
	public Date getTimeStart() {
		return timeStart;
	}
/**
 * Fonction qui permet de donner l'heure de départ
 * @param timeStart heure de départ
 */
	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}
/**
 * Fonction qui donne l'heure d'arrivée de la location
 * @return Heure d'arrivée sous le format Date
 */
	public Date getTimeEnd() {
		return timeEnd;
	}
	/**
	 * Fonction qui permet de donner l'heure d'arriver
	 * @param timeStart heure d'arriver
	 */
	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}
/**
 * Fonction qui donne la station d'arrivée de la location
 * @return fonction d'arrivée
 */
	public Station getArrival() {
		return arrival;
	}
/**
 * Fonction qui permet de donner la station d'arrivée
 * @param arrival nouvelle station d'arrivée
 */
	public void setArrival(Station arrival) {
		this.arrival.removeRide(this);
		this.arrival = arrival;
		this.arrival.registerEndRide(this);
	}
/**
 * Fonction qui permet de voir le velo de la location
 * @return le velo correspondant à cette location
 */
	public Bicycle getBike() {
		return bike;
	}
/**
 * Fonction qui permet de lier un velo à la location
 * @param bike le vélo qui est associé à cette location
 */
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
