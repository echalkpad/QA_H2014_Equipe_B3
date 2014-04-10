package ca.ulaval.glo4002.domain.staff;

import java.io.Serializable;

public class StaffMember implements Serializable {

	private static final long serialVersionUID = -7716343327245896940L;

	private Integer licenseNumber; //TODO: Change to String

	public StaffMember(Integer licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public Integer getLicenseNumber() {
		return this.licenseNumber;
	}
	
	@Override
	public int hashCode() {
		return licenseNumber.hashCode();
	};
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof StaffMember) {
			return licenseNumber.equals(((StaffMember)obj).licenseNumber);
		}
		return false;
	};
}
