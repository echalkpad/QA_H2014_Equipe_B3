package ca.ulaval.glo4002.domain.surgicaltool;

public class SurgicalToolNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 7143798799655127089L;

	public SurgicalToolNotFoundException() {
		super();
	}
	
	public SurgicalToolNotFoundException(String message) {
		super(message);
	}
}
