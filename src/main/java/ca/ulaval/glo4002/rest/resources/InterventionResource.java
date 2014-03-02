package ca.ulaval.glo4002.rest.resources;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import ca.ulaval.glo4002.entitymanager.EntityManagerProvider;
import ca.ulaval.glo4002.exceptions.RequestParseException;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.persistence.intervention.HibernateInterventionRepository;
import ca.ulaval.glo4002.persistence.patient.HibernatePatientRepository;
import ca.ulaval.glo4002.rest.requestparsers.intervention.CreateInterventionRequestParser;
import ca.ulaval.glo4002.rest.requestparsers.intervention.CreateInterventionRequestParserFactory;
import ca.ulaval.glo4002.rest.requestparsers.surgicaltool.ModifySurgicalToolRequestParser;
import ca.ulaval.glo4002.rest.requestparsers.surgicaltool.ModifySurgicalToolRequestParserFactory;
import ca.ulaval.glo4002.rest.requestparsers.surgicaltool.CreateSurgicalToolRequestParser;
import ca.ulaval.glo4002.rest.requestparsers.surgicaltool.CreateSurgicalToolRequestParserFactory;
import ca.ulaval.glo4002.rest.utils.BadRequestJsonResponseBuilder;
import ca.ulaval.glo4002.services.intervention.InterventionService;
import ca.ulaval.glo4002.services.intervention.InterventionServiceBuilder;

@Path("interventions/")
public class InterventionResource {
	public String INTERVENTION_NUMBER_PARAMETER = "nointervention";
	public String INSTRUMENT_NUMBER_PARAMETER = "noinstrument";
	
	private InterventionService service;
	private CreateInterventionRequestParserFactory createInterventionRequestParserFactory;
	private CreateSurgicalToolRequestParserFactory createSurgicalToolRequestParserFactory;
	private ModifySurgicalToolRequestParserFactory modifySurgicalToolRequestParserFactory;

	@PathParam("intervention_number")
	private int interventionNumber;
	@PathParam("surgicalToolTypeCode")
	private String surgicalToolTypeCode;
	@PathParam("surgicalToolSerialNumber")
	private String surgicalToolSerialNumber;
	
	public InterventionResource() {
		EntityManager entityManager = new EntityManagerProvider().getEntityManager();
		
		buildInterventionService(entityManager);
		
		this.createInterventionRequestParserFactory = new CreateInterventionRequestParserFactory();
		this.createSurgicalToolRequestParserFactory = new CreateSurgicalToolRequestParserFactory();
		this.modifySurgicalToolRequestParserFactory = new ModifySurgicalToolRequestParserFactory();
	}

	private void buildInterventionService(EntityManager entityManager) {
		InterventionServiceBuilder interventionServiceBuilder = new InterventionServiceBuilder();
		interventionServiceBuilder.entityTransaction(entityManager.getTransaction());
		interventionServiceBuilder.interventionRepository(new HibernateInterventionRepository());
		interventionServiceBuilder.patientRepository(new HibernatePatientRepository());
		this.service = new InterventionService(interventionServiceBuilder);
	}
	
	public InterventionResource(InterventionResourceBuilder interventionResourceBuilder) {
		this.service = interventionResourceBuilder.service;
		this.createInterventionRequestParserFactory = interventionResourceBuilder.createInterventionRequestParserFactory;
		this.createSurgicalToolRequestParserFactory = interventionResourceBuilder.createSurgicalToolRequestParserFactory;
		this.modifySurgicalToolRequestParserFactory = interventionResourceBuilder.modifySurgicalToolRequestParserFactory;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(String request){
		try {
			CreateInterventionRequestParser requestParser = getCreateInterventionRequestParser(request);
			service.createIntervention(requestParser); 
			return Response.status(Status.CREATED).build();
		} catch (RequestParseException e) {
			return BadRequestJsonResponseBuilder.build("INT001", e.getMessage());
		} catch (ServiceRequestException e) {
			return BadRequestJsonResponseBuilder.build(e.getInternalCode(), e.getMessage());
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	private CreateInterventionRequestParser getCreateInterventionRequestParser(String request) throws RequestParseException {
		JSONObject jsonRequest = new JSONObject(request);
		CreateInterventionRequestParser interventionRequestParser = createInterventionRequestParserFactory.getParser(jsonRequest);
		return interventionRequestParser;
	}

	@POST @Path("{intervention_number: [0-9]+}/instruments/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createSurgicalTool(String request) {
		try {
			CreateSurgicalToolRequestParser requestParser = getCreateSurgicalToolRequestParser(request);
			service.createSurgicalTool(requestParser); 
			return Response.status(Status.CREATED).build();
		} catch (RequestParseException e) {
			return BadRequestJsonResponseBuilder.build("INT010", e.getMessage());
		} catch (ServiceRequestException e) {
			return BadRequestJsonResponseBuilder.build(e.getInternalCode(), e.getMessage());
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	private CreateSurgicalToolRequestParser getCreateSurgicalToolRequestParser(String request) throws RequestParseException {
		JSONObject jsonRequest = new JSONObject(request);
		jsonRequest.put("nointervention", String.valueOf(interventionNumber));
		
		CreateSurgicalToolRequestParser requestParser = createSurgicalToolRequestParserFactory.getParser(jsonRequest);
		return requestParser;
	}
	
	@PUT @Path("{intervention_number: [0-9]+}/instruments/{surgicalToolTypeCode}/{surgicalToolSerialNumber}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifySurgicalTool(String request) {
		try {
			ModifySurgicalToolRequestParser requestParser = getModifySurgicalToolRequestParser(request);
			service.modifySurgicalTool(requestParser); 
			return Response.status(Status.OK).build();
		} catch (RequestParseException e) {
			return BadRequestJsonResponseBuilder.build("INT010", e.getMessage());
		} catch (ServiceRequestException e) {
			return BadRequestJsonResponseBuilder.build(e.getInternalCode(), e.getMessage());
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	private ModifySurgicalToolRequestParser getModifySurgicalToolRequestParser(String request) throws RequestParseException {
		JSONObject jsonRequest = new JSONObject(request);
		jsonRequest.put("nointervention", String.valueOf(interventionNumber));
		jsonRequest.put("typecode", String.valueOf(surgicalToolTypeCode));
		jsonRequest.put("serialNumber", String.valueOf(surgicalToolSerialNumber));
		
		ModifySurgicalToolRequestParser requestParser = modifySurgicalToolRequestParserFactory.getParser(jsonRequest);
		return requestParser;
	}
}