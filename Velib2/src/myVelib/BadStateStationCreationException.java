package myVelib;

public class BadStateStationCreationException extends Exception {

	/**Une classe erreur dans le cas o� l'utilisateur n'a pas tap� un bon type lors de la cr�ation d'une station
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BadStateStationCreationException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BadStateStationCreationException(String message) {
		super("Le type :"+message+"n'existe pas, veuillez inscrire soit on service soit offline");
		// TODO Auto-generated constructor stub
	}

}
