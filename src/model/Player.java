package model;
import java.util.ArrayList;


public class Player {
	int health;
	int stamina;
	private Room currentRoom;
	private Room previousRoom;
	Torch t = new Torch();
	private ArrayList<Key> keyring = new ArrayList<Key>();


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

	public boolean canGetInRoom(Room r){
		if(r.isNeedKey() == false)
			return true;
		else if(hasKeyForRoom(r))
			return true;
		return false;
	}


	public Room getCurrentRoom() {
		return currentRoom;
	}

	public void setCurrentRoom(Room r){
		if(currentRoom != null)
			setPreviousRoom(currentRoom);
		currentRoom = r;
	}

	public Room getPreviousRoom() {
		return previousRoom;
	}

	public void setPreviousRoom(Room previousRoom) {
		this.previousRoom = previousRoom;
	}
	
	public ArrayList<Key> getKeyring() {
		return keyring;
	}

}
