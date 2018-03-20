package myVelib.Bicycle;

public class Bicycle {
	private static Long compteur=(long) 1;
	private long bikeID;
	protected String typeBike; // protected est necessaire ici pour que les sous-classes y est accès
	public Bicycle() {
		super();
		compteur++;
		bikeID=compteur;
			
	}
	public long getBikeID() {
		return bikeID;
	}
	public String getTypeBike() {
		return typeBike;
	}
	@Override
	public String toString() {
		return "Bicycle [bikeID=" + bikeID + ", typeBike=" + typeBike + "]";
	}
	
	
}
