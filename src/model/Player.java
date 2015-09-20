package model;
import java.util.ArrayList;

import items.Fist;
import items.HealPotion;
import items.Key;
import items.Torch;
import items.Weapon;


public class Player {
	private int health;
	private Torch t = new Torch(10);
	private Weapon wp = new Fist();
	private ArrayList<HealPotion> secours;
	private ArrayList<Key> keyring = new ArrayList<Key>();

	private Room currentRoom;
	private Room previousRoom;


	public Player(){
		health = 10;
	}

	public boolean isAlive(){
		return health > 0;
	}

	public void useHealthPotion(){
		if(!secours.isEmpty()){
			HealPotion p = secours.get(0);
			secours.remove(p);
			if(health + p.getValue() > 10)
				health = 10;
			else
				health += p.getValue();
		}
		else{
			System.out.println("You don't have any heal potion left");
		}
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

	public Weapon getWp() {
		return wp;
	}

	public void setWp(Weapon wp) {
		this.wp = wp;
	}

	public Torch getTorch() {
		return t;
	}

	public void setT(Torch t) {
		this.t = t;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}

	public void useTorch() {
		getTorch().use(getCurrentRoom());
	}
}
