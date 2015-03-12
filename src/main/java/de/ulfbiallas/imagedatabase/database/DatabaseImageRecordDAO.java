package de.ulfbiallas.imagedatabase.database;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import de.ulfbiallas.imagedatabase.entities.Image;
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
		Session session = databaseConnection.getSession();
		return (ImageRecord) session.get(ImageRecord.class, id);
	}



	public List<ImageRecord> getImageRecords() {
		Session session = databaseConnection.getSession();
		Criteria criteria = session.createCriteria(ImageRecord.class);
		List<ImageRecord> list = criteria.list();
		return list;
	}



	public String save(ImageRecord imageRecord) {
		Session session = databaseConnection.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(imageRecord);
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
		Session session = databaseConnection.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(imageRecord);
			tx.commit();
		}
		catch (Exception e) {
		   if (tx!=null) tx.rollback();
		   e.printStackTrace(); 
		}
	}



	public void delete(ImageRecord imageRecord) {
		Session session = databaseConnection.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(imageRecord);
			tx.commit();
		}
		catch (Exception e) {
		   if (tx!=null) tx.rollback();
		   e.printStackTrace(); 
		}
	}

}
