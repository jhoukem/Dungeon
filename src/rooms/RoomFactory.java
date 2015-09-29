package rooms;

import java.util.ArrayList;

import exceptions.UnknowRoomTypeException;
import model.Direction;
import monsters.Arakne;
import monsters.Glouton;
import monsters.Gnome;
import monsters.Troll;
import monsters.Vampire;


public class RoomFactory {

			
	public static Room generateRandomRoom(ArrayList<Room> rooms) throws UnknowRoomTypeException{
		int alea = (int) (Math.random() * 101);
		Room room;
		if(alea < 15)
			room = RoomFactory.generateRoom("Trap", rooms);
		else if(alea < 25)
			room = RoomFactory.generateRoom("Enigma", rooms);
		else if(alea < 35)
		room = generateRandomMonsterRoom(rooms);
		else
			room = RoomFactory.generateRoom("Normal", rooms);
		if(Math.random()*101 > 65)
			room.setHasTorch(true);
		if(Math.random()*101 > 85)
			room.setHasPotion(true);
		return room;
	}


	public static Room generateRoom(String s, ArrayList<Room> rooms) throws UnknowRoomTypeException{
		Room r = null;

		if(s.equals("Enigma"))
			r = new EnigmaRoom(rooms.size() + 1);
		else if(s.equals("Normal"))
			r = new Room(rooms.size() + 1);
		else if(s.equals("Exit")){
			r = new Room(rooms.size() + 1);
			r.setExit(true);	
		}
		else if(s.equals("Entrance")){
			r = new Room(rooms.size() + 1);
			r.setEntrance(true);	
		}
		else if(s.equals("Arakne"))
			r = new MonsterRoom(rooms.size() + 1, new Arakne());
		else if(s.equals("Glouton"))
			r = new MonsterRoom(rooms.size() + 1, new Glouton());
		else if(s.equals("Gnome"))
			r = new MonsterRoom(rooms.size() + 1, new Gnome());
		else if(s.equals("Vampire"))
			r = new MonsterRoom(rooms.size() + 1, new Vampire());
		else if(s.equals("Troll"))
			r = new MonsterRoom(rooms.size() + 1, new Troll());
		else if(s.equals("Trap"))
			r = new TrapRoom(rooms.size() + 1);
		else
			throw new UnknowRoomTypeException();
		rooms.add(r);

		return r;
	}

	/**
	 * @param room the first room we have
	 * @param dir the direction from the first room where we want the connection
	 * @param room2 -> the room we want to connect to another room
	 * ex :  (room1, NORTH, room2) will connect the room2 on the north side of the room1
	 * 
	 */
	public static void connectRoom(Room room, Direction dir, Room room2 ){
		if(room == room2){
			System.out.println("Can't connect a room with itself");
		}
		//usefull when we load the dj from a file
		if(!room.alreadyConnected(room2) && room.getNextRoom(dir) == null){
			room.neighbors.put(dir, room2);
			if(dir == Direction.NORTH)
				room2.neighbors.put(Direction.SOUTH, room);
			else if(dir == Direction.EAST)
				room2.neighbors.put(Direction.WEST, room);

			else if(dir == Direction.SOUTH)
				room2.neighbors.put(Direction.NORTH, room);

			else if(dir == Direction.WEST)
				room2.neighbors.put(Direction.EAST, room);
		}
	}


	public static Room generateRandomMonsterRoom(ArrayList<Room> rooms) {
		int alea = (int) (Math.random() * 101);
		try {
			if(alea < 30)
				return RoomFactory.generateRoom("Glouton", rooms);
			else if(alea < 55)
				return RoomFactory.generateRoom("Arakne", rooms);
			else if(alea < 75)
				return RoomFactory.generateRoom("Gnome", rooms);
			else if(alea < 90)
				return RoomFactory.generateRoom("Vampire", rooms);
			else 
				return RoomFactory.generateRoom("Troll", rooms);

		} catch (UnknowRoomTypeException e) {
			e.printStackTrace();
			return null;
		}
	}



}
