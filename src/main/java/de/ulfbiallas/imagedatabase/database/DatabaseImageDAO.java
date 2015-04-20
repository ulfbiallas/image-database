package de.ulfbiallas.imagedatabase.database;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Component;

import de.ulfbiallas.imagedatabase.entities.Image;
import de.ulfbiallas.imagedatabase.entities.ImageDAO;



//@Component
public class DatabaseImageDAO implements ImageDAO {

	private final DatabaseConnection databaseConnection;



	@Inject
	public DatabaseImageDAO(DatabaseConnection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}



	public Image getById(String id) {
		EntityManager entityManager = databaseConnection.getEntityManager();
		return (Image) entityManager.find(Image.class, id);
	}



	public List<Image> getImages() {
		EntityManager entityManager = databaseConnection.getEntityManager();
		CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Image> criteriaQuery = queryBuilder.createQuery(Image.class);
		criteriaQuery.from(Image.class);
		return entityManager.createQuery(criteriaQuery).getResultList();
	}



	public String save(Image image) {
		EntityManager entityManager = databaseConnection.getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			entityManager.persist(image);
			tx.commit();
			return image.getId();
		}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
			return null;
		}
	}



	public void update(Image image) {
		EntityManager entityManager = databaseConnection.getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			entityManager.refresh(image);
			tx.commit();
		}
		catch (Exception e) {
		   if (tx!=null) tx.rollback();
		   e.printStackTrace(); 
		}
	}



	public void delete(Image image) {
		EntityManager entityManager = databaseConnection.getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			entityManager.remove(image);
			tx.commit();
		}
		catch (Exception e) {
		   if (tx!=null) tx.rollback();
		   e.printStackTrace(); 
		}
	}

}
