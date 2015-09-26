package exceptions;

@SuppressWarnings("serial")
public class MissingEntranceRoomException extends Exception {
	
	public MissingEntranceRoomException() {
		super("The dungeon has no entrance room");
	}
}
