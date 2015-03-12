package de.ulfbiallas.imagedatabase.database;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
		Session session = databaseConnection.getSession();
		return (File) session.get(File.class, id);
	}



	public List<File> getFiles() {
		Session session = databaseConnection.getSession();
		Criteria criteria = session.createCriteria(File.class);
		List<File> list = criteria.list();
		return list;
	}



	public String save(File file) {
		Session session = databaseConnection.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(file);
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
		Session session = databaseConnection.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(file);
			tx.commit();
		}
		catch (Exception e) {
		   if (tx!=null) tx.rollback();
		   e.printStackTrace(); 
		}
	}



	public void delete(File file) {
		Session session = databaseConnection.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(file);
			tx.commit();
		}
		catch (Exception e) {
		   if (tx!=null) tx.rollback();
		   e.printStackTrace(); 
		}
	}

}
