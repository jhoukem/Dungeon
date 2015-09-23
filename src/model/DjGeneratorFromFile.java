package model;

import java.io.File;
import java.util.ArrayList;

import rooms.Room;

public class DjGeneratorFromFile {



	public static ArrayList<Room> generateDjFromFile(File f){

		ArrayList<Room> rooms = new ArrayList<Room>();
		FileParser fp = new FileParser(f);
		ArrayList<String[]> roomList = fp.parseLines();
		//create all the rooms
		for (String[] strings : roomList) {
			rooms.add(new Room(Integer.parseInt(strings[0])));
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
					Dungeon.connectRoom(r1, dir, r2);
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
