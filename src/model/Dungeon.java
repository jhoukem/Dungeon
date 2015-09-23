package model;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import rooms.Room;
import rooms.RoomFactory;

import items.Key;


public class Dungeon {

	Scanner sc = new Scanner(System.in);
	public Player p = new Player();
	private boolean playerMoved = true;

	private ArrayList<Room> rooms = new ArrayList<Room>();
	private String line;
	private Room entrance, exit;

	public Dungeon(){

	}

	public void initPlayer() {
		//		p.setCurrentRoom(entrance);
		p.setCurrentRoom(rooms.get(0));
	}

	public boolean hasExit(){
		if(exit != null)
			return true;
		else 
			return false;
	}

	public void init(){

		//		Room r1 = RoomFactory.generateRoom("Entrance", getRooms());
		//		Room r2 = RoomFactory.generateRoom("Arakne", getRooms());
		//		Room r3 = RoomFactory.generateRoom("Normal", getRooms());
		//		Room r4 = RoomFactory.generateRoom("Glouton", getRooms());
		//		Room r5 = RoomFactory.generateRoom("Normal", getRooms());
		//		Room r6 = RoomFactory.generateRoom("Normal", getRooms());
		//		Room r7 = RoomFactory.generateRoom("Exit", getRooms());
		//		Room r8 = RoomFactory.generateRoom("Trap", getRooms());
		//		Room r9 = RoomFactory.generateRoom("Enigma", getRooms());
		//		Room r10 = RoomFactory.generateRoom("Normal", getRooms());
		//
		//		setEntrance(r1);
		//		r9.setNeedKey(true);
		//		r4.setKey(new Key(9));
		//		r5.setHasTorch(true);
		//		r10.setHasTorch(true);
		//
		//		connectRoom(r1, Direction.NORTH, r2);
		//		connectRoom(r1, Direction.WEST, r3);
		//		connectRoom(r2, Direction.NORTH, r7);
		//
		//		connectRoom(r3, Direction.SOUTH, r4);
		//		connectRoom(r3, Direction.WEST, r5);
		//		connectRoom(r3, Direction.NORTH, r6);
		//
		//		connectRoom(r6, Direction.NORTH, r8);
		//		connectRoom(r6, Direction.WEST, r9);
		//		connectRoom(r6, Direction.EAST, r2);
		//
		//
		//		connectRoom(r9, Direction.WEST, r10);

		rooms = DjGeneratorFromFile.generateDjFromFile(new File("testDj2.txt"));
		for(Room r : rooms){
			if(r.isEntrance())
				entrance = r;
			else if (r.isExit())
				exit = r;
		}

		initPlayer();
	}


	public void update() {
		System.out.println("Please choose a direction bewteen 'north', 'east', 'south' or 'west'.");
		if(playerMoved)
			p.useTorch();
		line = sc.nextLine();
		executeCommand(line);
		p.getCurrentRoom().act(p);
	}


	public boolean isGameOver(){
		return p.getHealth() < 1 || p.getCurrentRoom().isExit();
	}

	public void executeCommand(String line) {


		if(line.equals("potion")){
			p.useHealthPotion();
		}
		if(line.equals("inventory")){
			p.displayInventory();
		}
		else if(line.equals("n")){

			if(p.getCurrentRoom().hasNorthSide){
				if(p.getCurrentRoom().getNextRoom(Direction.NORTH ).isNeedKey() && // if the next room need a key 
						!p.hasKeyForRoom(p.getCurrentRoom().getNextRoom(Direction.NORTH ))){ // and if the player don't have the key
					System.out.println("You need a key to enter in this room");
					playerMoved = false;	
				}
				else{
					playerMoved = true;	
					p.setCurrentRoom(p.getCurrentRoom().getNextRoom(Direction.NORTH ));//change the current room
				}

			}
			else{
				System.out.println("There is no way on north side !");
				playerMoved = false;	
			}

		}
		else if(line.equals("e")){
			if(p.getCurrentRoom().hasEastSide){
				if(p.getCurrentRoom().getNextRoom(Direction.EAST ).isNeedKey() && // if the next room need a key 
						!p.hasKeyForRoom(p.getCurrentRoom().getNextRoom(Direction.EAST ))){ // and if the player don't have the key
					System.out.println("You need a key to enter in this room");
					playerMoved = false;	
				}

				else{
					p.setCurrentRoom(p.getCurrentRoom().getNextRoom(Direction.EAST));//change the current room
					playerMoved = true;	
				}

			}
			else{
				System.out.println("There is no way on east side !");
				playerMoved = false;	
			}


		}
		else if(line.equals("s")){
			if(p.getCurrentRoom().hasSouthSide){
				if(p.getCurrentRoom().getNextRoom(Direction.SOUTH ).isNeedKey() && // if the next room need a key 
						!p.hasKeyForRoom(p.getCurrentRoom().getNextRoom(Direction.SOUTH ))){ // and if the player don't have the key
					System.out.println("You need a key to enter in this room");
					playerMoved = false;	
				}

				else{
					p.setCurrentRoom(p.getCurrentRoom().getNextRoom(Direction.SOUTH));//change the current room
					playerMoved = true;	
				}

			}
			else{
				System.out.println("There is no way on south side !");
				playerMoved = false;	
			}


		}
		else if(line.equals("w")){
			if(p.getCurrentRoom().hasWestSide){
				if(p.getCurrentRoom().getNextRoom(Direction.WEST ).isNeedKey() && // if the next room need a key 
						!p.hasKeyForRoom(p.getCurrentRoom().getNextRoom(Direction.WEST ))){ // and if the player don't have the key
					System.out.println("You need a key to enter in this room");
					playerMoved = false;	
				}
				else{
					p.setCurrentRoom(p.getCurrentRoom().getNextRoom(Direction.WEST));//change the current room
					playerMoved = true;	
				}

			}
			else{
				playerMoved = false;	
				System.out.println("There is no way on west side !");
			}

		}
		else{
			System.out.println("Please write a direction like 'north', 'east', 'south' or 'west' .");
		}
	}

	public ArrayList<Room> getRooms() {
		return rooms;
	}

	public void setRooms(ArrayList<Room> rooms) {
		this.rooms = rooms;
	}
	public Room getEntrance() {
		return entrance;
	}

	public void setEntrance(Room entrance) {
		this.entrance = entrance;
	}


}
