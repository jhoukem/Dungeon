package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import items.Baton;
import items.HealPotion;
import items.Key;
import items.Mace;
import items.Spike;
import items.Sword;
import items.Torch;
import items.Weapon;

import java.io.File;
import java.util.ArrayList;

import model.Direction;
import model.Dungeon;
import model.GenerateFromFile;
import model.Player;
import model.RandomGenerate;
import monsters.Arakne;
import monsters.Monster;
import monsters.Vampire;
import rooms.EnigmaRoom;
import rooms.MonsterRoom;
import rooms.Question;
import rooms.Room;
import rooms.RoomFactory;
import rooms.TrapRoom;
import exceptions.CorruptedFileException;
import exceptions.DungeonTooSmallException;
import exceptions.MissingEntranceRoomException;
import exceptions.MissingExitRoomException;
import exceptions.UnknowRoomTypeException;


public class Test {


	
	@org.junit.Test
	public void testGetFirstLockedRoom() throws UnknowRoomTypeException{
		ArrayList<Room> list = new ArrayList<Room>();
		ArrayList<Room> connected = new ArrayList<Room>();
		
		ArrayList<Room> locked = new ArrayList<Room>();
		ArrayList<Room> parcours = new ArrayList<Room>();
		
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
		RandomGenerate.getFirstRoomLockedFrom(r1, locked, parcours);
		Room r;
		if(!locked.isEmpty())
			r = locked.get(0);
		else
			r = null;
		assertEquals(6, r.getNumero());

	}




	@org.junit.Test
	public void testDjRandomInit() throws DungeonTooSmallException,
	MissingExitRoomException, MissingEntranceRoomException {
		Dungeon dj = new Dungeon();
		for(int i = 4; i < 20; i++){
			dj.randomInit(7);
			assertTrue(dj.hasExit());
			assertTrue(dj.hasEntrance());
		}
	}

	@org.junit.Test
	public void testInitFromFile() throws MissingExitRoomException,
	MissingEntranceRoomException {
		Dungeon dj = new Dungeon();
		dj.initFromFile("dj1.txt");
		assertTrue(dj.hasExit());
		assertTrue(dj.hasEntrance());
	}

	@org.junit.Test(expected = MissingExitRoomException.class)
	public void testNoExit() throws MissingExitRoomException,
	MissingEntranceRoomException {
		Dungeon dj = new Dungeon();
		dj.initFromFile("testDjNoExit.txt");
	}

	@org.junit.Test(expected = MissingEntranceRoomException.class)
	public void testNoEntrance() throws MissingExitRoomException,
	MissingEntranceRoomException {
		Dungeon dj = new Dungeon();
		dj.initFromFile("testDjNoEntrance.txt");
	}

	@org.junit.Test
	public void testCanPlayerGoDir() {
		Dungeon dj = new Dungeon();

		Room r1 = new Room(1);
		Room r2 = new Room(2);

		r1.setEntrance(true);
		dj.setEntrance(r1);

		dj.getRooms().add(r1);
		dj.getRooms().add(r2);
		dj.initPlayer();

		RoomFactory.connectRoom(r1, Direction.EAST, r2);
		assertTrue(dj.canPlayerGoTo(Direction.EAST));
		assertFalse(dj.canPlayerGoTo(Direction.NORTH));
		assertFalse(dj.canPlayerGoTo(Direction.SOUTH));
		assertFalse(dj.canPlayerGoTo(Direction.WEST));
	}

	@org.junit.Test
	public void testQuestionParse() throws CorruptedFileException {
		ArrayList<Question> q = GenerateFromFile.getAllQuestions("lib_questions.txt");
		assertEquals(6, q.size());
	}

	@org.junit.Test
	public void testPlayerUsePotion() {
		Player p = new Player();
		Arakne a = new Arakne();
		// the arakne hit~10
		a.hit(p);
		p.getSecours().add(new HealPotion());
		p.getSecours().add(new HealPotion());
		// player has 3 potions (one from the init)

		int playerLife = p.getHealth();// save the player life after get hit
		p.useHealthPotion();// 2potions left
		// the life of the player has increase after use the potion
		assertTrue(playerLife < p.getHealth());
		// after using a +40 pdv potion, the life max is limit to 100
		assertEquals(p.getHealth(), 100);
		p.useHealthPotion();
		// When full life, player can't use any more potions
		assertEquals(p.getSecours().size(), 2);
		a.hit(p);
		playerLife = p.getHealth();
		p.getSecours().clear();
		// if player dont have health potion, he can't use it
		p.useHealthPotion();
		assertEquals(p.getHealth(), playerLife);

		p.getSecours().add(new HealPotion());
		p.setHealth(30);
		p.useHealthPotion();
		assertEquals(p.getHealth(), 70);
	}

