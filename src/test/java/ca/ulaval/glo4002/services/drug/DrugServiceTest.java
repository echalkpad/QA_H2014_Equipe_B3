package ca.ulaval.glo4002.services.drug;

import javax.persistence.EntityTransaction;

import org.junit.*;
import org.mockito.*;

import static org.mockito.Mockito.*;
import ca.ulaval.glo4002.domain.drug.DrugRepository;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.rest.requestparsers.drug.DrugSearchRequestParser;

public class DrugServiceTest {
	private static final String SAMPLE_DRUG_NAME = "drug_name";
	
	private DrugRepository drugRepositoryMock;
	private EntityTransaction entityTransactionMock;
	private DrugService drugService;
	
	private DrugSearchRequestParser drugSearchRequestMock;
	
	@Before
	public void setup()  {
		createMocks();
		buildInterventionService();
		stubCreateInterventionRequestMockMethods();
		stubEntityTransactionsMethods();
	}
	
	private void createMocks() {
		drugRepositoryMock = mock(DrugRepository.class);
		entityTransactionMock = mock(EntityTransaction.class);
		drugSearchRequestMock = mock(DrugSearchRequestParser.class);
	}
	
	private void buildInterventionService() {
		DrugServiceBuilder drugServiceBuilder = new DrugServiceBuilder();
		drugServiceBuilder.drugRepository(drugRepositoryMock);
		drugServiceBuilder.entityTransaction(entityTransactionMock);
		drugService = drugServiceBuilder.build();
	}
	
	private void stubCreateInterventionRequestMockMethods() {
		when(drugSearchRequestMock.getName()).thenReturn(SAMPLE_DRUG_NAME);
	}
	
	private void stubEntityTransactionsMethods() {
		when(entityTransactionMock.isActive()).thenReturn(true);
	}
	
	@Test
	public void verifySearchDrugCallsCorrectRepositoryMethods() throws ServiceRequestException {
		drugService.searchDrug(drugSearchRequestMock);
		
		verify(drugRepositoryMock).findByName(SAMPLE_DRUG_NAME);
	}
	
	@Test
	public void verifySearchDrugTransactionHandling() throws ServiceRequestException {
		drugService.searchDrug(drugSearchRequestMock);
		InOrder inOrder = inOrder(entityTransactionMock);
		
		inOrder.verify(entityTransactionMock).begin();
		inOrder.verify(entityTransactionMock).commit();
	}
	
}