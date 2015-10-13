package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map.Entry;

import exceptions.DungeonTooSmallException;
import exceptions.UnknowRoomTypeException;
import items.Key;
import rooms.Room;
import rooms.RoomFactory;

public class RandomGenerate {
	public static boolean keyOnPath = false;

	public static ArrayList<Room> generate(int size) throws DungeonTooSmallException{
		if(size<4)
			throw new DungeonTooSmallException();
		ArrayList<Room> rooms = new ArrayList<Room>();
		generateLinearDj(size,rooms);
		generateLabyPath(size/2,rooms);
		generateExitKey(rooms);
		//				generateKey(rooms);
		return rooms;
	}



	private static ArrayList<Room> getLockedRoom(ArrayList<Room> rooms) {
		ArrayList<Room> list = new ArrayList<Room>();
		for (Room room : list) {
			if(room.isLocked())
				list.add(room);
		}
		return list;
	}



	public static Room getFirstRoomLockedFrom(Room r, ArrayList<Room> list){
		Room current = null;
		if(!list.contains(r)){
			list.add(r);
			for(Entry<Direction, Room> entry : r.neighbors.entrySet()) {
				current = entry.getValue();
				if(current.isLocked())
					return current;
				else if(!list.contains(current))
					current = getFirstRoomLockedFrom(current, list);
			}
		}

		return current;

	}



	public static void getConnectedRoom(Room r, ArrayList<Room> list){

		for(Entry<Direction, Room> entry : r.neighbors.entrySet()) {
			Room current = entry.getValue();

			if(!list.contains(current) && !current.isLocked() ){
				list.add(current);
				getConnectedRoom(current, list);
			}

		}
	}



	private static void generateExitKey(ArrayList<Room> rooms) {
		Collections.shuffle(rooms);
		for (Room r : rooms) {
			if(!r.isExit() && !r.isEntrance() && r.getNeighborsCount() == 1){
				r.setKey(new Key(getExitRoomNumber(rooms)));
				break;
			}
		}
	}

	private static void generateKey(ArrayList<Room> rooms) {

		ArrayList<Room> list = new ArrayList<Room>();
		ArrayList<Room> roomsLocked = getLockedRoom(rooms);

		Room entrance = rooms.get(getEntranceRoomNumber(rooms));


		Room locked = RandomGenerate.getFirstRoomLockedFrom(entrance, rooms);
		while(locked != null){

			if(!locked.isLocked())
				System.out.println("error au locked");


			Key k = new Key(locked.getNumero());
			RandomGenerate.getConnectedRoom(entrance, list);
			int alea = (int) (Math.random()*list.size());
			while(list.get(alea).getKey() != null){
				alea = (int) (Math.random()*list.size());
			}



			list.get(alea).setKey(k);
			locked.setLocked(false);
			locked = RandomGenerate.getFirstRoomLockedFrom(rooms.get(getEntranceRoomNumber(rooms)), rooms);
		}
		for (Room room : roomsLocked) {
			room.setLocked(true);
		}


	}





	private static void generateLabyPath(int size, ArrayList<Room> rooms) {
		for (int i = 0; i < size; i++) {
			extendDj(rooms, i%2);
		}
	}



	private static void extendDj(ArrayList<Room> rooms, int i) {
		ArrayList<Room> toExtends = new ArrayList<Room>();
		boolean monster = false;
		boolean sphinx = false;
		Collections.shuffle(rooms);
		for (int j = 0; j < rooms.size(); j++) {
			Room current = rooms.get(j);
			//if i == 0 we look for the rooms with 2 neigbours
			if(i == 0 && current.getNeighborsCount() == 2 )
				toExtends.add(current);
			//if i == 1 we look for the room with 1 neigbours and not the exit
			else if(i == 1 && current.getNeighborsCount() == 1 && !current.isExit() )
				toExtends.add(current);
		}

		for (int j = 0; j < toExtends.size(); j++) {
			Room current = toExtends.get(j);
			Direction dir = getRandomDirection();
			//while we don't find a empty direction to connect the current room to the previous room
			while(current.getNextRoom(dir)!= null)
				dir = getRandomDirection();
			try {
				Room newRoom;
				if(!monster){
					newRoom = RoomFactory.generateRandomMonsterRoom(rooms);
					monster = true;
				}
				else if(!sphinx){
					newRoom = RoomFactory.generateRoom("Enigma", rooms);
					sphinx = true;
				}
				else
					newRoom = RoomFactory.generateRandomRoom(rooms);
				if(Math.random()*101<20)
					newRoom.setLocked(true);

				RoomFactory.connectRoom(current, dir, newRoom);
			} catch (UnknowRoomTypeException e) {
				e.printStackTrace();
			}
		}
	}


