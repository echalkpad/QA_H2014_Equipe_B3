package ca.ulaval.glo4002.drug;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ca.ulaval.glo4002.exceptions.BadFileFormatException;
import ca.ulaval.glo4002.exceptions.DrugNotFoundException;

public class DrugArchiveTest {
	private static final String UNEXISTING_DRUG_FILENAME = "unexisting_file.txt";
	private static final String DRUG_FILE_CONTENT_W_MISSING_VALUES = "\"47738\",\"CAT IV\",\"Human\",\"02229682\"";
	private static final String DRUG_FILE_CONTENT_W_INVALID_DIN = "\"47738\",\"CAT IV\",\"Human\",\"02229682a\",\"SUCRETS FOR KIDS\",\"\",\"N\",\"\",\"1\",\"27-JUN-2012\",\"0116203001\"";
	private static final String DRUG_FILE_CONTENT_VALID = "\"47738\",\"CAT IV\",\"Human\",\"02229682\",\"SUCRETS FOR KIDS\",\"\",\"N\",\"\",\"1\",\"27-JUN-2012\",\"0116203001\"\n"
	                + "\"67232\",\"CAT IV\",\"Human\",\"02243329\",\"CETAPHIL DAILY FACIAL MOISTURIZER SPF 15\",\"\",\"N\",\"\",\"2\",\"01-DEC-2005\",\"0242881001\"\n"
	                + "\"2209\",\"\",\"Human\",\"00312746\",\"TRIFLUOPERAZINE\",\"\",\"N\",\"48161\",\"1\",\"07-MAR-2011\",\"0131362002\"";

	private static final int INVALID_DIN = -1;
	private static final int TRIFLUOPERAZINE_DIN = 312746;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test(expected = FileNotFoundException.class)
	public void DrugArchiveThrowsExceptionWhenFileNotFound() throws IOException, FileNotFoundException, BadFileFormatException {
		new DrugArchive(new FileReader(UNEXISTING_DRUG_FILENAME));
	}

	@Test(expected = BadFileFormatException.class)
	public void DrugArchiveThrowsExceptionWhenBadFileFormat() throws IOException, FileNotFoundException, BadFileFormatException {
		new DrugArchive(new StringReader(DRUG_FILE_CONTENT_W_MISSING_VALUES));
	}

	@Test(expected = BadFileFormatException.class)
	public void DrugArchiveThrowsExceptionWhenBadFileFormat2() throws IOException, FileNotFoundException, BadFileFormatException {
		new DrugArchive(new StringReader(DRUG_FILE_CONTENT_W_INVALID_DIN));
	}

	@Test
	public void DrugArchiveLoadsFileWithoutThrowingException() throws IOException, FileNotFoundException, BadFileFormatException {
		new DrugArchive(new StringReader(DRUG_FILE_CONTENT_VALID));
	}

	@Test
	public void DrugArchiveLoadsFileCorrectly() throws IOException, FileNotFoundException, BadFileFormatException, DrugNotFoundException {
		DrugArchive drugArchive = new DrugArchive(new StringReader(DRUG_FILE_CONTENT_VALID));
		Drug medicament = drugArchive.getDrug(TRIFLUOPERAZINE_DIN);
		assertEquals("TRIFLUOPERAZINE", medicament.getName());
	}

	@Test(expected = DrugNotFoundException.class)
	public void DrugArchiveThrowsExceptionWhenRequestingUnexistingDin() throws IOException, FileNotFoundException, BadFileFormatException,
	                DrugNotFoundException {
		DrugArchive drugArchive = new DrugArchive(new StringReader(DRUG_FILE_CONTENT_VALID));
		drugArchive.getDrug(INVALID_DIN);
	}
}