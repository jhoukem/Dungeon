package model;

public class Torch {
	private int fire;

	public Torch(){
		fire = 10;
	}

	public void use(Room r){
		if(fire > 1){
			fire --;
			System.out.println("Possible directions : (Torch) ");
			String s = "";
			if(r.hasEastSide)
				s+= "East\n";
			if(r.hasNorthSide)
				s+= "North\n";
			if(r.hasSouthSide)
				s+= "South\n";
			if(r.hasWestSide)
				s+= "West\n";
			System.out.println(s);	
		}
		else
			System.out.println("Your torch is extinguished");
	}


}
