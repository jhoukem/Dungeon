package model;
import java.util.HashMap;


public class Room {

	final int numero;

	public boolean needKey = false;
	public boolean hasEastSide, hasWestSide, hasNorthSide, hasSouthSide = false;

	public boolean isExit = false;
	public boolean isEntrance = false;
	public boolean isTrap = false;
	public Key key = null;

	public HashMap<Direction, Room> neighbors;

	public void act(Player p){
		System.out.println("Room n°"+numero);	
		if(isExit)
			System.out.println("Congratulation ! You escaped from the dungeon");
		
		else if(isTrap){// if the room is a trap after it has hit the player the trap is destroyed
			System.out.println("You fall in a trap");
			p.health --;
			System.out.println("Health :"+p.health);
			isTrap = false;
			if(p.health < 1)
				System.out.println("You are dead ! Game Over...");
		}
		checkKey(p);

	}

	public void checkKey(Player p){
		if(key != null ){
			System.out.println("You picked up a key ! ("+key.ROOM_NUMBER+")");
			p.keyring.add(key);
			key = null;
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


}
