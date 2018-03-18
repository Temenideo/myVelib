package myVelib.Bicycle;

import myVelib.BadBicycleCreationException;

public abstract class Bicycle {
	private static Long compteur=(long) 100000;
	private long bikeID;
	protected String typeBike;
	public double speed;
	
	
	public Bicycle(String typeBike) throws BadBicycleCreationException {
		super();
		if (typeBike=="Mechanical"){
		compteur++;
		bikeID=compteur;
		this.typeBike = typeBike;
		}
		else{
			if(typeBike=="Electrical") {
				compteur++;
				bikeID=compteur;
				this.typeBike = typeBike;
			}
		else {
			throw new BadBicycleCreationException(typeBike);
			}
			
		}
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
	public double getSpeed() {
		return speed;		
	}
	
	
}
