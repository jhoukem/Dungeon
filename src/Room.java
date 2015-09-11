import java.util.HashMap;


public class Room {

	int numero;

	boolean needKey;
	boolean hasEastSide,hasWestSide, hasNorthSide, hasSouthSide = false;

	boolean isExit = false;
	boolean isEntrance = false;
	boolean isTrap = false;
	Key key = null;
	
	HashMap<Direction, Room> neighbors;


	public Room(int n, boolean b){
		numero = n;
		needKey = b;
		neighbors = new HashMap<Direction, Room>();
	}


	public Room getNextRoom(Direction dir){
		Room r = null;
		if(neighbors.containsKey(dir))
			r = neighbors.get(dir);
		return r;
	}




}
