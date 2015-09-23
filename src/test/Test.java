package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;

import exceptions.UnknowRoomTypeException;
import rooms.Room;
import rooms.RoomFactory;

import items.Key;
import model.Direction;
import model.DjGeneratorFromFile;
import model.Dungeon;
import model.FileParser;
import model.Question;

public class Test {



	@org.junit.Test
	public void roomConnection() {
		Dungeon dj = new Dungeon();
		Room r1;
		Room r2;
		try {
			r1 = RoomFactory.generateRoom("Normal", dj.getRooms());
			r2 = RoomFactory.generateRoom("Normal", dj.getRooms());
			assertFalse(r1.hasNorthSide);
			assertFalse(r1.hasEastSide);
			assertFalse(r1.hasWestSide);
			assertFalse(r1.hasSouthSide);

			assertFalse(r2.hasNorthSide);
			assertFalse(r2.hasEastSide);
			assertFalse(r2.hasWestSide);
			assertFalse(r2.hasSouthSide);

			RoomFactory.connectRoom(r1, Direction.NORTH, r2);

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
			RoomFactory.connectRoom(r1, Direction.SOUTH, r2);
			assertTrue(r1.neighbors.size() < 2);
		} catch (UnknowRoomTypeException e) {
			e.printStackTrace();
		}
	}

	@org.junit.Test
	public void playerMove() {
		Dungeon dj = new Dungeon();
		Room r1, r2;
		try {
			r1 = RoomFactory.generateRoom("Normal", dj.getRooms());
			r2 = RoomFactory.generateRoom("Normal", dj.getRooms());
			RoomFactory.connectRoom(r1, Direction.NORTH, r2);
			dj.setEntrance(r1);
			// init player in room 1
			dj.initPlayer();
			assertTrue(dj.p.getCurrentRoom() == r1);
			assertTrue(dj.p.canGetInRoom(r2));
			dj.executeCommand("n");
			assertTrue(dj.p.getCurrentRoom() == r2);
			dj.executeCommand("s");
			assertTrue(dj.p.getCurrentRoom() == r1);
		} catch (UnknowRoomTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@org.junit.Test
	public void playerOpenDoor() {
		Dungeon dj = new Dungeon();
		Room r1, r2;
		try {
			r1 = RoomFactory.generateRoom("Normal", dj.getRooms());
			r2 = RoomFactory.generateRoom("Normal", dj.getRooms());
			r2.setNeedKey(true);
			RoomFactory.connectRoom(r1, Direction.EAST, r2);
			dj.initPlayer();
			assertFalse(dj.p.canGetInRoom(r2));
			dj.p.addkey(new Key(2));
			assertTrue(dj.p.canGetInRoom(r2));

		} catch (UnknowRoomTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	//	@org.junit.Test
	//	public void testParseFile() {
	//		Dungeon dj = new Dungeon();
	//		dj.setRooms(DjGeneratorFromFile.generateDjFromFile(new File("testDj.txt")));
	//		//Room1
	//		Room r1 = dj.getRooms().get(0);
	//		assertTrue(r1.hasNorthSide);
	//		assertTrue(r1.hasWestSide);
	//		assertFalse(r1.hasEastSide);
	//		assertFalse(r1.hasSouthSide);
	//
	//		//Room2
	//		Room r2 = dj.getRooms().get(1);
	//		assertTrue(r2.hasNorthSide);
	//		assertTrue(r2.hasSouthSide);
	//		assertFalse(r2.hasEastSide);
	//		assertFalse(r2.hasWestSide);
	//
	//		//Room3
	//		Room r3 = dj.getRooms().get(2);
	//		assertTrue(r3.hasNorthSide);
	//		assertTrue(r3.hasEastSide);
	//		assertFalse(r3.hasWestSide);
	//		assertFalse(r3.hasSouthSide);
	//
	//		//Room4
	//		Room r4 = dj.getRooms().get(3);
	//		assertTrue(r4.hasNorthSide);
	//		assertTrue(r4.hasWestSide);
	//		assertTrue(r4.hasSouthSide);
	//		assertFalse(r4.hasEastSide);
	//
	//		//Room5
	//		Room r5 = dj.getRooms().get(4);
	//		assertTrue(r5.hasSouthSide);
	//		assertFalse(r5.hasWestSide);
	//		assertFalse(r5.hasEastSide);
	//		assertFalse(r5.hasNorthSide);
	//
	//		//Room6
	//		Room r6 = dj.getRooms().get(5);
	//		assertTrue(r6.hasEastSide);
	//		assertFalse(r6.hasWestSide);
	//		assertFalse(r6.hasNorthSide);
	//		assertFalse(r6.hasSouthSide);
	//
	//		//Room7
	//		Room r7 = dj.getRooms().get(6);
	//		assertTrue(r7.hasSouthSide);
	//		assertFalse(r7.hasWestSide);
	//		assertFalse(r7.hasEastSide);
	//		assertFalse(r7.hasNorthSide);
	//
	//	}

	@org.junit.Test
	public void testQuestion() {
		Question q1 = new Question("qui est le plus puissant sith", "revan", "vador");
		assertTrue(q1.isCorrectAnswer(q1.getPossibleAnswer().indexOf("revan")+1));
		assertFalse(q1.isCorrectAnswer(q1.getPossibleAnswer().indexOf("vador")+1));
		q1.addAnswer("empereur");
		assertTrue(q1.getPossibleAnswer().contains(new String("empereur")));
	}



}
