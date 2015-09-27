package items;

import model.Direction;
import rooms.Room;


public class Torch {
	private static final int	MAX_FIRE	= 20;
	private int					fire;

	public Torch() {
		fire = MAX_FIRE;
	}

	public Torch(int n) {
		if ((n < 21) && (n > 0)) {
			fire = n;
		} else {
			fire = MAX_FIRE;
		}
	}

	public void use(Room r) {
		if (fire > 0) {
			fire--;
			System.out.println("Possible directions : (Torch = " + fire + ") ");
			String s = "";
			if (r.neighbors.containsKey(Direction.NORTH)) {
				s += "North\n";
			}
			if (r.neighbors.containsKey(Direction.EAST)) {
				s += "East\n";
			}
			if (r.neighbors.containsKey(Direction.SOUTH)) {
				s += "South\n";
			}
			if (r.neighbors.containsKey(Direction.WEST)) {
				s += "West\n";
			}
			System.out.println(s);
		} else {
			System.out.println("Your torch is extinguished");
		}
	}

	public void reload() {
		fire = MAX_FIRE;
	}

	public int getFire() {
		return fire;
	}

}
