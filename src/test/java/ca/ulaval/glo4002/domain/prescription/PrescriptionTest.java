package ca.ulaval.glo4002.domain.prescription;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.domain.staff.StaffMember;

public class PrescriptionTest {
	private Prescription prescriptionWithDrug;
	private Prescription prescriptionWithDrugName;
	private Drug drugMock;
	private StaffMember staffMemberMock;
	
	private static final Date SAMPLE_DATE = new Date();
	private static final int SAMPLE_NUMBER_OF_RENEWALS = 2;
	private static final String SAMPLE_DRUG_NAME = "drug_name";
	
	@Before
	public void setup()  {
		drugMock = mock(Drug.class);
		staffMemberMock = mock(StaffMember.class);
		prescriptionWithDrug = new PrescriptionBuilder().drug(drugMock).date(SAMPLE_DATE).prescriber(staffMemberMock).allowedRenewalCount(SAMPLE_NUMBER_OF_RENEWALS).build();
		prescriptionWithDrugName = new PrescriptionBuilder().drugName(SAMPLE_DRUG_NAME).date(SAMPLE_DATE).prescriber(staffMemberMock).allowedRenewalCount(SAMPLE_NUMBER_OF_RENEWALS).build();
	}
	
	@Test(expected = IllegalStateException.class)
	public void disallowsUnspecifiedPrescriber() {
		new PrescriptionBuilder().drug(drugMock).date(SAMPLE_DATE).allowedRenewalCount(SAMPLE_NUMBER_OF_RENEWALS).build();
	}
	
	@Test(expected = IllegalStateException.class)
	public void disallowsUnspecifiedRenewalCount() {
		new PrescriptionBuilder().drug(drugMock).date(SAMPLE_DATE).prescriber(staffMemberMock).build();
	}
	
	@Test(expected = IllegalStateException.class)
	public void disallowsUnspecifiedDate() {
		new PrescriptionBuilder().drug(drugMock).prescriber(staffMemberMock).allowedRenewalCount(SAMPLE_NUMBER_OF_RENEWALS).build();
	}
	
	@Test(expected = IllegalStateException.class)
	public void disallowsUnspecifiedDrugAndDrugName() {
		new PrescriptionBuilder().date(SAMPLE_DATE).prescriber(staffMemberMock).allowedRenewalCount(SAMPLE_NUMBER_OF_RENEWALS).build();
	}
	
	@Test
	public void comparesIdCorrectly() {
		int prescriptionId = prescriptionWithDrug.getId();
		assertTrue(prescriptionWithDrug.compareId(prescriptionId));
	}
	
	@Test
	public void returnsDrugCorrectly() {
		assertSame(drugMock, prescriptionWithDrug.getDrug());
	}
	
	@Test
	public void returnsDrugNameCorrectly() {
		assertEquals(SAMPLE_DRUG_NAME, prescriptionWithDrugName.getDrugName());
	}
	
	@Test
	public void returnsAllowedNumberOfRenewalsCorrectly() {
		assertEquals(SAMPLE_NUMBER_OF_RENEWALS, prescriptionWithDrug.getAllowedNumberOfRenewal());
	}
	
	@Test
	public void returnsDateCorrectly() {
		assertEquals(SAMPLE_DATE, prescriptionWithDrug.getDate());
	}
	
	@Test
	public void returnsStaffMemberCorrectly() {
		assertSame(staffMemberMock, prescriptionWithDrug.getStaffMember());
	}
}
