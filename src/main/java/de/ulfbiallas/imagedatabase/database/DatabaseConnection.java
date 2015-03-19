package de.ulfbiallas.imagedatabase.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Component;

@Component
public class DatabaseConnection {

	private EntityManager entityManager;



	public DatabaseConnection() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("imagedatabase");
		entityManager = entityManagerFactory.createEntityManager();
	}



	public EntityManager getEntityManager() {
		return entityManager;
	}

}
