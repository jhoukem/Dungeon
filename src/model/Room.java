package model;
import java.util.HashMap;


public class Room {

	final int numero;

	private boolean needKey = false;
	public boolean hasEastSide, hasWestSide, hasNorthSide, hasSouthSide = false;

	private boolean isExit = false;
	private boolean isEntrance = false;
	private boolean isTrap = false;
	private Key key = null;

	public HashMap<Direction, Room> neighbors;

	public void act(Player p){
		System.out.println("Room n°"+numero);	
		if(isExit())
			System.out.println("Congratulation ! You escaped from the dungeon");
		
		else if(isTrap()){// if the room is a trap after it has hit the player the trap is destroyed
			System.out.println("You fall in a trap");
			p.health --;
			System.out.println("Health :"+p.health);
			setTrap(false);
			if(p.health < 1)
				System.out.println("You are dead ! Game Over...");
		}
		checkKey(p);

	}

	public void checkKey(Player p){
		if(getKey() != null ){
			System.out.println("You picked up a key ! ("+getKey().ROOM_NUMBER+")");
			p.getKeyring().add(getKey());
			setKey(null);
		}
	}

	public Room(int n){
		numero = n;
		neighbors = new HashMap<Direction, Room>();
	}


	public Room getNextRoom(Direction dir){
		Room r = null;
		if(neighbors.containsKey(dir))
			r = neighbors.get(dir);
		return r;
	}

	public boolean alreadyConnected(Room r){
		if(neighbors.containsValue(r))
			return true;
		else
			return false;
	}

	public boolean isExit() {
		return isExit;
	}

	public void setExit(boolean isExit) {
		this.isExit = isExit;
	}

	public boolean isNeedKey() {
		return needKey;
	}

	public void setNeedKey(boolean needKey) {
		this.needKey = needKey;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public boolean isTrap() {
		return isTrap;
	}

	public void setTrap(boolean isTrap) {
		this.isTrap = isTrap;
	}


}
