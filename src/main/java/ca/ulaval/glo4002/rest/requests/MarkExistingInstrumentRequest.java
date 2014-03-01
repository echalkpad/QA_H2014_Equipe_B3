package ca.ulaval.glo4002.rest.requests;

import org.json.JSONException;
import org.json.JSONObject;

import ca.ulaval.glo4002.exceptions.BadRequestException;

public class MarkExistingInstrumentRequest extends MarkInstrumentRequest {
	
	public MarkExistingInstrumentRequest(JSONObject jsonRequest) throws BadRequestException {
		buildRequest(jsonRequest);
		validateRequestParameters();
	}
	
	private void buildRequest(JSONObject jsonRequest) throws BadRequestException {
		if(jsonRequest.has(TYPECODE_PARAMETER))
			throw new BadRequestException(INVALID_OR_INCOMPLETE_REQUEST_CODE, INVALID_OR_INCOMPLETE_REQUEST_MESSAGE);
		
		try {
			this.status = jsonRequest.getString("statut");
			this.serialNumber = jsonRequest.getString("noserie");
		} catch (JSONException e) {
			throw new BadRequestException("INT010", "données invalides ou incomplètes");
		}
	}
	
	void validateRequestParameters() throws BadRequestException {
		validateStatus();
	}
	
}
