package rooms;
import java.util.HashMap;

import model.Direction;
import model.Player;
import items.HealPotion;
import items.Key;


public class Room {

	private final int numero;

	private boolean locked = false;
	private boolean isExit = false;
	private boolean isEntrance = false;
	private boolean hasTorch = false;
	private boolean hasPotion = false;
	private Key key = null;

	public HashMap<Direction, Room> neighbors;

	public void act(Player p){
		checkRoom(p);
	}

	/**
	 * Check the exit and all the items in the room
	 * @param p
	 */
	public void checkRoom(Player p){
		displayNum();
		if(isExit())
			System.out.println("Congratulation ! You escaped from the dungeon");
		else 
			checkItem(p);
	}

	protected void displayNum() {
		System.out.println("Room nï¿½"+getNumero());	
	}

	public void checkItem(Player p){
		if(getKey() != null ){
			System.out.println("You picked up a key ! ("+getKey().ROOM_NUMBER+")");
			p.getKeyring().add(getKey());
			setKey(null);
		}
		if(hasTorch){
			hasTorch = false;
			System.out.println("You picked up a torch !");
			p.getTorch().reload();
		}
		if(hasPotion){
			hasPotion = false;
			System.out.println("You picked up a health potion !");
			p.getSecours().add(new HealPotion());
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
	public boolean isANumber(String line) {
		if(line.isEmpty())
			return false;
		for(int i = 0; i < line.length(); i++){
			if(line.charAt(i) < '0' || line.charAt(i) > '9')
				return false;
		}
		return true;
	}

	public boolean isExit() {
		return isExit;
	}

	public void setExit(boolean isExit) {
		this.isExit = isExit;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean needKey) {
		this.locked = needKey;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public boolean isEntrance() {
		return isEntrance;
	}

	public void setEntrance(boolean isEntrance) {
		this.isEntrance = isEntrance;
	}
	public boolean hasTorch() {
		return hasTorch;
	}

	public void setHasTorch(boolean hasTorch) {
		this.hasTorch = hasTorch;
	}

	public int getNumero() {
		return numero;
	}

	public String toString(){
		String s;
		s="Room : "+numero;
		if(neighbors.get(Direction.NORTH) != null){
			s+= " NORTH : "+ getNextRoom(Direction.NORTH).getNumero();
		}
		if(neighbors.get(Direction.EAST) != null){
			s+= " EAST : "+ getNextRoom(Direction.EAST).getNumero();
		}
		if(neighbors.get(Direction.SOUTH) != null){
			s+= " SOUTH : "+ getNextRoom(Direction.SOUTH).getNumero();
		}
		if(neighbors.get(Direction.WEST) != null){
			s+= " WEST : "+ getNextRoom(Direction.WEST).getNumero();
		}
		if(isLocked())
			s+= " LOCKED";
		else
			s+= " UNLOCKED";
		if(key != null)
			s+=" key n°"+key.ROOM_NUMBER;
		else
			s+=" no key";


		return s;
	}

	public int getNeighborsCount(){
		int i = 0;
		if(neighbors.containsKey(Direction.NORTH))
			i++;
		if (neighbors.containsKey(Direction.EAST)) 
			i++;
		if (neighbors.containsKey(Direction.SOUTH)) 
			i++;
		if (neighbors.containsKey(Direction.WEST)) 
			i++;
		return i;
	}

	public boolean isHasPotion() {
		return hasPotion;
	}

	public void setHasPotion(boolean hasPotion) {
		this.hasPotion = hasPotion;
	}

}
