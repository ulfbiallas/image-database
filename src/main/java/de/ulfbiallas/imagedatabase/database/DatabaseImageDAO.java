package de.ulfbiallas.imagedatabase.database;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import de.ulfbiallas.imagedatabase.entities.Image;
import de.ulfbiallas.imagedatabase.entities.ImageDAO;



@Component
public class DatabaseImageDAO implements ImageDAO {

	private final DatabaseConnection databaseConnection;



	@Inject
	public DatabaseImageDAO(DatabaseConnection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}



	public Image getById(String id) {
		Session session = databaseConnection.getSession();
		return (Image) session.get(Image.class, id);
	}



	public List<Image> getImages() {
		Session session = databaseConnection.getSession();
		Criteria criteria = session.createCriteria(Image.class);
		List<Image> list = criteria.list();
		return list;
	}



	public String save(Image image) {
		Session session = databaseConnection.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(image);
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
		Session session = databaseConnection.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(image);
			tx.commit();
		}
		catch (Exception e) {
		   if (tx!=null) tx.rollback();
		   e.printStackTrace(); 
		}
	}



	public void delete(Image image) {
		Session session = databaseConnection.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(image);
			tx.commit();
		}
		catch (Exception e) {
		   if (tx!=null) tx.rollback();
		   e.printStackTrace(); 
		}
	}

}
