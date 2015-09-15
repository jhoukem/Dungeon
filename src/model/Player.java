package model;
import java.util.ArrayList;


public class Player {
	int health;
	int stamina;
	public 	Room currentRoom;
	public 	Room previousRoom;
	
	ArrayList<Key> keyring = new ArrayList<Key>();

	public Player(){
		health = 3;
		stamina = 10;
	}
	
	public boolean hasKeyForRoom(Room r){
		for(Key key : keyring){
			if(r.numero == key.ROOM_NUMBER)
				return true;
		}
		return false;
	}

	public void addkey(Key k){
		keyring.add(k);
	}
	
	public void changeCurrentRoom(Room r){
		currentRoom = r;
	}

	public boolean canGetInRoom(Room r){
		if(r.needKey == false)
			return true;
		else if(hasKeyForRoom(r))
			return true;
		return false;
	}
	
}
