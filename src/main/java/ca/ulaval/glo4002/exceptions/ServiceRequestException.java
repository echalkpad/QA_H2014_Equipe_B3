package ca.ulaval.glo4002.exceptions;

public class ServiceRequestException extends Exception {

	private static final long serialVersionUID = -4693484254820695541L;
	private String internalCode = "";
	
	public ServiceRequestException(String internalCode, String message) {
		super(message);
		this.internalCode = internalCode;
	}
	
	public ServiceRequestException(String internalCode) {
		super();
		this.internalCode = internalCode;
	}
	
	public ServiceRequestException() {
		super();
	}
	
	public String getInternalCode() {
		return internalCode;
	}
}