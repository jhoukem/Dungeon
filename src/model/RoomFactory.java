package model;

import java.util.ArrayList;

public class RoomFactory {


	public static Room generateRoom(String s, ArrayList<Room> rooms){
		Room r = null;

		if(s.equals("e"))
			r = new EnigmRoom(rooms.size() + 1);
		else if(s.equals("r"))
			r = new Room(rooms.size() + 1);
		else if(s.equals("m"))
			r = new MonsterRoom(rooms.size() + 1);
		else if(s.equals("t"))
			r = new TrapRoom(rooms.size() + 1);
		else
			System.out.println("TYPE INCONNU !");
		rooms.add(r);

		return r;
	}

}
