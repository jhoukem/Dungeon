package model;
import java.util.HashMap;

import items.Key;


public class Room {

	final int numero;

	private boolean needKey = false;
	public boolean hasEastSide, hasWestSide, hasNorthSide, hasSouthSide = false;

	private boolean isExit = false;
	private boolean isEntrance = false;
	private boolean hasTorch = false;
	private Key key = null;

	public HashMap<Direction, Room> neighbors;

	public void act(Player p){
		displayNum();
		if(isExit())
			System.out.println("Congratulation ! You escaped from the dungeon");
		else 
			checkItem(p);

	}

	protected void displayNum() {
		System.out.println("Room n°"+numero);	
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
	protected boolean isANumber(String line) {
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

}