	@org.junit.Test
	public void testPlayerHit() {
		Player p = new Player();
		Arakne a = new Arakne();
		int arakneLife = a.getHealth();
		int playerLife = p.getHealth();
		p.hit(a);
		a.hit(p);
		// the life of the player has decrease
		assertTrue(arakneLife > a.getHealth());
		// the life of the player has decrease
		assertTrue(playerLife > p.getHealth());
	}

	@org.junit.Test
	public void testIsANumber() {
		Room r = new Room(1);
		assertFalse(r.isANumber(""));
		assertFalse(r.isANumber("sqdqdq"));
		assertFalse(r.isANumber("  1   ef"));
		assertFalse(r.isANumber(" 12"));
		assertFalse(r.isANumber(" 1 5 "));
		assertTrue(r.isANumber("1"));
		assertTrue(r.isANumber("2"));
		assertTrue(r.isANumber("15"));
	}

	@org.junit.Test
	public void testRandomWeapon() {
		Player p = new Player();
		EnigmaRoom er = new EnigmaRoom(1);
		for (int i = 0; i < 20; i++) {
			er.giveRandomWeapon(p);
			Weapon w = p.getWp();
			assertTrue((w instanceof Sword) || (w instanceof Baton)
					|| (w instanceof Mace) || (w instanceof Spike));
		}

	}

	@org.junit.Test
	public void testEnigmaIsCorrectNumber() {
		EnigmaRoom er = new EnigmaRoom(1);
		assertFalse(er.isACorrectNumber(0));
		assertFalse(er.isACorrectNumber(-1));
		assertTrue(er.isACorrectNumber(2));
	}

