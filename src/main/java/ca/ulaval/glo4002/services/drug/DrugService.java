package ca.ulaval.glo4002.services.drug;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import ca.ulaval.glo4002.domain.drug.Drug;
import ca.ulaval.glo4002.domain.drug.DrugRepository;
import ca.ulaval.glo4002.entitymanager.EntityManagerProvider;
import ca.ulaval.glo4002.exceptions.ServiceRequestException;
import ca.ulaval.glo4002.persistence.drug.HibernateDrugRepository;
import ca.ulaval.glo4002.rest.dto.DrugSearchDto;

public class DrugService {
	public static final String ERROR_SERVICE_REQUEST_EXCEPTION_DIN001 = "DIN001";
	
	private DrugRepository drugRepository;
	private EntityManager entityManager;
	private EntityTransaction entityTransaction;

	public DrugService() {
		this.drugRepository =  new HibernateDrugRepository();
		this.entityManager = new EntityManagerProvider().getEntityManager();
		this.entityTransaction = entityManager.getTransaction();
	}
	
	public DrugService(DrugRepository drugRepository, EntityManager entityManager) {
		this.drugRepository =  drugRepository;
		this.entityManager = entityManager;
		this.entityTransaction = entityManager.getTransaction();		
	}

	public List<Drug> searchDrug(DrugSearchDto drugSearchDto) throws ServiceRequestException, Exception {
		try {
			entityTransaction.begin();
			List<Drug> drugResults = drugRepository.search(drugSearchDto.getName());
			entityTransaction.commit();
			return drugResults;
		} catch (Exception e) {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
			throw new ServiceRequestException(ERROR_SERVICE_REQUEST_EXCEPTION_DIN001, e.getMessage());
		}
	}
}
