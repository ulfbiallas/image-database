package de.ulfbiallas.imagedatabase.database;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Component;

import de.ulfbiallas.imagedatabase.entities.ImageRecord;
import de.ulfbiallas.imagedatabase.entities.ImageRecordDAO;



@Component
public class DatabaseImageRecordDAO implements ImageRecordDAO {

	private final DatabaseConnection databaseConnection;



	@Inject
	public DatabaseImageRecordDAO(DatabaseConnection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}



	public ImageRecord getById(String id) {
		EntityManager entityManager = databaseConnection.getEntityManager();
		return (ImageRecord) entityManager.find(ImageRecord.class, id);
	}



	public List<ImageRecord> getImageRecords() {
		EntityManager entityManager = databaseConnection.getEntityManager();
		CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ImageRecord> criteriaQuery = queryBuilder.createQuery(ImageRecord.class);
		criteriaQuery.from(ImageRecord.class);
		return entityManager.createQuery(criteriaQuery).getResultList();
	}



	public String save(ImageRecord imageRecord) {
		EntityManager entityManager = databaseConnection.getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			entityManager.persist(imageRecord);
			tx.commit();
			return imageRecord.getId();
		}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
			return null;
		}
	}



	public void update(ImageRecord imageRecord) {
		EntityManager entityManager = databaseConnection.getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			entityManager.refresh(imageRecord);
			tx.commit();
		}
		catch (Exception e) {
		   if (tx!=null) tx.rollback();
		   e.printStackTrace(); 
		}
	}



	public void delete(ImageRecord imageRecord) {
		EntityManager entityManager = databaseConnection.getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			entityManager.remove(imageRecord);
			tx.commit();
		}
		catch (Exception e) {
		   if (tx!=null) tx.rollback();
		   e.printStackTrace(); 
		}
	}

}
