package exceptions;

public class DungeonTooSmallException extends Exception {

	public DungeonTooSmallException() {
		super("The size of the dungeon is too small. It must be > 2 !");
	}
}
