package model;
import java.util.ArrayList;

import rooms.Room;

import items.Fist;
import items.HealPotion;
import items.Key;
import items.Torch;
import items.Weapon;
import monsters.Monster;


public class Player {
	private int health;
	private Torch t = new Torch();
	private Weapon wp = new Fist();
	private ArrayList<HealPotion> secours = new ArrayList<HealPotion>();
	private ArrayList<Key> keyring = new ArrayList<Key>();

	private Room currentRoom;
	private Room previousRoom;


	public Player(){
		health = 100;
		secours.add(new HealPotion());
	}

	public boolean isAlive(){
		return health > 0;
	}

	public int hit(Monster m){
		int dmg = getWp().getPower();
		int alea = (int)(Math.random()*21) * getWp().getPower() / 100;
		dmg += Math.random()*101 > 50 ?	alea : -alea;

		m.setHealth(m.getHealth() - dmg);
		return dmg;
	}

	public void useHealthPotion(){
		if(!secours.isEmpty()){
			if(health < 100)
				secours.get(0).use(this);
			else
				System.out.println("Impossible action : your life is full !");
		}
		else{
			System.out.println("You don't have any heal potion left");
		}
	}

	public boolean hasKeyForRoom(Room r){
		for(Key key : keyring){
			if(r.getNumero() == key.ROOM_NUMBER)
				return true;
		}
		return false;
	}

	public void addkey(Key k){
		keyring.add(k);
	}

	public boolean canGetInRoom(Room r){
		if(!r.isLocked())
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

	public ArrayList<HealPotion> getSecours() {
		return secours;
	}

	public void displayInventory() {
		System.out.println("Health potion : "+secours.size());
		System.out.println("Keyring : "+keyring.size()+" key");
		for(Key k : keyring){
			System.out.println("Key n�"+k.ROOM_NUMBER);
		}
		System.out.println("Current weapon : "+wp.getName()+" ("+wp.getPower()+" power)");
	}
}
