package de.ulfbiallas.imagedatabase.database;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import de.ulfbiallas.imagedatabase.entities.Tag;
import de.ulfbiallas.imagedatabase.entities.TagDAO;



@Component
public class DatabaseTagDAO implements TagDAO {

	private final DatabaseConnection databaseConnection;



	@Inject
	public DatabaseTagDAO(DatabaseConnection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}



	public Tag getById(String id) {
		EntityManager entityManager = databaseConnection.getEntityManager();
		return (Tag) entityManager.find(Tag.class, id);
	}



	public Tag getByName(String name) {
		EntityManager entityManager = databaseConnection.getEntityManager();
		CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tag> criteriaQuery = queryBuilder.createQuery(Tag.class);
		Root<Tag> user = criteriaQuery.from(Tag.class);
		criteriaQuery.select(user).where(queryBuilder.equal(user.get("name"), name));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}



	public List<Tag> getTags() {
		EntityManager entityManager = databaseConnection.getEntityManager();
		CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tag> criteriaQuery = queryBuilder.createQuery(Tag.class);
		criteriaQuery.from(Tag.class);
		return entityManager.createQuery(criteriaQuery).getResultList();
	}



	public String save(Tag tag) {
		EntityManager entityManager = databaseConnection.getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			entityManager.persist(tag);
			tx.commit();
			return tag.getId();
		}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
			return null;
		}
	}



	public void update(Tag tag) {
		EntityManager entityManager = databaseConnection.getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			entityManager.refresh(tag);
			tx.commit();
		}
		catch (Exception e) {
		   if (tx!=null) tx.rollback();
		   e.printStackTrace(); 
		}
	}



	public void delete(Tag tag) {
		EntityManager entityManager = databaseConnection.getEntityManager();
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			entityManager.remove(tag);
			tx.commit();
		}
		catch (Exception e) {
		   if (tx!=null) tx.rollback();
		   e.printStackTrace(); 
		}
	}

}
