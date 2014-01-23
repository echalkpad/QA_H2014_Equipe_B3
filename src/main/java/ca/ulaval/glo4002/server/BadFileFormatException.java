package ca.ulaval.glo4002.server;

public class BadFileFormatException extends Exception {
	private static final long serialVersionUID = 1L;
	 
	public BadFileFormatException() {
		super();
	}

	public BadFileFormatException(String message) {
		super(message);
	}
}
