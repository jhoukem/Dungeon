package exceptions;

@SuppressWarnings("serial")
public class CorruptedFileException extends Exception{
	public CorruptedFileException() {
		super("The question file is corrupted");
	}
}
