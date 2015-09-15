package model;
import java.util.ArrayList;
import java.util.Scanner;


public class Dungeon {

	Scanner sc = new Scanner(System.in);
	public Player p = new Player();
	
	private ArrayList<Room> rooms = new ArrayList<Room>();

	private String line;
	private Room entrance, exit;

	public Dungeon(){

	}

	public void initPlayer() {
		p.setCurrentRoom(entrance);
	}

	public boolean hasExit(){
		if(exit != null)
			return true;
		else 
			return false;
	}

	public void init(){

		Room r1 = RoomFactory.generateRoom("r", getRooms());
		Room r2 = RoomFactory.generateRoom("r", getRooms());
		Room r3 = RoomFactory.generateRoom("r", getRooms());
		Room r4 = RoomFactory.generateRoom("r", getRooms());
		Room r5 = RoomFactory.generateRoom("r", getRooms());
		Room r6 = RoomFactory.generateRoom("e", getRooms());
		Room r7 = RoomFactory.generateRoom("r", getRooms());


		r7.setExit(true);
		r7.setNeedKey(true);

		r6.setKey(new Key(7));
		r5.setTrap(true);
		entrance = r1;
		
		connectRoom(r1, Direction.NORTH, r2);
		connectRoom(r1, Direction.WEST, r3);
		connectRoom(r3, Direction.NORTH, r4);
		connectRoom(r4, Direction.NORTH, r5);
		connectRoom(r4, Direction.WEST, r6);
		connectRoom(r2, Direction.NORTH, r7);

		initPlayer();
	}

	/**
	 * @param room the first room we have
	 * @param dir the direction from the first room where we want the connection
	 * @param room2 -> the room we want to connect to another room
	 * ex :  (room1, NORTH, room2) will connect the room2 on the north side of the room1
	 * 
	 */
	public void connectRoom(Room room, Direction dir, Room room2 ){
		if(!room.alreadyConnected(room2)){
			room.neighbors.put(dir, room2);
			if(dir == Direction.NORTH){
				room2.neighbors.put(Direction.SOUTH, room);
				room2.hasSouthSide = true;	
				room.hasNorthSide = true;	
			}
			else if(dir == Direction.EAST){
				room2.neighbors.put(Direction.WEST, room);
				room2.hasWestSide = true;	
				room.hasEastSide = true;	
			}
			else if(dir == Direction.SOUTH){
				room2.neighbors.put(Direction.NORTH, room);
				room2.hasNorthSide = true;	
				room.hasSouthSide = true;	
			}
			else if(dir == Direction.WEST){
				room2.neighbors.put(Direction.EAST, room);
				room2.hasEastSide = true;	
				room.hasWestSide = true;	
			}
		}
	}



	public void update() {
		System.out.println("Please choose a direction bewteen 'north', 'east', 'south' or 'west' .");
		line = sc.nextLine();
		executeCommand(line);
		p.getCurrentRoom().act(p);
	}


	public boolean isGameOver(){
		return p.health < 1 || p.getCurrentRoom().isExit();
	}

	public void executeCommand(String line) {

		if(line.equals("n")){

			if(p.getCurrentRoom().hasNorthSide){
				if(p.getCurrentRoom().getNextRoom(Direction.NORTH ).isNeedKey() && // if the next room need a key 
						!p.hasKeyForRoom(p.getCurrentRoom().getNextRoom(Direction.NORTH ))) // and if the player don't have the key
					System.out.println("You need a key to enter in this room");
				else
					p.setCurrentRoom(p.getCurrentRoom().getNextRoom(Direction.NORTH ));//change the current room
			}
			else
				System.out.println("There is no way on north side !");

		}
		else if(line.equals("e")){
			if(p.getCurrentRoom().hasEastSide){
				if(p.getCurrentRoom().getNextRoom(Direction.EAST ).isNeedKey() && // if the next room need a key 
						!p.hasKeyForRoom(p.getCurrentRoom().getNextRoom(Direction.EAST ))) // and if the player don't have the key
					System.out.println("You need a key to enter in this room");
				else
					p.setCurrentRoom(p.getCurrentRoom().getNextRoom(Direction.EAST));//change the current room
			}
			else
				System.out.println("There is no way on east side !");

		}
		else if(line.equals("s")){
			if(p.getCurrentRoom().hasSouthSide){
				if(p.getCurrentRoom().getNextRoom(Direction.SOUTH ).isNeedKey() && // if the next room need a key 
						!p.hasKeyForRoom(p.getCurrentRoom().getNextRoom(Direction.SOUTH ))) // and if the player don't have the key
					System.out.println("You need a key to enter in this room");
				else
					p.setCurrentRoom(p.getCurrentRoom().getNextRoom(Direction.SOUTH));//change the current room
			}
			else
				System.out.println("There is no way on south side !");

		}
		else if(line.equals("w")){
			if(p.getCurrentRoom().hasWestSide){
				if(p.getCurrentRoom().getNextRoom(Direction.WEST ).isNeedKey() && // if the next room need a key 
						!p.hasKeyForRoom(p.getCurrentRoom().getNextRoom(Direction.WEST ))) // and if the player don't have the key
					System.out.println("You need a key to enter in this room");
				else
					p.setCurrentRoom(p.getCurrentRoom().getNextRoom(Direction.WEST));//change the current room
			}
			else
				System.out.println("There is no way on west side !");
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

}
