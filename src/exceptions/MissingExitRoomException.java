package exceptions;

@SuppressWarnings("serial")
public class MissingExitRoomException extends Exception {
	public MissingExitRoomException() {
		super("The dungeon has no exit room");
	}
}
