package model;

import java.util.ArrayList;
import java.util.Collections;

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
		return rooms;
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
			exit.setNeedKey(true);
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
}
