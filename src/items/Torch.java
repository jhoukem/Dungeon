package items;

import java.util.Map.Entry;

import model.Direction;
import rooms.Room;


public class Torch {
	private static final int	MAX_FIRE	= 20;
	private int					fire;

	public Torch() {
		fire = MAX_FIRE;
	}

	public Torch(int n) {
		// fire value cannot be initialized at 0 or more than 21
		if ((n < 21) && (n > 0)) {
			fire = n;
		} else {
			fire = MAX_FIRE;
		}
	}

	/**
	 * 
	 * @param r actual room of the player
	 */
	public void use(Room r) {
		if (fire > 0) {
			fire--;
			System.out.println("Possible directions : (Torch = " + fire + ") ");
			String s = "";
			for(Entry<Direction, Room> entry : r.neighbors.entrySet()) {
				Direction dir = entry.getKey();
				s+= dir+"\n";
			}
			System.out.println(s);
		} else {
			System.out.println("Your torch is extinguished");
		}
	}

	public void extinguish() {
		fire = 0;
	}

	public void reload() {
		fire = MAX_FIRE;
	}

	public int getFire() {
		return fire;
	}

}