	private static int getExitRoomNumber(ArrayList<Room> rooms) {
		for(Room r : rooms){
			if(r.isExit())
				return r.getNumero();
		}
		return 0;
	}

	private static int getEntranceRoomNumber(ArrayList<Room> rooms) {
		for(Room r : rooms){
			if(r.isEntrance())
				return r.getNumero();
		}
		return 0;
	}

	/**
	 * @param size the size from the entrance to the exit
	 * @param rooms all the rooms of the dungeon
	 * This function create start by the end of the dungeon. It's create the exit and the room just before
	 * then it basically add a new room connected to the previous one till we get the rooms size requiered 
	 * 
	 */
	public static void generateLinearDj(int size, ArrayList<Room> rooms) {

		try {
			Room exit = RoomFactory.generateRoom("Exit", rooms);
			exit.setLocked(true);
			Room beforeExit;

			beforeExit = Math.random()*101 > 50 ? 	RoomFactory.generateRoom("Normal", rooms) : 
				RoomFactory.generateRoom("Glouton", rooms);

			RoomFactory.connectRoom(exit, getRandomDirection(), beforeExit);

			for (int i = 2; i < size; i++) {
				//TODO maybe add a random for different room type
				Room current = RoomFactory.generateRoom("Normal", rooms);
				//get the room before in the arraylist (to make one path to the exit)
				Room before = rooms.get(current.getNumero()-2);
				Direction dir = getRandomDirection();
				//while we don't find a empty direction to connect the current room to the previous room
				while(before.getNextRoom(dir)!= null)
					dir = getRandomDirection();
				RoomFactory.connectRoom(before, dir, current);
				if(i == size-1)
					current.setEntrance(true);
			}
		} catch (UnknowRoomTypeException e) {
			e.printStackTrace();
		}
	}

	private static Direction getRandomDirection() {
		int alea = (int) (Math.random()*101);
		if(alea < 25)
			return Direction.NORTH;
		else if(alea < 50)
			return Direction.EAST;
		else if(alea < 75)
			return Direction.SOUTH;
		else
			return Direction.WEST;
	}



	public static void main(String[] args) {
		ArrayList<Room> list = new ArrayList<Room>();
		ArrayList<Room> connected = new ArrayList<Room>();
		try {
			Room r1 = RoomFactory.generateRoom("Normal",list);
			Room r2 = RoomFactory.generateRoom("Normal",list);
			Room r3 = RoomFactory.generateRoom("Normal",list);
			Room r4 = RoomFactory.generateRoom("Normal",list);
			Room r5 = RoomFactory.generateRoom("Normal",list);
			Room r6 = RoomFactory.generateRoom("Normal",list);

			RoomFactory.connectRoom(r1, Direction.NORTH, r2);
			RoomFactory.connectRoom(r2, Direction.NORTH, r3);
			RoomFactory.connectRoom(r3, Direction.NORTH, r4);
			RoomFactory.connectRoom(r4, Direction.NORTH, r5);
			RoomFactory.connectRoom(r3, Direction.EAST, r6);
			r6.setLocked(true);

			Room locked = getFirstRoomLockedFrom(r1, connected);

			System.out.println(locked.getNumero());
			System.out.println(locked.toString());

		} catch (UnknowRoomTypeException e) {
			e.printStackTrace();
		}



	}
}
