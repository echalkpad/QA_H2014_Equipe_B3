package ca.ulaval.glo4002.persistence;

import javax.persistence.EntityManager;

import ca.ulaval.glo4002.domain.prescription.Prescription;
import ca.ulaval.glo4002.domain.prescription.PrescriptionRepository;

public class HibernatePrescriptionRepository extends HibernateRepository implements PrescriptionRepository {

	public void persist(Prescription prescription) {
		entityManager.persist(prescription);
	}

	public HibernatePrescriptionRepository() {
		super();
	}

	public HibernatePrescriptionRepository(EntityManager entityManager) {
		super(entityManager);
	}
}
