package ca.ulaval.glo4002.drug;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import ca.ulaval.glo4002.exceptions.BadFileFormatException;
import ca.ulaval.glo4002.exceptions.DrugNotFoundException;

/*
 * 86% coverage, j'aime
 * 
 * @author Vincent
 * 
 */

public class DrugArchive {
	private static final int DRUG_IDENTIFICATION_NUMBER_COLUMN = 3;
	private static final int BRAND_NAME_COLUMN = 4;

	private List<Drug> drugs = new ArrayList<Drug>();

	public DrugArchive(Reader reader) throws FileNotFoundException, IOException, BadFileFormatException {
		CSVReader csvReader = new CSVReader(reader, ',');

		String[] nextLine;
		int lineNumber = 0;
		while ((nextLine = csvReader.readNext()) != null) {
			drugs.add(parseDrug(nextLine, ++lineNumber));
		}

		csvReader.close();
		
		
	}

	private Drug parseDrug(final String[] line, int lineNumber) throws BadFileFormatException {
		try {
			return new Drug(Integer.parseInt(line[DRUG_IDENTIFICATION_NUMBER_COLUMN]), line[BRAND_NAME_COLUMN]);
		} catch (NumberFormatException e) {
			throw new BadFileFormatException(String.format("Could not parse line %d due to bad data format.", lineNumber));
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new BadFileFormatException(String.format("Could not parse line %d due to invalid number of values.", lineNumber));
		}
	}

	/*
	 * il faudrait trouver une alternative à l'approche ittérative
	 * 
	 * -Vince L-G
	 */

	public Drug getDrug(int din) throws DrugNotFoundException {
		for (Drug drug : drugs) {
			if (drug.getDin() == din) {
				return drug;
			}
		}

		throw new DrugNotFoundException(String.format("Cannot find 'Medicament' with id '%s'.", din));
	}
}
