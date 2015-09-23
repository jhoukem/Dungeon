package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import rooms.Room;
import rooms.RoomFactory;

import items.Key;
import model.Direction;
import model.Dungeon;

public class Test {

	
	
	@org.junit.Test
	public void roomConnection() {
		Dungeon dj = new Dungeon();
		Room r1 = RoomFactory.generateRoom("r", dj.getRooms());
		Room r2 = RoomFactory.generateRoom("r", dj.getRooms());

		assertFalse(r1.hasNorthSide);
		assertFalse(r1.hasEastSide);
		assertFalse(r1.hasWestSide);
		assertFalse(r1.hasSouthSide);

		assertFalse(r2.hasNorthSide);
		assertFalse(r2.hasEastSide);
		assertFalse(r2.hasWestSide);
		assertFalse(r2.hasSouthSide);

		dj.connectRoom(r1, Direction.NORTH, r2);

		assertTrue(r1.hasNorthSide);
		assertFalse(r1.hasEastSide);
		assertFalse(r1.hasWestSide);
		assertFalse(r1.hasSouthSide);
		assertTrue(r2.hasSouthSide);
		assertFalse(r2.hasEastSide);
		assertFalse(r2.hasWestSide);
		assertFalse(r2.hasNorthSide);

		assertTrue(r1.neighbors.containsKey(Direction.NORTH));
		assertTrue(r2.neighbors.containsKey(Direction.SOUTH));

		assertFalse(r1.neighbors.containsKey(Direction.SOUTH));
		assertFalse(r1.neighbors.containsKey(Direction.EAST));
		assertFalse(r1.neighbors.containsKey(Direction.WEST));

		assertFalse(r2.neighbors.containsKey(Direction.NORTH));
		assertFalse(r2.neighbors.containsKey(Direction.EAST));
		assertFalse(r2.neighbors.containsKey(Direction.WEST));

		// can't connect two room two time
		dj.connectRoom(r1, Direction.SOUTH, r2);
		assertTrue(r1.neighbors.size() < 2);
	}

	@org.junit.Test
	public void playerMove() {
		Dungeon dj = new Dungeon();
		Room r1 = RoomFactory.generateRoom("r", dj.getRooms());
		Room r2 = RoomFactory.generateRoom("r", dj.getRooms());
		dj.connectRoom(r1, Direction.NORTH, r2);
		dj.setEntrance(r1);
		// init player in room 1
		dj.initPlayer();
		assertTrue(dj.p.getCurrentRoom() == r1);
		assertTrue(dj.p.canGetInRoom(r2));
		dj.executeCommand("n");
		assertTrue(dj.p.getCurrentRoom() == r2);
		dj.executeCommand("s");
		assertTrue(dj.p.getCurrentRoom() == r1);

	}

	@org.junit.Test
	public void playerOpenDoor() {
		Dungeon dj = new Dungeon();
		Room r1 = RoomFactory.generateRoom("r", dj.getRooms());
		Room r2 = RoomFactory.generateRoom("r", dj.getRooms());
		r2.setNeedKey(true);
		dj.connectRoom(r1, Direction.EAST, r2);
		dj.initPlayer();
		assertFalse(dj.p.canGetInRoom(r2));
		dj.p.addkey(new Key(2));
		assertTrue(dj.p.canGetInRoom(r2));

	}
	
}
