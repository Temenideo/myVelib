package myVelib;
/**Une classe erreur dans le cas o� l'utilisateur n'a pas tap� un bon type lors de la cr�ation d'une station
 * 
 */
public class BadTypeStationCreationException extends Exception {

	private static final long serialVersionUID = 1L;
	/**
	 * Cr�e une ereur sans donn�e d'entr�e
	 */
	public BadTypeStationCreationException() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * Cr�e une erreur et renvoi un message expliquant le probl�me
	 * @param message c'est la param�tre que l'utilisateur � rentrer et qui ne corresponds pas un � un �tat
	 */
	public BadTypeStationCreationException(String message) {
		super("Le type :"+message+"n'existe pas, veuillez inscrire soit standard soit plus");
		// TODO Auto-generated constructor stub
	}

}
