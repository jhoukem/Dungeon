package items;

import rooms.Room;

public class Torch {
	private static final int MAX_FIRE = 10;
	private int fire;

	public Torch(){

	}

	public Torch(int n){
		if(n < 11 && n > 0)
			fire = n;
		else
			fire = MAX_FIRE;
	}

	public void use(Room r){
		if(fire > 1){
//			fire --;
			System.out.println("Possible directions : (Torch = "+fire+") ");
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

	public void reload() {
		fire = MAX_FIRE;
	}


}