	@org.junit.Test
	public void testRoomConnection() {
		Dungeon dj = new Dungeon();
		Room r1;
		Room r2;
		try {
			r1 = RoomFactory.generateRoom("Normal", dj.getRooms());
			r2 = RoomFactory.generateRoom("Normal", dj.getRooms());
			assertFalse(r1.neighbors.containsKey(Direction.NORTH));
			assertFalse(r1.neighbors.containsKey(Direction.EAST));
			assertFalse(r1.neighbors.containsKey(Direction.WEST));
			assertFalse(r1.neighbors.containsKey(Direction.SOUTH));

			assertFalse(r2.neighbors.containsKey(Direction.NORTH));
			assertFalse(r2.neighbors.containsKey(Direction.EAST));
			assertFalse(r2.neighbors.containsKey(Direction.WEST));
			assertFalse(r2.neighbors.containsKey(Direction.SOUTH));

			RoomFactory.connectRoom(r1, Direction.NORTH, r2);

			assertTrue(r1.neighbors.containsKey(Direction.NORTH));
			assertFalse(r1.neighbors.containsKey(Direction.EAST));
			assertFalse(r1.neighbors.containsKey(Direction.WEST));
			assertFalse(r1.neighbors.containsKey(Direction.SOUTH));
			assertTrue(r2.neighbors.containsKey(Direction.SOUTH));
			assertFalse(r2.neighbors.containsKey(Direction.EAST));
			assertFalse(r2.neighbors.containsKey(Direction.WEST));
			assertFalse(r2.neighbors.containsKey(Direction.NORTH));

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
	public void testPlayerMove() {
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
	public void testPlayerOpenDoor() {
		Dungeon dj = new Dungeon();
		Room r1, r2;
		try {
			r1 = RoomFactory.generateRoom("Normal", dj.getRooms());
			r2 = RoomFactory.generateRoom("Normal", dj.getRooms());
			r2.setLocked(true);
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

	@org.junit.Test(expected = UnknowRoomTypeException.class)
	public void testRoomFactoryExceptions() throws UnknowRoomTypeException {
		RoomFactory.generateRoom("kjdfse", new ArrayList<Room>());
	}

	@org.junit.Test
	public void testRoomFactory() throws UnknowRoomTypeException {
		Room r = RoomFactory.generateRoom("Normal", new ArrayList<Room>());
		boolean isNormal = false;
		if (r instanceof Room) {
			isNormal = true;
		}
		assertTrue(isNormal);

		Room r1 = RoomFactory.generateRoom("Enigma", new ArrayList<Room>());
		boolean isEnigma = false;
		if (r1 instanceof EnigmaRoom) {
			isEnigma = true;
		}
		assertTrue(isEnigma);

		Room r2 = RoomFactory.generateRoom("Arakne", new ArrayList<Room>());
		boolean isMonster = false;
		if (r2 instanceof MonsterRoom) {
			isMonster = true;
		}
		assertTrue(isMonster);

		r2 = RoomFactory.generateRoom("Glouton", new ArrayList<Room>());
		isMonster = false;
		if (r2 instanceof MonsterRoom) {
			isMonster = true;
		}
		assertTrue(isMonster);

		Room r3 = RoomFactory.generateRoom("Exit", new ArrayList<Room>());
		boolean isExit = false;
		if ((r3 instanceof Room) && r3.isExit()) {
			isExit = true;
		}
		assertTrue(isExit);

		Room r4 = RoomFactory.generateRoom("Entrance", new ArrayList<Room>());
		boolean isEntrance = false;
		if ((r4 instanceof Room) && r4.isEntrance()) {
			isEntrance = true;
		}
		assertTrue(isEntrance);

		Room r5 = RoomFactory.generateRoom("Trap", new ArrayList<Room>());
		boolean isTrap = false;
		if (r5 instanceof TrapRoom) {
			isTrap = true;
		}
		assertTrue(isTrap);

	}

	@org.junit.Test
	public void testParseFile() {
		Dungeon dj = new Dungeon();
		dj.setRooms(GenerateFromFile.generateDjFromFile(new File("testDjNoExit.txt")));
		// Room1
		Room r1 = dj.getRooms().get(0);
		assertTrue(r1.neighbors.containsKey(Direction.NORTH));
		assertTrue(r1.neighbors.containsKey(Direction.WEST));
		assertFalse(r1.neighbors.containsKey(Direction.EAST));
		assertFalse(r1.neighbors.containsKey(Direction.SOUTH));

		// Room2
		Room r2 = dj.getRooms().get(1);
		assertTrue(r2.neighbors.containsKey(Direction.NORTH));
		assertTrue(r2.neighbors.containsKey(Direction.SOUTH));
		assertFalse(r2.neighbors.containsKey(Direction.EAST));
		assertFalse(r2.neighbors.containsKey(Direction.WEST));

		// Room3
		Room r3 = dj.getRooms().get(2);
		assertTrue(r3.neighbors.containsKey(Direction.NORTH));
		assertTrue(r3.neighbors.containsKey(Direction.EAST));
		assertFalse(r3.neighbors.containsKey(Direction.WEST));
		assertFalse(r3.neighbors.containsKey(Direction.SOUTH));

		// Room4
		Room r4 = dj.getRooms().get(3);
		assertTrue(r4.neighbors.containsKey(Direction.NORTH));
		assertTrue(r4.neighbors.containsKey(Direction.WEST));
		assertTrue(r4.neighbors.containsKey(Direction.SOUTH));
		assertFalse(r4.neighbors.containsKey(Direction.EAST));

		// Room5
		Room r5 = dj.getRooms().get(4);
		assertTrue(r5.neighbors.containsKey(Direction.SOUTH));
		assertFalse(r5.neighbors.containsKey(Direction.WEST));
		assertFalse(r5.neighbors.containsKey(Direction.EAST));
		assertFalse(r5.neighbors.containsKey(Direction.NORTH));

		// Room6
		Room r6 = dj.getRooms().get(5);
		assertTrue(r6.neighbors.containsKey(Direction.EAST));
		assertFalse(r6.neighbors.containsKey(Direction.WEST));
		assertFalse(r6.neighbors.containsKey(Direction.NORTH));
		assertFalse(r6.neighbors.containsKey(Direction.SOUTH));

		// Room7
		Room r7 = dj.getRooms().get(6);
		assertTrue(r7.neighbors.containsKey(Direction.SOUTH));
		assertFalse(r7.neighbors.containsKey(Direction.WEST));
		assertFalse(r7.neighbors.containsKey(Direction.EAST));
		assertFalse(r7.neighbors.containsKey(Direction.NORTH));

	}

	@org.junit.Test
	public void testQuestion() {
		Question q1 = new Question("qui est le plus puissant sith", "revan", "vador");
		assertTrue(q1.isCorrectAnswer(q1.getPossibleAnswer().indexOf("revan") + 1));
		assertFalse(q1.isCorrectAnswer(q1.getPossibleAnswer().indexOf("vador") + 1));
		q1.addAnswer("empereur");
		assertTrue(q1.getPossibleAnswer().contains(new String("empereur")));
	}

	@org.junit.Test(expected = DungeonTooSmallException.class)
	public void testRandomDjException() throws DungeonTooSmallException,
	UnknowRoomTypeException {
		ArrayList<Room> rooms = RandomGenerate.generate(3);
		rooms = RandomGenerate.generate(2);
		rooms = RandomGenerate.generate(1);
		rooms = RandomGenerate.generate(0);
	}

	@org.junit.Test
	public void testRandomDjSize() {
		ArrayList<Room> rooms = new ArrayList<Room>();
		RandomGenerate.generateLinearDj(4, rooms);
		assertEquals(rooms.size(), 4);

		ArrayList<Room> rooms2 = new ArrayList<Room>();
		RandomGenerate.generateLinearDj(12, rooms2);
		assertEquals(rooms2.size(), 12);

		ArrayList<Room> rooms3 = new ArrayList<Room>();
		RandomGenerate.generateLinearDj(47, rooms3);
		assertEquals(rooms3.size(), 47);
	}

	@org.junit.Test
	public void testLinearDjConnection() {
		for (int i = 0; i < 10; i++) {
			ArrayList<Room> rooms = new ArrayList<Room>();
			RandomGenerate.generateLinearDj((int) ((Math.random() * 22) + 4), rooms);
			assertTrue(rooms.get(0).getNeighborsCount() == 1);
			for (int j = 1; j < (rooms.size() - 1); j++) {
				assertEquals(rooms.get(j).getNeighborsCount(), 2);
			}
			assertEquals(rooms.get(rooms.size() - 1).getNeighborsCount(), 1);
		}
	}

	@org.junit.Test
	public void testRandomDjConnection() {
		try {
			for (int i = 0; i < 10; i++) {
				ArrayList<Room> rooms = RandomGenerate.generate(4);
				assertEquals(rooms.size(), 9);
				// rooms = RandomGenerate.generate(5);
				// assertTrue(rooms.size() == 1);
			}
		} catch (DungeonTooSmallException e) {
			e.printStackTrace();
		}
	}

	@org.junit.Test
	public void testTorchConstructor() {
		Torch t = new Torch(52);
		assertEquals(20, t.getFire());
		t = new Torch(-5);
		assertEquals(20, t.getFire());
		t = new Torch(10);
		assertEquals(10, t.getFire());
	}

	@org.junit.Test
	public void testTorcheReload() {
		Torch t = new Torch(10);
		t.reload();
		assertEquals(20, t.getFire());
	}

	@org.junit.Test
	public void testTorcheUse() {
		Room r1 = new Room(0);
		Torch t = new Torch();
		while (t.getFire() > 0) {
			int buff = t.getFire();
			t.use(r1);
			assertEquals(buff - 1, t.getFire());
		}
		assertEquals(0, t.getFire());

	}

	@org.junit.Test
	public void testActivatedRoom() {
		TrapRoom trap = new TrapRoom(0);
		Player p = new Player();
		assertEquals(false, trap.isActivated());
		trap.act(p);
		assertEquals(p.getHealth(), 99);
		assertEquals(true, trap.isActivated());
		trap.act(p);
		assertEquals(p.getHealth(), 99);

	}

	@org.junit.Test
	public void testGameOver() {
		Dungeon dj = new Dungeon();
		dj.p.setHealth(0);
		assertEquals(false, dj.p.isAlive());
		assertEquals(true, dj.isGameOver());
		dj.p.setHealth(100);
		assertEquals(true, dj.p.isAlive());
		try {
			dj.randomInit(4);
		} catch (DungeonTooSmallException e) {

			e.printStackTrace();
		} catch (MissingExitRoomException e) {

			e.printStackTrace();
		} catch (MissingEntranceRoomException e) {

			e.printStackTrace();
		}
		dj.p.setCurrentRoom(dj.getExit());
		assertEquals(true, dj.isGameOver());
	}

	@org.junit.Test
	public void testMonsterRoomIsCorrectNumber() {
		MonsterRoom er = new MonsterRoom(1, new Vampire());
		assertFalse(er.isACorrectNumber(5));
		assertFalse(er.isACorrectNumber(-1));
		assertTrue(er.isACorrectNumber(2));
	}

	@org.junit.Test
	public void testMonsterIsAlive() {
		Monster m = new Arakne();
		m.setHealth(0);
		assertEquals(false, m.isAlive());
		m.setHealth(100);
		assertEquals(true, m.isAlive());
	}

	@org.junit.Test
	public void testCheckItems() {
		Player p = new Player();
		p.getTorch().extinguish();
		assertEquals(p.getKeyring().size(),0);
		Room r = new Room(1);
		r.setKey(new Key(1));
		r.setHasPotion(true);
		r.setHasTorch(true);
		r.act(p);
		assertTrue(p.getKeyring().size()>0);
		assertEquals(p.getSecours().size(),2);
		assertEquals(p.getTorch().getFire(),20);
	}


	@org.junit.Test(expected = CorruptedFileException.class)
	public void testCorruptedFileExceptions() throws CorruptedFileException {
		GenerateFromFile.getAllQuestions("testCorruptedQuestionFile.txt");
	}
}
