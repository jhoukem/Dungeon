package rooms;

import java.util.ArrayList;

import exceptions.UnknowRoomTypeException;
import model.Direction;
import monsters.Arakne;
import monsters.Glouton;


public class RoomFactory {


	public static Room generateRoom(String s, ArrayList<Room> rooms) throws UnknowRoomTypeException{
		Room r = null;

		if(s.equals("Enigma"))
			r = new EnigmRoom(rooms.size() + 1);
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
		//		else TODO throw an exception here
	}

	

}
