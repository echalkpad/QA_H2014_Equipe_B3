package ca.ulaval.glo4002.services.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SurgicalToolModificationDTO {

	@JsonProperty("statut")
	private String newStatus;
	@JsonProperty("noserie")
	private String newSerialNumber;

	@JsonIgnore
	private Integer interventionNumber;

	@JsonIgnore
	private String typeCode;

	@JsonIgnore
	private String originalSerialNumber;

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typecode) {
		this.typeCode = typecode;
	}

	public String getOriginalSerialNumber() {
		return originalSerialNumber;
	}

	public void setOriginalSerialNumber(String originalSerialNumber) {
		this.originalSerialNumber = originalSerialNumber;
	}

	public String getNewStatus() {
		return newStatus;
	}

	public String getNewSerialNumber() { // TODO: Pourquoi get/set plutôt que public?
		return newSerialNumber;
	}

	public Integer getInterventionNumber() {
		return interventionNumber;
	}

	public void setInterventionNumber(Integer interventionNumber) {
		this.interventionNumber = interventionNumber;
	}

}
