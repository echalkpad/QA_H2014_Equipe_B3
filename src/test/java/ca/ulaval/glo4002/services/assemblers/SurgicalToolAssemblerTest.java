package ca.ulaval.glo4002.services.assemblers;

import static org.unitils.reflectionassert.ReflectionAssert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4002.domain.surgicaltool.SurgicalTool;
import ca.ulaval.glo4002.domain.surgicaltool.SurgicalToolStatus;
import ca.ulaval.glo4002.services.dto.SurgicalToolCreationDTO;

@RunWith(MockitoJUnitRunner.class)
public class SurgicalToolAssemblerTest {
	private static final Integer SAMPLE_INTERVENTION_NUMBER = 3;
	private static final String SAMPLE_SERIAL_NUMBER = "2321984423QTY";
	private static final String SAMPLE_TYPE_CODE = "1FT566";
	private static final SurgicalToolStatus SAMPLE_STATUS = SurgicalToolStatus.UNUSED;
 
	SurgicalToolAssembler surgicalToolFactory = new SurgicalToolAssembler();
	SurgicalToolCreationDTO surgicalToolCreationDTO = new SurgicalToolCreationDTO();

	@Before
	public void init() {
		surgicalToolCreationDTO.interventionNumber = SAMPLE_INTERVENTION_NUMBER;
		surgicalToolCreationDTO.serialNumber = SAMPLE_SERIAL_NUMBER;
		surgicalToolCreationDTO.status = SAMPLE_STATUS.getValue();
		surgicalToolCreationDTO.typeCode = SAMPLE_TYPE_CODE;
	}

	@Test
	public void assemblesSurgicalToolCorrectly() {
		SurgicalTool createdSurgicalTool = surgicalToolFactory.createFromDTO(surgicalToolCreationDTO);
		SurgicalTool expectedSurgicalTool = new SurgicalTool(SAMPLE_SERIAL_NUMBER, SAMPLE_TYPE_CODE, SAMPLE_STATUS);

		assertReflectionEquals(expectedSurgicalTool, createdSurgicalTool);
	}
}
