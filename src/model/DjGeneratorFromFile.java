package model;

import java.io.File;
import java.util.ArrayList;

public class DjGeneratorFromFile {

	ArrayList<Room> rooms = new ArrayList<Room>();

	public DjGeneratorFromFile(File f){
		FileParser fp = new FileParser(f);
		ArrayList<String[]> roomList=fp.parseLines();
		//create all the rooms
		for (String[] strings : roomList) {
			rooms.add(new Room(Integer.parseInt(strings[0])));
		}		
		//connect all the rooms
		for (String[] list : roomList) {
			for(int i = 0; i < list.length ;i++){
				//NESW
				String room = list[i];
				
				if(!room.equals("*")){
					Direction dir = null;
					
					switch (i) {
					case 1:dir = Direction.NORTH;break;
					case 2:dir = Direction.EAST;break;
					case 3:dir = Direction.SOUTH;break;
					case 4:dir = Direction.WEST;break;
					default:
						break;
					}
					
					int num = Integer.parseInt(room);
					Room r1 = getRoomNumber(i+1);
					Room r2 = getRoomNumber(num);
					Dungeon.connectRoom(r1, dir, r2);
					
					
					
				}
			}
		}



	}	




public Room getRoomNumber(int i){
	for(Room r : rooms){
		if(r.numero == i)
			return r;
	}
	return null;
}

}
