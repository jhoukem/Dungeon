package exceptions;

public class UnknowRoomTypeException extends Exception {

	public UnknowRoomTypeException() {
		super("This type of room is not defined in the factory !");
	}
	
}
