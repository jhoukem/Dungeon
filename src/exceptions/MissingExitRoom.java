package exceptions;

@SuppressWarnings("serial")
public class MissingExitRoom extends Exception {
	public MissingExitRoom() {
		super("The dungeon has no exit room");
	}
}
