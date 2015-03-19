package de.ulfbiallas.imagedatabase.database;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Component;

import de.ulfbiallas.imagedatabase.entities.File;
import de.ulfbiallas.imagedatabase.entities.FileDAO;



@Component
public class DatabaseFileDAO implements FileDAO {

	private final DatabaseConnection databaseConnection;



	@Inject
	public DatabaseFileDAO(DatabaseConnection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}



	public File getById(String id) {
		EntityManager entityManager = databaseConnection.getEntityManager();
		return (File) entityManager.find(File.class, id);
	}



	public List<File> getFiles() {
		EntityManager entityManager = databaseConnection.getEntityManager();
		CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<File> criteriaQuery = queryBuilder.createQuery(File.class);
		criteriaQuery.from(File.class);
		return entityManager.createQuery(criteriaQuery).getResultList();
	}



	public String save(File file) {
		EntityManager entityManager = databaseConnection.getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			entityManager.persist(file);
			tx.commit();
			return file.getId();
		}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
			return null;
		}
	}



	public void update(File file) {
		EntityManager entityManager = databaseConnection.getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			entityManager.refresh(file);
			tx.commit();
		}
		catch (Exception e) {
		   if (tx!=null) tx.rollback();
		   e.printStackTrace(); 
		}
	}



	public void delete(File file) {
		EntityManager entityManager = databaseConnection.getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			entityManager.remove(file);
			tx.commit();
		}
		catch (Exception e) {
		   if (tx!=null) tx.rollback();
		   e.printStackTrace(); 
		}
	}

}
