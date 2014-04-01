package ca.ulaval.glo4002.rest.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.rest.utils.BadRequestJsonResponseBuilder;
import ca.ulaval.glo4002.services.PatientService;
import ca.ulaval.glo4002.services.assemblers.PrescriptionAssembler;
import ca.ulaval.glo4002.services.dto.PrescriptionCreationDto;
import ca.ulaval.glo4002.services.dto.validators.PrescriptionCreationDtoValidator;

@Path("patient/{patient_number: [0-9]+}/prescriptions/")
public class PatientResource {
	
	private PatientService patientService; 

	public PatientResource() {
		this.patientService = new PatientService();
	}

	public PatientResource(PatientService patientService) {
		this.patientService = patientService;
	}

	@PathParam("patient_number")
	private int patientNumber;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(PrescriptionCreationDto prescriptionCreationDto) throws Exception {
		try {
			prescriptionCreationDto.setPatientNumber(patientNumber);
			patientService.createPrescription(prescriptionCreationDto, new PrescriptionCreationDtoValidator(), new PrescriptionAssembler());
			return Response.status(Status.CREATED).build();
		} catch (ServiceRequestException e) {
			return BadRequestJsonResponseBuilder.build(e.getInternalCode(), e.getMessage());
		}
	}
}
