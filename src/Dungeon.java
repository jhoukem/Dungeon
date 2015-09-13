import java.util.ArrayList;
import java.util.Scanner;


public class Dungeon {

	Scanner sc = new Scanner(System.in);
	ArrayList<Room> rooms = new ArrayList<Room>();
	Player p = new Player();

	String line;

	public Room generateRoom(){
		Room r = new Room(rooms.size() + 1, false);
		rooms.add(r);
		return r;
	}
	
	public void initPlayer() {
		p.currentRoom = rooms.get(0);
		
	}
	
	public void init(){
		Room r1 = new Room(1, false);
		Room r2 = new Room(2, false);
		Room r3 = new Room(3, false);
		Room r4 = new Room(4, false);
		Room r5 = new Room(5, false);
		Room r6 = new Room(6, false);
		Room r7 = new Room(7, true);


		r1.isEntrance = true;
		r7.isExit = true;
		r6.key = new Key(7);
		r5.isTrap = true;

		connectRoom(r1, Direction.NORTH, r2);
		connectRoom(r1, Direction.WEST, r3);
		connectRoom(r3, Direction.NORTH, r4);
		connectRoom(r4, Direction.NORTH, r5);
		connectRoom(r4, Direction.WEST, r6);
		connectRoom(r2, Direction.NORTH, r7);

		rooms.add(r1);
		rooms.add(r2);
		rooms.add(r3);
		rooms.add(r4);

		p.currentRoom = r1;
	}
	public Dungeon(){

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
		System.out.println("You are in the room n°"+p.currentRoom.numero);
		System.out.println("Please choose a direction bewteen 'north', 'east', 'south' or 'west' .");
		line = sc.nextLine();
		executeCommand(line);
		checkRoom();
		checkItem();

	}

	private void checkRoom() {
		if(p.currentRoom.isTrap)
			p.health = 0;
		else
			checkItem();
	}

	private void checkItem() {
		if(p.currentRoom.key != null ){
			System.out.println("You picked up a key ! ("+p.currentRoom.key.ROOM_NUMBER+")");
			p.keyring.add(p.currentRoom.key);
			p.currentRoom.key = null;
		}

	}

	public boolean isGameOver(){
		return p.health < 1 || p.currentRoom.isExit;
	}

	public void executeCommand(String line) {

		if(line.equals("n")){

			if(p.currentRoom.hasNorthSide){
				if(p.currentRoom.getNextRoom(Direction.NORTH ).needKey && // if the next room need a key 
						!p.hasKeyForRoom(p.currentRoom.getNextRoom(Direction.NORTH ))){ // and if the player don't have the key
					System.out.println("You need a key to enter in this room");
				}
				else{
					System.out.println("You moved in a room");
					p.currentRoom = p.currentRoom.getNextRoom(Direction.NORTH );//change the current room
				}
			}
			else
				System.out.println("There is no way on north side !");


		}
		else if(line.equals("e")){
			if(p.currentRoom.hasEastSide){
				if(p.currentRoom.getNextRoom(Direction.EAST ).needKey && // if the next room need a key 
						!p.hasKeyForRoom(p.currentRoom.getNextRoom(Direction.EAST ))){ // and if the player don't have the key
					System.out.println("You need a key to enter in this room");
				}
				else{
					System.out.println("You moved in a room");
					p.currentRoom = p.currentRoom.getNextRoom(Direction.EAST );//change the current room
				}
			}
			else
				System.out.println("There is no way on east side !");

		}
		else if(line.equals("s")){
			if(p.currentRoom.hasSouthSide){
				if(p.currentRoom.getNextRoom(Direction.SOUTH ).needKey && // if the next room need a key 
						!p.hasKeyForRoom(p.currentRoom.getNextRoom(Direction.SOUTH ))){ // and if the player don't have the key
					System.out.println("You need a key to enter in this room");
				}
				else{
					System.out.println("You moved in a room");
					p.currentRoom = p.currentRoom.getNextRoom(Direction.SOUTH );//change the current room
				}
			}
			else
				System.out.println("There is no way on south side !");

		}
		else if(line.equals("w")){
			if(p.currentRoom.hasWestSide){
				if(p.currentRoom.getNextRoom(Direction.WEST ).needKey && // if the next room need a key 
						!p.hasKeyForRoom(p.currentRoom.getNextRoom(Direction.WEST ))){ // and if the player don't have the key
					System.out.println("You need a key to enter in this room");
				}
				else{
					System.out.println("You moved in a room");
					p.currentRoom = p.currentRoom.getNextRoom(Direction.WEST );//change the current room
				}
			}
			else
				System.out.println("There is no way on west side !");

		}
		else{
			System.out.println("Please write a direction like 'north', 'east', 'south' or 'west' .");
		}
	}

}
