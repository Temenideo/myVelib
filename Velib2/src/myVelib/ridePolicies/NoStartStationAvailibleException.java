package myVelib.ridePolicies;

public class NoStartStationAvailibleException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public NoStartStationAvailibleException(){
		System.out.println("No station fitting your criteria is availabale for departure, please try again later or change your ride settings");
	}
}
