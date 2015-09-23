package rooms;

import java.util.ArrayList;

import monsters.Arakne;
import monsters.Glouton;


public class RoomFactory {


	public static Room generateRoom(String s, ArrayList<Room> rooms){
		Room r = null;

		if(s.equals("e"))
			r = new EnigmRoom(rooms.size() + 1);
		else if(s.equals("r"))
			r = new Room(rooms.size() + 1);
		else if(s.equals("mA"))
			r = new MonsterRoom(rooms.size() + 1, new Arakne());
		else if(s.equals("mG"))
			r = new MonsterRoom(rooms.size() + 1, new Glouton());
		else if(s.equals("t"))
			r = new TrapRoom(rooms.size() + 1);
		else
			System.out.println("TYPE INCONNU !");//TODO throw an unknowRoomTypeException here !
		rooms.add(r);

		return r;
	}

}
