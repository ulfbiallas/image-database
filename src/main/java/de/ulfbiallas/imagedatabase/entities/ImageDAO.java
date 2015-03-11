package de.ulfbiallas.imagedatabase.entities;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.ulfbiallas.imagedatabase.database.DatabaseConnection;



@Component
public class ImageDAO {

	private final DatabaseConnection databaseConnection;



	@Autowired
	public ImageDAO(DatabaseConnection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}



	public Image getById(String id) {
		Session session = databaseConnection.getSession();
		return (Image) session.get(Image.class, id);
	}



	public List<Image> getFiles() {
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
