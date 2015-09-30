package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import rooms.Room;
import exceptions.DungeonTooSmallException;
import exceptions.MissingEntranceRoomException;
import exceptions.MissingExitRoomException;


public class Dungeon {

	Scanner	sc = new Scanner(System.in);
	public Player p	= new Player();
	private boolean	playerMoved	= true;

	private ArrayList<Room>	rooms = new ArrayList<Room>();
	private String line;
	private Room entrance, exit;

	public Dungeon() {

	}

	public void randomInit(int size) throws DungeonTooSmallException,
	MissingExitRoomException, MissingEntranceRoomException {
		rooms = RandomGenerate.generate(size);
		initEntranceAndExit();
		initPlayer();
	}

	public void initFromFile(String s) throws MissingExitRoomException,
	MissingEntranceRoomException {
		rooms = GenerateFromFile.generateDjFromFile(new File(s));
		initEntranceAndExit();
		initPlayer();
	}

	private void initEntranceAndExit() throws MissingExitRoomException,
	MissingEntranceRoomException {
		for (Room r : rooms) {
			if (r.isEntrance()) {
				entrance = r;
			} else if (r.isExit()) {
				exit = r;
			}
		}
		if (!hasExit())
			throw new MissingExitRoomException();
		else if (!hasEntrance())
			throw new MissingEntranceRoomException();
	}

	public boolean hasEntrance() {
		if (entrance != null)
			return true;
		return false;
	}

	public boolean hasExit() {
		if (exit != null)
			return true;
		return false;
	}

	public void update() {
		System.out.println("Please choose a direction bewteen 'north', 'east', 'south' or 'west'.");
		if (playerMoved) {
			p.useTorch();
		}
		line = sc.nextLine();
		executeCommand(line);
		p.getCurrentRoom().act(p);

	}

	public void initPlayer() {
		p = new Player();
		p.setCurrentRoom(entrance);
	}

	public boolean isFinish() {
		return p.getCurrentRoom().isExit();
	}
	
	public boolean isGameOver() {
		return (p.getHealth() < 1) || p.getCurrentRoom().isExit();
	}

	public boolean canPlayerGoTo(Direction dir) {
		if (p.getCurrentRoom().neighbors.containsKey(dir)) {
			// key is needed
			if (p.getCurrentRoom().getNextRoom(dir).isLocked()) {
				if (!p.hasKeyForRoom(p.getCurrentRoom().getNextRoom(dir))) {
					System.out.println("You need a key to enter in this room");
					return false;
				}
			}
		} else {
			System.out.println("There is no way on '" + dir + "' side !");
			return false;
		}

		return true;
	}

	public void executeCommand(String line) {

		Direction dir = null;

		switch (line) {
		case "n":
			dir = Direction.NORTH;
			break;
		case "e":
			dir = Direction.EAST;
			break;
		case "s":
			dir = Direction.SOUTH;
			break;
		case "w":
			dir = Direction.WEST;
			break;
		case "potion":
			p.useHealthPotion();
			break;
		case "inventory":
			p.displayInventory();
			break;
		}
		if (dir != null && canPlayerGoTo(dir)) {
			playerMoved = true;
			p.setCurrentRoom(p.getCurrentRoom().getNextRoom(dir));// change the current room of the player
			
		} else {
			playerMoved = false;
		}
	}

	public ArrayList<Room> getRooms() {
		return rooms;
	}

	public void setRooms(ArrayList<Room> rooms) {
		this.rooms = rooms;
	}

	public Room getEntrance() {
		return entrance;
	}

	public Room getExit() {
		return exit;
	}

	public void setEntrance(Room entrance) {
		this.entrance = entrance;
	}

}
