package myVelib.ridePolicies;

public class NoEndStationAvailibleExecption extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 0;
	public NoEndStationAvailibleExecption() {
		System.out.println("No station fitting your criteria is availabale for the end, please try again later or change your ride settings");
	}

}
