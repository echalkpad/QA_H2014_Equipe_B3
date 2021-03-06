package ca.ulaval.glo4002.services.dto.validators;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.domain.drug.Din;
import ca.ulaval.glo4002.services.dto.PrescriptionCreationDTO;

@RunWith(MockitoJUnitRunner.class)
public class PrescriptionCreationDTOValidatorTest {
	private static final Date SAMPLE_DATE_PARAMETER = new Date(3);
	private static final Integer SAMPLE_RENEWALS_PARAMETER = 2;
	private static final String SAMPLE_DRUG_NAME_PARAMETER = "drug_name";
	private static final String SAMPLE_STAFF_MEMBER_PARAMETER = "3";
	private static final Din SAMPLE_DIN_PARAMETER = new Din("098423");
	private static final Integer SAMPLE_PATIENT_NUMBER_PARAMETER = 3;
	private static final Integer MIN_RENEWALS_PARAMETER = 0;

	private PrescriptionCreationDTO prescriptionCreationDTO = new PrescriptionCreationDTO();
	private PrescriptionCreationDTOValidator prescriptionCreationDTOValidator = new PrescriptionCreationDTOValidator();
	
	@Before
	public void init() throws ParseException {
		prescriptionCreationDTO.staffMember = SAMPLE_STAFF_MEMBER_PARAMETER;
		prescriptionCreationDTO.date = SAMPLE_DATE_PARAMETER;
		prescriptionCreationDTO.renewals = SAMPLE_RENEWALS_PARAMETER;
		prescriptionCreationDTO.patientNumber = SAMPLE_PATIENT_NUMBER_PARAMETER;
		prescriptionCreationDTO.din = SAMPLE_DIN_PARAMETER;
	}

	@Test
	public void validatingRequestWithDrugNameDoesNotThrowAnException() {
		prescriptionCreationDTO.din = null;
		prescriptionCreationDTO.drugName = SAMPLE_DRUG_NAME_PARAMETER;

		try {
			prescriptionCreationDTOValidator.validate(prescriptionCreationDTO);
		} catch (Exception e) {
			fail("The validator should not have thrown an exception");
		}
	}

	@Test
	public void validatingRequestWithDinDoesNotThrowAnException() {
		prescriptionCreationDTO.din = SAMPLE_DIN_PARAMETER;
		prescriptionCreationDTO.drugName = null;

		try {
			prescriptionCreationDTOValidator.validate(prescriptionCreationDTO);
		} catch (Exception e) {
			fail("The validator should not have thrown an exception");
		}
	}

	@Test(expected = DTOValidationException.class)
	public void disallowsEmptyDrugName() {
		prescriptionCreationDTO.din = null;
		prescriptionCreationDTO.drugName = "";

		prescriptionCreationDTOValidator.validate(prescriptionCreationDTO);
	}

	@Test(expected = DTOValidationException.class)
	public void disallowsUnspecifiedDrugAndDrugNameParameters() {
		prescriptionCreationDTO.din = null;
		prescriptionCreationDTO.drugName = null;

		prescriptionCreationDTOValidator.validate(prescriptionCreationDTO);
	}

	@Test(expected = DTOValidationException.class)
	public void disallowsDrugAndDrugNameParametersBothSpecified() {
		prescriptionCreationDTO.din = SAMPLE_DIN_PARAMETER;
		prescriptionCreationDTO.drugName = SAMPLE_DRUG_NAME_PARAMETER;

		prescriptionCreationDTOValidator.validate(prescriptionCreationDTO);
	}

	@Test(expected = DTOValidationException.class)
	public void disallowsUnspecifiedStaffMemberParameter() {
		prescriptionCreationDTO.staffMember = null;
		prescriptionCreationDTOValidator.validate(prescriptionCreationDTO);
	}

	@Test(expected = DTOValidationException.class)
	public void disallowsUnspecifiedRenewalsParameter() {
		prescriptionCreationDTO.renewals = null;
		prescriptionCreationDTOValidator.validate(prescriptionCreationDTO);
	}

	@Test(expected = DTOValidationException.class)
	public void disallowsUnspecifiedDateParameter() {
		prescriptionCreationDTO.date = null;
		prescriptionCreationDTOValidator.validate(prescriptionCreationDTO);
	}

	@Test(expected = DTOValidationException.class)
	public void disallowsNegativeRenewalsParameter() {
		prescriptionCreationDTO.renewals = -1;
		prescriptionCreationDTOValidator.validate(prescriptionCreationDTO);
	}

	@Test
	public void allowsMinimumRenewalsParameter() {
		prescriptionCreationDTO.renewals = MIN_RENEWALS_PARAMETER;
		
		try {
			prescriptionCreationDTOValidator.validate(prescriptionCreationDTO);
		} catch (Exception e) {
			fail("The validator should not have thrown an exception");
		}
	}
}
