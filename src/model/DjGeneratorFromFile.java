package model;

import java.io.File;
import java.util.ArrayList;

import exceptions.UnknowRoomTypeException;
import items.Key;
import rooms.Room;
import rooms.RoomFactory;

public class DjGeneratorFromFile {



	public static ArrayList<Room> generateDjFromFile(File f){

		ArrayList<Room> rooms = new ArrayList<Room>();
		FileParser fp = new FileParser(f);
		ArrayList<String[]> roomList = fp.parseLines(" +");

		//create all the rooms
		for (String[] strings : roomList) {
//			for (int i = 0; i < strings.length; i++) {
//				System.out.println("t" +strings[i]);
//			}
			
			//String[5] must contains the type of the room
			Room r;
			try {
				r = RoomFactory.generateRoom(strings[5], rooms);
				if(!strings[6].equals("*"))
					r.setKey(new Key(Integer.parseInt(strings[6])));
				if(strings[7].equals("TRUE"))
					r.setHasTorch(true);
				if(strings[8].equals("TRUE"))
					r.setNeedKey(true);
			} catch (UnknowRoomTypeException e) {
				e.printStackTrace();
			}
		}		

		//connect all the rooms
		for (String[] list : roomList) {
			String room = list[0];

			for(int i = 1; i < 5 ;i++){
				String room2 = list[i];

				if(!room2.equals("*")){
					Direction dir = null;

					switch (i) {
					case 1:dir = Direction.NORTH;break;
					case 2:dir = Direction.EAST;break;
					case 3:dir = Direction.SOUTH;break;
					case 4:dir = Direction.WEST;break;
					default:
						break;
					}

					int numRoom1 = Integer.parseInt(room);
					int numRoom2 = Integer.parseInt(room2);

					Room r1 = getRoomNumber(numRoom1, rooms);
					Room r2 = getRoomNumber(numRoom2,rooms);
					RoomFactory.connectRoom(r1, dir, r2);
				}
			}

		}
		return rooms;
	}	


	public static Room getRoomNumber(int i, ArrayList<Room> rooms){
		for(Room r : rooms){
			if(r.getNumero() == i)
				return r;
		}
		return null;
	}

}
